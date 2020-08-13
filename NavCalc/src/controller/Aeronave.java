package controller;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

@Entity
@Table (name = "aeronaves_padrao")
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
@DynamicInsert(value = true)
public class Aeronave {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name="tipo", nullable = false, length = 4)
	private String tipo;
	@Column(name="velocidadeCruzeiro", nullable = false)
	private int velocidadeCruzeiro; 
	@Column(name="velocidadeSubida", nullable = false)
	private int velocidadeSubida;
	@Column(name="velocidadeDescida", nullable = false)
	private int velocidadeDescida;
	@Column(name="razaoSubida", nullable = false)
	private int razaoSubida;
	@Column(name="razaoDescida", nullable = false)
	private int razaoDescida;
	@Column(name="consumo", nullable = false)
	private int consumo;

	// Setters
	public void setTipo(String tipo) {
		if (tipo != null && tipo.length() == 4) {
			this.tipo = tipo.toUpperCase();
		}
	}

	public void setVelocidadeCruzeiro(int velocidadeCruzeiro) {
		if (velocidadeCruzeiro > 0) {
			this.velocidadeCruzeiro = velocidadeCruzeiro;
		}
	}

	public void setVelocidadeSubida(int velocidadeSubida) {
		if (velocidadeSubida > 0) {
			this.velocidadeSubida = velocidadeSubida;
		}
	}

	public void setVelocidadeDescida(int velocidadeDescida) {
		if (velocidadeDescida > 0) {
			this.velocidadeDescida = velocidadeDescida;
		}
	}

	public void setRazaoSubida(int razaoSubida) {
		if (razaoSubida > 0) {
			this.razaoSubida = razaoSubida;
		}

	}

	public void setRazaoDescida(int razaoDescida) {
		if (razaoDescida > 0) {
			this.razaoDescida = razaoDescida;
		}
	}
	
	public void setConsumo(int consumo) {
		if (consumo > 0) {
			this.consumo = consumo;
		}
	}
	
	public int getConsumo() {
		return consumo;
	}
	
	public void setId(long id) {
		if (id > 0) {
			this.id = id;
		}
	}

	// Getters
	public long getId() {
		return id;
	}

	public String getTipo() {
		return tipo;
	}

	public int getVelocidadeCruzeiro() {
		return velocidadeCruzeiro;
	}

	public int getVelocidadeSubida() {
		return velocidadeSubida;
	}

	public int getVelocidadeDescida() {
		return velocidadeDescida;
	}

	public int getRazaoSubida() {
		return razaoSubida;
	}

	public int getRazaoDescida() {
		return razaoDescida;
	}
}
