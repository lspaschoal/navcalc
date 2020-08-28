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
@Table (name = "fixos")
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
@DynamicInsert(value = true)
public class Fixo extends Coordenada {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name="nome", nullable = false, length = 50)
	private String nome;
	
	public Fixo() {
	}
	
	// Construtor que recebe as coordenadas completas
		public Fixo(int latitudeGraus, int latitudeMinutos, int latitudeSegundos, char ns, int longitudeGraus,
				int longitudeMinutos, int longitudeSegundos, char we) {
			this.setLatitude(latitudeGraus, latitudeMinutos, latitudeSegundos, ns);
			this.setLongitude(longitudeGraus, longitudeMinutos, longitudeSegundos,we);
		}
	
	// Construtor que recebe o nome a as coordenadas completas
	public Fixo(String nome, int latitudeGraus, int latitudeMinutos, int latitudeSegundos, char ns, int longitudeGraus,
			int longitudeMinutos, int longitudeSegundos, char we) {
		this.setLatitude(latitudeGraus, latitudeMinutos, latitudeSegundos, ns);
		this.setLongitude(longitudeGraus, longitudeMinutos, longitudeSegundos,we);
		this.setNome(nome);
	}
	
	// Construtor que recebe o nome a as coordenadas em double
	public Fixo(String nome, double latitude, double longitude) {
		this.setLatitude(latitude);
		this.setLongitude(longitude);
		this.setNome(nome);
	}
	
	// Setter que define o nome
	public void setNome(String nome) {
			this.nome = nome.toUpperCase();
	}
	
	// Getter que retorna o nome
	public String getNome() {
		return this.nome;
	}
	
	// Getter para o Id
	public long getId() {
		return this.id;
	}
	
	// Setter para o Id
	public void setId(long id) {
		if (id > 0) {
			this.id = id;
		}
	}
}
