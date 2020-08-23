package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.PlanejamentoSalvo;

public class ModelPlanejamentoSalvo {
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
	
	public boolean salvar(PlanejamentoSalvo ps) {
		try {
			abrirConn();
			if (conn != null && !conn.isClosed()) {
				String sql = "INSERT INTO planejamentos_salvos (id_usuario,id_origem,id_destino,aeronave_personalizada,id_aeronave,altitude,dmg,rota) values (?,?,?,?,?,?,?,?);";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setLong(1, ps.getIdUsuario());
				stmt.setLong(2, ps.getId_origem());
				stmt.setLong(3, ps.getId_destino());
				stmt.setBoolean(4, ps.isAeronave_personalizada());
				stmt.setLong(5, ps.getId_aeronave());
				stmt.setInt(6, ps.getAltitude());
				stmt.setInt(7, ps.getDmg());
				stmt.setString(8,ps.getRota());
				if (stmt.executeUpdate() == 1) {
					msgErro = "";
					fecharConn();
					return true;
				} else {
					msgErro = "Erro: Ocorreu um erro durante o cadastro. Por favor, tente novamente.";
				}
			} else {
				msgErro = "Erro: Falha na conexão com o banco de dados.";
			}
		} catch (SQLException e) {
			msgErro = "Erro de SQL: " + e.toString();
		}
		fecharConn();
		return false;
	}
	
	public boolean atualizar(PlanejamentoSalvo ps) {
		try {
			abrirConn();
			if (conn != null && !conn.isClosed()) {
				String sql = "UPDATE planejamentos_salvos SET id_usuario=?,id_origem=?,id_destino=?,aeronave_personalizada=?,id_aeronave=?,altitude=?,dmg=?,rota=? WHERE id=?;";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setLong(1, ps.getIdUsuario());
				stmt.setLong(2, ps.getId_origem());
				stmt.setLong(3, ps.getId_destino());
				stmt.setBoolean(4, ps.isAeronave_personalizada());
				stmt.setLong(5, ps.getId_aeronave());
				stmt.setInt(6, ps.getAltitude());
				stmt.setInt(7, ps.getDmg());
				stmt.setString(8,ps.getRota());
				stmt.setLong(9, ps.getId());
				if (stmt.executeUpdate() == 1) {
					msgErro = "";
					fecharConn();
					return true;
				} else {
					msgErro = "Erro: Ocorreu um erro durante o cadastro. Por favor, tente novamente.";
				}
			} else {
				msgErro = "Erro: Falha na conexão com o banco de dados.";
			}
		} catch (SQLException e) {
			msgErro = "Erro de SQL: " + e.toString();
		}
		fecharConn();
		return false;
	}
	
	public PlanejamentoSalvo getPlanejamento(long id){
		PlanejamentoSalvo ps = null;
		try {
			abrirConn();
			if (conn != null && !conn.isClosed()) {
				String sql = "SELECT id,id_usuario,id_origem,id_destino,aeronave_personalizada,id_aeronave,altitude,dmg,rota FROM planejamentos_salvos WHERE id=?;";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setLong(1, id);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					ps = new PlanejamentoSalvo();
					ps.setId(rs.getLong(1));
					ps.setIdUsuario(rs.getLong(2));
					ps.setId_origem(rs.getLong(3));
					ps.setId_destino(rs.getLong(4));
					ps.setAeronave_personalizada(rs.getBoolean(5));
					ps.setId_aeronave(rs.getLong(6));
					ps.setAltitude(rs.getInt(7));
					ps.setDmg(rs.getInt(8));
					ps.setRota(rs.getString(9));
				}
			}	
		} catch (SQLException e) {
			msgErro = "Erro de SQL: " + e.toString();
		}
		fecharConn();
		return ps;
	}
	
	public ArrayList<PlanejamentoSalvo> getPlanosUsuario(long id_usuario){
		ArrayList<PlanejamentoSalvo> lista_planos = new ArrayList<PlanejamentoSalvo>();
		try {
			abrirConn();
			if (conn != null && !conn.isClosed()) {
				String sql = "SELECT id,id_usuario,id_origem,id_destino,aeronave_personalizada,id_aeronave,altitude,dmg,rota FROM planejamentos_salvos WHERE id_usuario=?;";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setLong(1, id_usuario);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					PlanejamentoSalvo ps = new PlanejamentoSalvo();
					ps.setId(rs.getLong(1));
					ps.setIdUsuario(rs.getLong(2));
					ps.setId_origem(rs.getLong(3));
					ps.setId_destino(rs.getLong(4));
					ps.setAeronave_personalizada(rs.getBoolean(5));
					ps.setId_aeronave(rs.getLong(6));
					ps.setAltitude(rs.getInt(7));
					ps.setDmg(rs.getInt(8));
					ps.setRota(rs.getString(9));
					lista_planos.add(ps);
				}
			}	
		} catch (SQLException e) {
			msgErro = "Erro de SQL: " + e.toString();
		}
		fecharConn();
		return lista_planos;
	}
	
	public boolean excluir(long id_planejamento) {
		boolean sucesso = false;
		try {
			abrirConn();
			if (conn != null && !conn.isClosed()) {
				String sql = "DELETE FROM planejamentos_salvos WHERE id=?;";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setLong(1, id_planejamento);
				stmt.executeQuery();
				sucesso = true;
			}
		} catch (SQLException e) {
			msgErro = "Erro de SQL: " + e.toString();
		}
		fecharConn();
		return sucesso;
	}

	public String getMsgErro() {
		return this.msgErro;
	}
}
