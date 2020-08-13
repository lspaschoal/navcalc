package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import controller.AeronavePersonalizada;

public class ModelAeronavePersonalizada {
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
	
	public boolean salvar(AeronavePersonalizada ap) {
		try {
			abrirConn();
			if (conn != null && !conn.isClosed()) {
				String sql = "INSERT INTO aeronaves_personalizadas (prefixo,tipo,velocidadeCruzeiro,velocidadeSubida,velocidadeDescida,razaoSubida,razaoDescida,consumo,id_usuario) values (?,?,?,?,?,?,?,?,?);";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, ap.getPrefixo());
				stmt.setString(2, ap.getTipo());
				stmt.setInt(3, ap.getVelocidadeCruzeiro());
				stmt.setInt(4, ap.getVelocidadeSubida());
				stmt.setInt(5, ap.getVelocidadeDescida());
				stmt.setInt(6, ap.getRazaoSubida());
				stmt.setInt(7, ap.getRazaoDescida());
				stmt.setLong(8, ap.getConsumo());
				stmt.setLong(9, ap.getIdUsuario());
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
	
	public ArrayList<AeronavePersonalizada> aeronavesUsuario(long idUsuario){
		ArrayList<AeronavePersonalizada> listaAeronaves = new ArrayList<AeronavePersonalizada>();
		try {
			abrirConn();
			if (conn != null && !conn.isClosed()) {
				String sql = "SELECT prefixo,tipo,velocidadeCruzeiro,velocidadeSubida,velocidadeDescida,razaoSubida,razaoDescida,consumo,id FROM aeronaves_personalizadas WHERE id_usuario=?;";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setLong(1, idUsuario);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					AeronavePersonalizada ap = new AeronavePersonalizada();
					ap.setPrefixo(rs.getString(1));
					ap.setTipo(rs.getString(2));
					ap.setVelocidadeCruzeiro(rs.getInt(3));
					ap.setVelocidadeSubida(rs.getInt(4));
					ap.setVelocidadeDescida(rs.getInt(5));
					ap.setRazaoSubida(rs.getInt(6));
					ap.setRazaoDescida(rs.getInt(7));
					ap.setId(rs.getInt(8));
					ap.setId(rs.getLong(9));
					listaAeronaves.add(ap);
				}
			}
		} catch (SQLException e) {
			msgErro = "Erro de SQL: " + e.toString();
		}
		fecharConn();
		return listaAeronaves;
	}
	
	public AeronavePersonalizada getAeronave(long id){
		AeronavePersonalizada ap = new AeronavePersonalizada();
		try {
			abrirConn();
			if (conn != null && !conn.isClosed()) {
				String sql = "SELECT prefixo,tipo,velocidadeCruzeiro,velocidadeSubida,velocidadeDescida,razaoSubida,razaoDescida,consumo,id FROM aeronaves_personalizadas WHERE id=?;";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setLong(1, id);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					ap.setPrefixo(rs.getString(1));
					ap.setTipo(rs.getString(2));
					ap.setVelocidadeCruzeiro(rs.getInt(3));
					ap.setVelocidadeSubida(rs.getInt(4));
					ap.setVelocidadeDescida(rs.getInt(5));
					ap.setRazaoSubida(rs.getInt(6));
					ap.setRazaoDescida(rs.getInt(7));
					ap.setConsumo(rs.getInt(8));
					ap.setId(rs.getLong(9));
				}
			}
		} catch (SQLException e) {
			msgErro = "Erro de SQL: " + e.toString();
		}
		fecharConn();
		return ap;
	}
	
	public boolean excluirAeronavePersonalizada(long idAeronave) {
		boolean sucesso = false;
		try {
			abrirConn();
			if (conn != null && !conn.isClosed()) {
				String sql = "DELETE FROM aeronaves_personalizadas WHERE id=?;";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setLong(1, idAeronave);
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
		return msgErro;
	}
	
	
}
