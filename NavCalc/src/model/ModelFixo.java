package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.Fixo;

public class ModelFixo {
	private Connection conn = null;
	private String msgErro = null;
	
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
	
	public Fixo getFixo(long id) {
		Fixo fixo = null;
		try {
			abrirConn();
			if (conn != null && !conn.isClosed()) {
				String sql = "SELECT nome,latitude,longitude FROM fixos where id=?;";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setLong(1, id);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					fixo = new Fixo();
					fixo.setId(id);
					fixo.setNome(rs.getString(1));
					fixo.setLatitude(rs.getDouble(2));
					fixo.setLongitude(rs.getDouble(3));
				}
			}
		} catch (SQLException e) {
			msgErro = "Erro de SQL: " + e.toString();
		}
		fecharConn();
		return fixo;
	}
	
	public Long getId(String nome_fixo) {
		Long id = 0L;
		try {
			abrirConn();
			if (conn != null && !conn.isClosed()) {
				String sql = "SELECT id FROM fixos where nome=?;";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, nome_fixo);
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
	
	public ArrayList<Fixo> listaFixos(String nome_fixo) {
		ArrayList<Fixo> lista = new ArrayList<Fixo>();
		try {
			abrirConn();
			if (conn != null && !conn.isClosed()) {
				String sql = "SELECT id,nome,latitude,longitude FROM fixos where nome=?;";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, nome_fixo);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					Fixo fixo = new Fixo();
					fixo.setId(rs.getLong(1));
					fixo.setNome(rs.getString(2));
					fixo.setLatitude(rs.getDouble(3));
					fixo.setLongitude(rs.getDouble(4));
					lista.add(fixo);
				}
			}
		} catch (SQLException e) {
			msgErro = "Erro de SQL: " + e.toString();
		}
		fecharConn();
		return lista;
	}
	
	public String getMsgErro() {
		return msgErro;
	}
}
