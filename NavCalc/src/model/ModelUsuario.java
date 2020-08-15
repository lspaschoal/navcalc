package model;

//import java.util.List;
//import javax.persistence.Query;
//import org.hibernate.Session;
//import controller.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import controller.Usuario;

public class ModelUsuario {
	private Connection conn = null;
	private String msgErro = null;
	
	public ModelUsuario(){
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
	
	public boolean usuarioExistente(String email) {
		Long id = 0L;
		try {
			abrirConn();
			if (conn != null && !conn.isClosed()) {
				String sql = "SELECT id FROM usuarios WHERE email = ?;";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, email);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					id = rs.getLong(1);
				}
			}
		} catch (SQLException e) {
			msgErro = "Erro de SQL: " + e.toString();
		}
		fecharConn();
		return (id > 0L);
	}
	
	public Long verificarLogin(String usuario, String senha) {
		Long id = -1L;
		try {
			abrirConn();
			if (conn != null && !conn.isClosed()) {
				String sql = "SELECT id FROM usuarios WHERE email = ? and senha = ?;";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, usuario);
				stmt.setString(2, senha);
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
	
	public boolean salvar(Usuario user) {
		try {
			abrirConn();
			if (conn != null && !conn.isClosed()) {
				String sql = "INSERT INTO usuarios (nome,sobrenome,dt_nascimento,email,senha) values (?,?,?,?,?);";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, user.getNome());
				stmt.setString(2, user.getSobrenome());
				stmt.setString(3, dataParaSql(user.getDtNascimento()));
				stmt.setString(4, user.getEmail());
				stmt.setString(5, user.getSenha());
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
	
	public Usuario getUsuario(long id) {
		Usuario user = null;
		try {
			abrirConn();
			if (conn != null && !conn.isClosed()) {
				String sql = "SELECT * FROM usuarios WHERE id=?;";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setLong(1, id);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					user = new Usuario();
					user.setId(id);
					user.setDtNascimento(sqlParaData(rs.getString(2)));
					user.setEmail(rs.getString(3));
					user.setNome(rs.getString(4));
					user.setSobrenome(rs.getString(6));
				}
			}
		} catch (SQLException e) {
			msgErro = "Erro de SQL: " + e.toString();
		}
		fecharConn();
		return user;
	}
	
	public Usuario getUsuario(String email) {
		Usuario user = null;
		try {
			abrirConn();
			if (conn != null && !conn.isClosed()) {
				String sql = "SELECT * FROM usuarios WHERE email=?;";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, email);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					user = new Usuario();
					user.setId(1);
					user.setDtNascimento(sqlParaData(rs.getString(2)));
					user.setEmail(rs.getString(3));
					user.setNome(rs.getString(4));
					user.setSobrenome(rs.getString(6));
				}
			}
		} catch (SQLException e) {
			msgErro = "Erro de SQL: " + e.toString();
		}
		fecharConn();
		return user;
	}
	
	public String dataParaSql(LocalDate dt) {
		return dt.getYear() + "-" + dt.getMonthValue() + "-" + dt.getDayOfMonth();
	}
	
	public LocalDate sqlParaData(String data) {
		int ano = Integer.parseInt(data.substring(0, 4));
		int mes = Integer.parseInt(data.substring(5, 7));
		int dia = Integer.parseInt(data.substring(8));
		return LocalDate.of(ano, mes, dia);
	}
//
//	public boolean salvar(Usuario user) {
//		Session session = HibernateUtil.abrirSession();
//		try {
//			session.saveOrUpdate(user);
//		} catch (Exception e) {
//			msgErro = "Erro: " + e.getMessage();
//			return false;
//		}
//		HibernateUtil.fecharSession();
//		return true;
//	}
//
//	public Long verificarLogin(String usuario, String senha) {
//		Session session = HibernateUtil.abrirSession();
//		session.beginTransaction();
//		try {
//			String sql = "SELECT id FROM usuarios WHERE email = :email and senha = :senha";
//			Query query = session.createQuery(sql);
//			query.setParameter("email", usuario);
//			query.setParameter("senha", senha);
//			@SuppressWarnings("rawtypes")
//			List list = query.getResultList();
//			if (list == null || list.size() == 0) {
//				return (long) -1;
//			} else {
//				return Long.parseLong(list.get(0).toString());
//			}
//		} catch (Exception e) {
//			System.out.println("Erro no verificarLogin(): "+e.toString());
//			return (long) -1;
//		}
//	}
//	
//	public Usuario getUsuario(long id) {
//		Session session = HibernateUtil.abrirSession();
//		session.beginTransaction();
//		return session.get(Usuario.class, id);
//	}
//
	public String getMsgErro() {
		return msgErro;
	}
	
	

}
