package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.Aeronave;

public class ModelAeronave {
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
	
	public boolean salvar(Aeronave a) {
		try {
			abrirConn();
			if (conn != null && !conn.isClosed()) {
				String sql = "INSERT INTO aeronaves_padrao (tipo,velocidadeCruzeiro,velocidadeSubida,velocidadeDescida,razaoSubida,razaoDescida,consumo) values (?,?,?,?,?,?,?);";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, a.getTipo());
				stmt.setInt(2, a.getVelocidadeCruzeiro());
				stmt.setInt(3, a.getVelocidadeSubida());
				stmt.setInt(4, a.getVelocidadeDescida());
				stmt.setInt(5, a.getRazaoSubida());
				stmt.setInt(6, a.getRazaoDescida());
				stmt.setInt(7, a.getConsumo());
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
	
	public ArrayList<Aeronave> listaAeronaves(){
		ArrayList<Aeronave> lista = new ArrayList<Aeronave>();
		try {
			abrirConn();
			if (conn != null && !conn.isClosed()) {
				String sql = "SELECT tipo,velocidadeCruzeiro,velocidadeSubida,velocidadeDescida,razaoSubida,razaoDescida,consumo,id FROM aeronaves_padrao ORDER BY tipo;";
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					Aeronave acft = new Aeronave();
					acft.setTipo(rs.getString(1));
					acft.setVelocidadeCruzeiro(rs.getInt(2));
					acft.setVelocidadeSubida(rs.getInt(3));
					acft.setVelocidadeDescida(rs.getInt(4));
					acft.setRazaoSubida(rs.getInt(5));
					acft.setRazaoDescida(rs.getInt(6));
					acft.setConsumo(rs.getInt(7));
					acft.setId(rs.getLong(8));
					lista.add(acft);
				}
			}
		} catch (SQLException e) {
			msgErro = "Erro de SQL: " + e.toString();
		}
		fecharConn();
		return lista;
	}
	
	public Aeronave getAeronave(String tipo){
		Aeronave acft = null;
		try {
			abrirConn();
			if (conn != null && !conn.isClosed()) {
				String sql = "SELECT tipo,velocidadeCruzeiro,velocidadeSubida,velocidadeDescida,razaoSubida,razaoDescida,consumo,id FROM aeronaves_padrao WHERE tipo=?;";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, tipo);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					acft = new Aeronave();
					acft.setTipo(rs.getString(1));
					acft.setVelocidadeCruzeiro(rs.getInt(2));
					acft.setVelocidadeSubida(rs.getInt(3));
					acft.setVelocidadeDescida(rs.getInt(4));
					acft.setRazaoSubida(rs.getInt(5));
					acft.setRazaoDescida(rs.getInt(6));
					acft.setConsumo(rs.getInt(7));
					acft.setId(rs.getLong(8));
				}
			}
		} catch (SQLException e) {
			msgErro = "Erro de SQL: " + e.toString();
		}
		fecharConn();
		return acft;
	}
	
	public Aeronave getAeronave(long id_aeronave) {
		Aeronave acft = null;
		try {
			abrirConn();
			if (conn != null && !conn.isClosed()) {
				String sql = "SELECT tipo,velocidadeCruzeiro,velocidadeSubida,velocidadeDescida,razaoSubida,razaoDescida,consumo,id FROM aeronaves_padrao WHERE id=?;";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setLong(1, id_aeronave);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					acft = new Aeronave();
					acft.setTipo(rs.getString(1));
					acft.setVelocidadeCruzeiro(rs.getInt(2));
					acft.setVelocidadeSubida(rs.getInt(3));
					acft.setVelocidadeDescida(rs.getInt(4));
					acft.setRazaoSubida(rs.getInt(5));
					acft.setRazaoDescida(rs.getInt(6));
					acft.setConsumo(rs.getInt(7));
					acft.setId(rs.getLong(8));
				}
			}
		} catch (SQLException e) {
			msgErro = "Erro de SQL: " + e.toString();
		}
		fecharConn();
		return acft;
	}
	
	
	public boolean excluirAeronave(long idAeronave) {
		boolean sucesso = false;
		try {
			abrirConn();
			if (conn != null && !conn.isClosed()) {
				String sql = "DELETE FROM aeronaves_padrao WHERE id=?;";
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
