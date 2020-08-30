package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	
	public Long getId(String icao) {
		Long id = 0L;
		try {
			abrirConn();
			if (conn != null && !conn.isClosed()) {
				String sql = "SELECT id FROM aerodromos WHERE icao=?;";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, icao.toUpperCase());
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					id = rs.getLong(1);
				}
			}
		} catch (SQLException e) {
			msgErro = "Erro de SQL: " + e.toString();
		}
		fecharConn();
		return id;
	}
	
	public Aerodromo getAerodromo(long id) {
		Aerodromo ad = null;
		try {
			abrirConn();
			if (conn != null && !conn.isClosed()) {
				String sql = "SELECT icao,latitude,longitude,elevacao,nome,id FROM aerodromos WHERE id=?;";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setLong(1, id);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					ad = new Aerodromo();
					ad.setId(id);
					ad.setIcao(rs.getString(1));
					ad.setLatitude(rs.getDouble(2));
					ad.setLongitude(rs.getDouble(3));
					ad.setElevacao(rs.getInt(4));
					ad.setNome(rs.getString(5));
				}
			}
		} catch (SQLException e) {
			msgErro = "Erro de SQL: " + e.toString();
		}
		fecharConn();
		return ad;
	}
	
	
	
	public String getMsgErro() {
		return this.msgErro;
	}

	public ArrayList<Aerodromo> listaAerodromos(String parametro) {
		ArrayList<Aerodromo> lista = new ArrayList<Aerodromo>();
		try {
			abrirConn();
			if (conn != null && !conn.isClosed()) {
				String ordem = "";
				if(parametro.equalsIgnoreCase("latitude") || parametro.equalsIgnoreCase("longitude")) {
					ordem = " DESC";
				}else {
					ordem = " ASC";
				}
				String sql = "SELECT id,icao,nome,elevacao,latitude,longitude FROM aerodromos ORDER BY " + parametro + ordem + ";";
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					Aerodromo ad = new Aerodromo();
					ad.setId(rs.getLong(1));
					ad.setIcao(rs.getString(2));
					ad.setNome(rs.getString(3));
					ad.setElevacao(rs.getInt(4));
					ad.setLatitude(rs.getDouble(5));
					ad.setLongitude(rs.getDouble(6));
					lista.add(ad);
				}
			}
		} catch (SQLException e) {
			msgErro = "Erro de SQL: " + e.toString();
		}
		fecharConn();
		return lista;
	}
	
	public boolean atualizaAerodromo(Aerodromo ad) {
		try {
			abrirConn();
			if (conn != null && !conn.isClosed()) {
				String sql = "UPDATE aerodromos set icao=?,nome=?,elevacao=?,latitude=?,longitude=? where id=?;";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, ad.getIcao());
				stmt.setString(2, ad.getNome());
				stmt.setInt(3,ad.getElevacao());
				stmt.setDouble(4, ad.getLatitude());
				stmt.setDouble(5, ad.getLongitude());
				stmt.setLong(6,ad.getId());
				if (stmt.executeUpdate() == 1) {
					msgErro = "";
					fecharConn();
					return true;
				} else {
					msgErro = "Erro: Ocorreu um erro durante o cadastro. Por favor, tente novamente.";
				}
			}
		} catch (SQLException e) {
			msgErro = "Erro de SQL: " + e.toString();
		}
		fecharConn();
		return false;
	}

	public void excluirAerodromo(long idAerodromo) {
		try {
			abrirConn();
			if (conn != null && !conn.isClosed()) {
				String sql = "DELETE FROM aerodromos WHERE id=?;";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setLong(1, idAerodromo);
				stmt.executeQuery();
			}
		} catch (SQLException e) {
			msgErro = "Erro de SQL: " + e.toString();
		}
		fecharConn();
	}

	public boolean cadastraAerodromo(Aerodromo ad) {
		try {
			abrirConn();
			if (conn != null && !conn.isClosed()) {
				String sql = "INSERT INTO aerodromos (icao,nome,elevacao,latitude,longitude) VALUES (?,?,?,?,?);";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, ad.getIcao());
				stmt.setString(2, ad.getNome());
				stmt.setInt(3,ad.getElevacao());
				stmt.setDouble(4, ad.getLatitude());
				stmt.setDouble(5, ad.getLongitude());
				if (stmt.executeUpdate() == 1) {
					msgErro = "";
					fecharConn();
					return true;
				} else {
					msgErro = "Erro: Ocorreu um erro durante o cadastro. Por favor, tente novamente.";
				}
			}
		} catch (SQLException e) {
			msgErro = "Erro de SQL: " + e.toString();
		}
		fecharConn();
		return false;
	}
}
