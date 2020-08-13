package controller;

public class Trecho {
	private Coordenada inicio;
	private Coordenada fim;
	private String nomeCoordenadaInicio;
	private String nomeCoordenadaFim;
	private double distancia;
	private int rumo;
	private double velocidade;
	private int tempoMinutos;

	public Trecho(Coordenada inicio, Coordenada fim, double velocidade) {
		this.inicio = inicio;
		this.fim = fim;
		this.distancia = Math.round(inicio.distancia(fim)*10);
		this.distancia = this.distancia / 10;
		this.rumo = inicio.rumo(fim);
		this.velocidade = velocidade;
		this.tempoMinutos = (int) Math.round(distancia*60/velocidade);
		if(inicio instanceof Aerodromo) {
			this.nomeCoordenadaInicio = ((Aerodromo) inicio).getIcao();
		}
		if(inicio instanceof Fixo) {
			this.nomeCoordenadaInicio = ((Fixo) inicio).getNome();
		}
		if(fim instanceof Aerodromo) {
			this.nomeCoordenadaFim = ((Aerodromo) fim).getIcao();
		}
		if(fim instanceof Fixo) {
			this.nomeCoordenadaFim = ((Fixo) fim).getNome();
		}
	}

	
	//Getters
	public Coordenada getInicio() {
		return inicio;
	}

	public Coordenada getFim() {
		return fim;
	}

	public double getDistancia() {
		return distancia;
	}

	public int getRumo() {
		return rumo;
	}

	public double getVelocidade() {
		return velocidade;
	}

	public int getTempoMinutos() {
		return tempoMinutos;
	}
	
	public String getNomeInicio() {
		return this.nomeCoordenadaInicio;
	}
	
	public String getNomeFim() {
		return this.nomeCoordenadaFim;
	}

}
