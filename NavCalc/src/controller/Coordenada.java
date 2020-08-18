package controller;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Coordenada {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "latitude", nullable = false)
	private double latitude;
	@Column(name = "longitude", nullable = false)
	private double longitude;

	// Define a latitude a partir de um double representando os graus
	public boolean setLatitude(double latitude) {
		if (latitude >= -90 && latitude <= 90) {
			this.latitude = latitude;
			return true;
		}
		return false;
	}

	// Define a latitude a partir de graus, minutos e segundos
	public boolean setLatitude(int graus, int minutos, int segundos, char hemisferio) {
		if (graus < 0 || graus > 90 || minutos < 0 || minutos > 59 || segundos < 0 || segundos > 59
				|| (hemisferio != 'N' && hemisferio != 'n' && hemisferio != 'S' && hemisferio != 's')) {
			return false;
		} else if (graus == 90 && (minutos > 0 && segundos > 0)) {
			return false;
		} else {
			double lat = (double) graus + (double) minutos / 60 + (double) segundos / 3600;
			if (hemisferio == 'S' || hemisferio == 's') {
				lat *= -1;
			}
			this.latitude = lat;
			return true;
		}
	}

	// Define a longitude a partir de um double representando os graus
	public boolean setLongitude(double longitude) {
		if (longitude >= -180 && longitude <= 180) {
			this.longitude = longitude;
			return true;
		}
		return false;
	}

	// Define a longitude a partir de graus, minutos e segundos
	public boolean setLongitude(int graus, int minutos, int segundos, char hemisferio) {
		if (graus < 0 || graus > 180 || minutos < 0 || minutos > 59 || segundos < 0 || segundos > 59
				|| (hemisferio != 'E' && hemisferio != 'e' && hemisferio != 'W' && hemisferio != 'w')) {
			return false;
		} else if (graus == 180 && (minutos > 0 && segundos > 0)) {
			return false;
		} else {
			double lon = (double) graus + (double) minutos / 60 + (double) segundos / 3600;
			if (hemisferio == 'W' || hemisferio == 'w') {
				lon *= -1;
			}
			this.longitude = lon;
			return true;
		}
	}

	// Setter para o ID
	public void setId(long id) {
		if (id > 0) {
			this.id = id;
		}
	}

	// Retorna a latitude convertida para uma string no formato ##°##'##"(N/S)
	public String latitudeToString() {
		double lat = this.latitude;
		char ns;
		if (lat < 0) {
			ns = 'S';
			lat *= -1;
		} else {
			ns = 'N';
		}
		int graus = (int) lat;
		int minutos = (int) ((lat * 60) % 60);
		int segundos = (int) ((lat * 3600) % 60);
		return String.format("%02d", graus) + "°" + String.format("%02d", minutos) + "'"
				+ String.format("%02d", segundos) + "\"" + ns;
	}

	// Retorna a longitude convertida para uma string no formato ###°##'##"(E/W)
	public String longitudeToString() {
		double lon = this.longitude;
		char we;
		if (lon < 0) {
			we = 'W';
			lon *= -1;
		} else {
			we = 'E';
		}
		int graus = (int) lon;
		int minutos = (int) ((lon * 60) % 60);
		int segundos = (int) ((lon * 3600) % 60);
		return String.format("%03d", graus) + "°" + String.format("%02d", minutos) + "'"
				+ String.format("%02d", segundos) + "\"" + we;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	// Retorna a latitude convvertida para radianos (usado nos cálculos de distância
	// e rumo)
	public double getLatitudeRadianos() {
		return (this.latitude * Math.PI) / (180);
	}

	// Retorna a longitude convvertida para radianos (usado nos cálculos de
	// distância
	// e rumo)
	public double getLongitudeRadianos() {
		return (this.longitude * Math.PI) / (180);
	}

	// Retorna a distancia em milhas nauticas entre a coordenada atual e uma outra
	// coordenada informada
	// Formula: Dist = graus(acos(sen(Lat1)*sen(Lat2) +
	// cos(Lat1)-cos((Lat2*(Long2-Long1))) * 60
	public double distancia(Coordenada destino) {
		double latitudeO = this.getLatitudeRadianos();
		double longitudeO = this.getLongitudeRadianos();
		double latitudeD = destino.getLatitudeRadianos();
		double longitudeD = destino.getLongitudeRadianos();
		double arco = Math.acos(Math.sin(latitudeO) * Math.sin(latitudeD)
				+ Math.cos(latitudeO) * Math.cos(latitudeD) * Math.cos(Math.abs(longitudeD - longitudeO)));
		double distancia = arco * 3437.74677078; // (60*180)/PI -> convertendo de radianos para graus e multiplicando
													// por 60
		return distancia;
	}

	// Retorna o rumo entre a coordenada atual e uma coordenada de destino fornecida
	// Fórmula: ATAN(Math.sen(longitudeD - longitudeO) / ((Math.cos(latitudeO) *
	// TAN(latitudeD)) - (Math.sen(latitudeO) * Math.cos(longitudeD - longitudeO))))
	public int rumo(Coordenada destino) {
		double latitudeO = this.getLatitudeRadianos();
		double longitudeO = this.getLongitudeRadianos();
		double latitudeD = destino.getLatitudeRadianos();
		double longitudeD = destino.getLongitudeRadianos();
		double azimute = Math.atan(Math.sin(longitudeD - longitudeO) / ((Math.cos(latitudeO) * Math.tan(latitudeD))
				- (Math.sin(latitudeO) * Math.cos(longitudeD - longitudeO))));
		azimute = azimute * 57.2957795131; // 180/PI -> convertendo de radianos para graus
		int rumo = (int) Math.round(azimute);
		if (latitudeD < latitudeO) {
			// Deslocamento para o sul: rumo = azimute + 180
			rumo += 180;
		} else if (longitudeD < longitudeO) {
			// Deslocamento para o norte: quando no sentido oeste, rumo = azimute + 360
			rumo += 360;
		}
		return rumo;
	}

}
