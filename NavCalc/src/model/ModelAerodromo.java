package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import controller.Aerodromo;

public class ModelAerodromo {
	
	private Connection conn = null;
	private String msgErro = null;
	
	public ModelAerodromo() {
		
	}
	
	public void abrirConn() {
		try {
			String driver = "org.mariadb.jdbc.Driver";
			Class.forName(driver);
			String url = "jdbc:mysql://localhost:3306/navcalc";
			String user = "root";
			String password = "";
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			msgErro = "Falha ao conectar ao banco de dados: " + e.toString();
		} catch (ClassNotFoundException e) {
			msgErro = "Erro no driver: " + e.toString();
		}
	}
	
	public void fecharConn() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			msgErro = "Erro ao fechar conexão com o banco de dados: " + e.toString();
		}
	}
	
	
	public Aerodromo getAerodromo(String icao) {
		Aerodromo ad = null;
		try {
			abrirConn();
			if (conn != null && !conn.isClosed()) {
				String sql = "SELECT * FROM aerodromos WHERE icao=?;";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, icao.toUpperCase());
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					ad = new Aerodromo();
					ad.setIcao(icao);
					ad.setLatitude(rs.getDouble(2));
					ad.setLongitude(rs.getDouble(3));
					ad.setElevacao(rs.getInt(4));
					ad.setNome(rs.getString(6));
				}
			}
		} catch (SQLException e) {
			msgErro = "Erro de SQL: " + e.toString();
		}
		fecharConn();
		return ad;
	}
	
	public Aerodromo getAerodromo(long id) {
		Aerodromo ad = null;
		
		return ad;
	}
	
	public String getMsgErro() {
		return this.msgErro;
	}
}
