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
	
	public ArrayList<Fixo> getFixo(String nome_fixo) {
		ArrayList<Fixo> lista = new ArrayList<Fixo>();
		try {
			abrirConn();
			if (conn != null && !conn.isClosed()) {
				String sql = "SELECT nome,latitude,longitude FROM fixos where nome=?;";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, nome_fixo);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					lista.add(new Fixo(rs.getString(1),rs.getDouble(2),rs.getDouble(3)));
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
