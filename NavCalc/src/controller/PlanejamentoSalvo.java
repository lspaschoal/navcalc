package controller;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import model.ModelAerodromo;
import model.ModelAeronave;
import model.ModelAeronavePersonalizada;
import model.ModelFixo;
import model.ModelPlanejamentoSalvo;

@Entity
@Table(name = "planejamentos_salvos")
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
@DynamicInsert(value = true)
public class PlanejamentoSalvo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "id_usuario", nullable = false, length = 4)
	private long id_usuario;
	@Column(name = "id_origem", nullable = false, length = 4)
	private long id_origem;
	@Column(name = "id_destino", nullable = false, length = 4)
	private long id_destino;
	@Column(name = "aeronave_personalizada", nullable = false)
	private boolean aeronave_personalizada;
	@Column(name = "id_aeronave", nullable = false)
	private long id_aeronave;
	@Column(name = "altitude", nullable = false)
	private int altitude;
	@Column(name = "dmg", nullable = false)
	private int dmg = 0;
	@Column(name = "rota")
	private String rota;
	@Transient
	String msgErro;

	public Planejamento gerarPlanejamento() {
		Planejamento plan = new Planejamento();
		ModelAerodromo mad = new ModelAerodromo();
		plan.setOrigem(mad.getAerodromo(this.id_origem));
		plan.setDestino(mad.getAerodromo(this.id_destino));
		Aeronave acft = new Aeronave();
		if (this.aeronave_personalizada) {
			AeronavePersonalizada ap = new AeronavePersonalizada();
			ModelAeronavePersonalizada map = new ModelAeronavePersonalizada();
			ap = map.getAeronave(this.id_aeronave);
			acft.setTipo(ap.getTipo());
			acft.setVelocidadeCruzeiro(ap.getVelocidadeCruzeiro());
			acft.setVelocidadeSubida(ap.getVelocidadeSubida());
			acft.setVelocidadeDescida(ap.getVelocidadeDescida());
			acft.setRazaoSubida(ap.getRazaoSubida());
			acft.setRazaoDescida(ap.getRazaoDescida());
			acft.setConsumo(ap.getConsumo());
		} else {
			ModelAeronave ma = new ModelAeronave();
			acft = ma.getAeronave(this.id_aeronave);
		}
		plan.setAeronave(acft);
		plan.setAltitude(this.altitude);
		plan.setDmg(this.dmg);
		ArrayList<Fixo> fixos = new ArrayList<Fixo>();
		ModelFixo mf = new ModelFixo();
		if (rota != null) {
			String[] coordenadas = rota.split(",");
			for (String s : coordenadas) {
				try {
					fixos.add(mf.getFixo(Long.parseLong(s)));
				} catch (NumberFormatException nfe) {
					int latgraus = Integer.parseInt(s.substring(0, 2));
					int latmin = Integer.parseInt(s.substring(2, 4));
					int latseg = Integer.parseInt(s.substring(4, 6));
					char lathemisferio = s.charAt(6);
					int longraus = Integer.parseInt(s.substring(7, 10));
					int lonmin = Integer.parseInt(s.substring(10, 12));
					int lonseg = Integer.parseInt(s.substring(12, 14));
					char lonhemisferio = s.charAt(14);
					fixos.add(
							new Fixo(latgraus, latmin, latseg, lathemisferio, longraus, lonmin, lonseg, lonhemisferio));
				}
			}
		}
		plan.setRota(fixos);
		plan.setDmg(this.dmg);
		plan.calcular();
		return plan;
	}

	public boolean salvar(Planejamento plan) {
		ModelAerodromo mad = new ModelAerodromo();
		this.id_origem = mad.getId(plan.getOrigem().getIcao());
		this.id_destino = mad.getId(plan.getDestino().getIcao());
		this.altitude = plan.getAltitude();
		this.dmg = plan.getDmg();
		ModelFixo mf = new ModelFixo();
		if (plan.getRota() != null && !plan.getRota().isEmpty()) {
			this.rota = "";
			for (int i = 0; i < plan.getRota().size() - 1; i++) {
				if (plan.getRota().get(i).getNome() != null) {
					this.rota += mf.getId(plan.getRota().get(i).getNome()) + ",";
				} else {
					String latitude = plan.getRota().get(i).latitudeToString().replace("°", "").replace("'", "")
							.replace("\"", "");
					String longitude = plan.getRota().get(i).longitudeToString().replace("°", "").replace("'", "")
							.replace("\"", "");
					this.rota += latitude + longitude + ",";
				}

			}
			if (plan.getRota().get(plan.getRota().size() - 1).getNome() != null) {
				this.rota += mf.getId(plan.getRota().get(plan.getRota().size() - 1).getNome());
			} else {
				String latitude = plan.getRota().get(plan.getRota().size() - 1).latitudeToString().replace("°", "")
						.replace("'", "").replace("\"", "");
				String longitude = plan.getRota().get(plan.getRota().size() - 1).longitudeToString().replace("°", "")
						.replace("'", "").replace("\"", "");
				this.rota += latitude + longitude;
			}

		}

		ModelPlanejamentoSalvo mps = new ModelPlanejamentoSalvo();
		if (mps.salvar(this)) {
			return true;
		} else {
			this.msgErro = mps.getMsgErro();
			return false;
		}
	}

	public boolean atualizar(Planejamento plan) {
		ModelAerodromo mad = new ModelAerodromo();
		this.id_origem = mad.getId(plan.getOrigem().getIcao());
		this.id_destino = mad.getId(plan.getDestino().getIcao());
		this.altitude = plan.getAltitude();
		this.dmg = plan.getDmg();
		ModelFixo mf = new ModelFixo();
		if (plan.getRota() != null && !plan.getRota().isEmpty()) {
			this.rota = "";
			for (int i = 0; i < plan.getRota().size() - 1; i++) {
				if (plan.getRota().get(i).getNome() != null) {
					this.rota += mf.getId(plan.getRota().get(i).getNome()) + ",";
				} else {
					String latitude = plan.getRota().get(i).latitudeToString().replace("°", "").replace("'", "")
							.replace("\"", "");
					String longitude = plan.getRota().get(i).longitudeToString().replace("°", "").replace("'", "")
							.replace("\"", "");
					this.rota += latitude + longitude + ",";
				}

			}
			if (plan.getRota().get(plan.getRota().size() - 1).getNome() != null) {
				this.rota += mf.getId(plan.getRota().get(plan.getRota().size() - 1).getNome());
			} else {
				String latitude = plan.getRota().get(plan.getRota().size() - 1).latitudeToString().replace("°", "")
						.replace("'", "").replace("\"", "");
				String longitude = plan.getRota().get(plan.getRota().size() - 1).longitudeToString().replace("°", "")
						.replace("'", "").replace("\"", "");
				this.rota += latitude + longitude;
			}

		}

		ModelPlanejamentoSalvo mps = new ModelPlanejamentoSalvo();
		if (mps.atualizar(this)) {
			return true;
		} else {
			this.msgErro = mps.getMsgErro();
			return false;
		}
	}

	// Setters
	public void setId(long id) {
		this.id = id;
	}

	public void setIdUsuario(long id) {
		this.id_usuario = id;
	}

	public void setId_origem(long id_origem) {
		this.id_origem = id_origem;
	}

	public void setId_destino(long id_destino) {
		this.id_destino = id_destino;
	}

	public void setAeronave_personalizada(boolean aeronave_personalizada) {
		this.aeronave_personalizada = aeronave_personalizada;
	}

	public void setId_aeronave(long id_aeronave) {
		this.id_aeronave = id_aeronave;
	}

	public void setAltitude(int altitude) {
		this.altitude = altitude;
	}

	public void setDmg(int dmg) {
		this.dmg = dmg;
	}

	public void setRota(String rota) {
		this.rota = rota;
	}

	// Getters
	public long getId() {
		return id;
	}

	public long getIdUsuario() {
		return id_usuario;
	}

	public long getId_origem() {
		return id_origem;
	}

	public long getId_destino() {
		return id_destino;
	}

	public boolean isAeronave_personalizada() {
		return aeronave_personalizada;
	}

	public long getId_aeronave() {
		return id_aeronave;
	}

	public int getAltitude() {
		return altitude;
	}

	public int getDmg() {
		return dmg;
	}

	public String getRota() {
		return rota;
	}

	public String getMsgErro() {
		return this.msgErro;
	}

}
