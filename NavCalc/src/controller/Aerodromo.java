package controller;

import java.sql.Connection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import model.ModelAerodromo;

@Entity
@Table (name = "aerodromos")
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
@DynamicInsert(value = true)
public class Aerodromo extends Coordenada {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name="icao", nullable = false, length = 4, unique = true)
	private String icao;
	@Column(name="nome", nullable = false)
	private String nome;
	@Column(name="elevacao", nullable = false)
	private int elevacao = 0;
	
	public Aerodromo() {
		
	}

	public Aerodromo(String icao, String nome, int elevacao, double latitude, double longitude) {
		this.setIcao(icao);
		this.setNome(nome);
		this.setElevacao(elevacao);
		this.setLatitude(latitude);
		this.setLongitude(longitude);
	}

	public Aerodromo(String icao, String nome, int elevacao, int latitudeGraus, int latitudeMinutos,
			int latitudeSegundos, char ns, int longitudeGraus, int longitudeMinutos, int longitudeSegundos, char we) {
		this.setIcao(icao);
		this.setNome(nome);
		this.setElevacao(elevacao);
		this.setLatitude(latitudeGraus, latitudeMinutos, latitudeSegundos, ns);
		this.setLongitude(longitudeGraus, longitudeMinutos, longitudeSegundos, we);
	}
	
	public Aerodromo getAerodromo(String icao) {
		Aerodromo ad = null;
		Connection conn = null;
		ModelAerodromo ma = new ModelAerodromo();
		ad = ma.getAerodromo(icao);
		return ad;
	}

	// Sobrescrita do método toString() para que retorne o código icao
	public String toString() {
		return this.icao;
	}

	// Setters
	public void setNome(String nome) {
		this.nome = nome;

	}

	public void setIcao(String icao) {
		if (icao.length() == 4) {
			this.icao = icao;
		}

	}

	public void setElevacao(int elevacao) {
		if (elevacao > 0) {
			this.elevacao = elevacao;
		}

	}
	
	//geters{

	public String getIcao() {
		return icao;
	}

	public String getNome() {
		return nome;
	}

	public int getElevacao() {
		return elevacao;
	}

}
