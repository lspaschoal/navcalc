package controller;

import java.time.Duration;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

public class Planejamento {
	
	private Aerodromo origem;
	private Aerodromo destino;
	private Aeronave aeronave;
	private int altitude;
	private int dmg = 0;
	private ArrayList<Fixo> rota = new ArrayList<Fixo>();
	private ArrayList<Trecho> trechos = new ArrayList<Trecho>();

	public void calcular2() {
		trechos.clear();
		ArrayList<Coordenada> coordenadas = new ArrayList<Coordenada>();
		coordenadas.add(origem);
		for (int i = 0; i < rota.size(); i++) {
			coordenadas.add(rota.get(i));
		}
		coordenadas.add(destino);
		// Subida
		double distanciaTOC = distanciaSubida(origem.getElevacao(), altitude);
		int indiceTOC = 1;
		while (coordenadas.get(indiceTOC - 1).distancia(coordenadas.get(indiceTOC)) < distanciaTOC) {
			distanciaTOC -= coordenadas.get(indiceTOC - 1).distancia(coordenadas.get(indiceTOC));
			indiceTOC++;
		}
		Fixo toc = new Fixo("TOC",
				(coordenadas.get(indiceTOC - 1).getLatitude()
						+ Math.cos(Math.toRadians(coordenadas.get(indiceTOC - 1).rumo(coordenadas.get(indiceTOC))))
								* (distanciaTOC / 60)),
				(coordenadas.get(indiceTOC - 1).getLongitude()
						+ Math.sin(Math.toRadians(coordenadas.get(indiceTOC - 1).rumo(coordenadas.get(indiceTOC))))
								* (distanciaTOC / 60)));
		coordenadas.add(indiceTOC, toc);

		for (int i = 0; i < indiceTOC; i++) {
			trechos.add(new Trecho(coordenadas.get(i), coordenadas.get(i + 1), aeronave.getVelocidadeCruzeiro()));
		}
	}
	public void calcular() {
		trechos.clear();
		ArrayList<Coordenada> coordenadas = new ArrayList<Coordenada>();
		coordenadas.add(origem);
		for (int i = 0; i < rota.size(); i++) {
			coordenadas.add(rota.get(i));
		}
		coordenadas.add(destino);
		for (int i = 0; i < coordenadas.size()-1; i++) {
			trechos.add(new Trecho(coordenadas.get(i), coordenadas.get(i + 1), aeronave.getVelocidadeCruzeiro()));
		}
	}

	// retorna a distância percorrida dados a velocidade e o tempo
	private double calculaDistancia(int velocidade, int minutos) {
		return (velocidade * ((double) minutos / 60));
	}

	// retorna a distância percorrida durante uma subida
	public double distanciaSubida(int altitudeInicial, int altitudeFinal) {
		int tempoSubidaMinutos = (int) Math
				.round((double) (altitudeFinal - altitudeInicial) / (double) (aeronave.getRazaoSubida()));
		double distancia = (Math.round(calculaDistancia(aeronave.getVelocidadeSubida(), tempoSubidaMinutos) * 10));
		distancia /= 10;
		return distancia;
	}

	// Getters e setters
	public Aerodromo getOrigem() {
		return origem;
	}

	public void setOrigem(Aerodromo origem) {
		this.origem = origem;
	}

	public Aerodromo getDestino() {
		return destino;
	}

	public void setDestino(Aerodromo destino) {
		this.destino = destino;
	}

	public Aeronave getAeronave() {
		return aeronave;
	}

	public void setAeronave(Aeronave aeronave) {
		this.aeronave = aeronave;
	}

	public int getAltitude() {
		return altitude;
	}

	public void setAltitude(int altitude) {
		this.altitude = altitude;
	}

	public ArrayList<Trecho> getTrechos() {
		return trechos;
	}
	
	public ArrayList<Fixo> getRota() {
		return this.rota;
	}

	public void setRota(ArrayList<Fixo> rota) {
		this.rota = rota;
	}

	public int getDmg() {
		return dmg;
	}

	public void setDmg(int dmg) {
		this.dmg = dmg;
	}

}
