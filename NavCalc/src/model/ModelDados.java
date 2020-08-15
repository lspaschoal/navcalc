package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class ModelDados {
	/*
	 * classe utilizada para carregar o bando de dados com os aeródromos, fixos e aeronaves padrão
	 */
	private Connection conn = null;
	private String msgErro;
	
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
	
	public ModelDados() {
		try {
			// Utilizando o Hibernate para a persistência das classes
			Session session;
			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			session = sessionFactory.openSession();
			session.close();
		} catch (HibernateException e) {
			msgErro = ("Erro de Hibernate: " + e.toString());
		}
	}
	
	public boolean inserir(String arquivo) {
		abrirConn();
		try {
			if (conn != null && !conn.isClosed()) {
				String sql = lerArquivo(arquivo);
				PreparedStatement stmt = conn.prepareStatement(sql);			
				ResultSet rs = stmt.executeQuery();
				if(rs.next()) {
					fecharConn();
					return true;
				}
			}
		} catch (SQLException e) {
			msgErro = "Erro de SQL: " + e.toString();
		}
		fecharConn();
		return false;
	}
	
	public boolean tabelaVazia(String nome_tabela) {
		int total = 0;
		try {
			if (conn != null && !conn.isClosed()) {
				String sql = "SELECT count(id) FROM ?;";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, nome_tabela);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					total = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			msgErro = "Erro de SQL: " + e.toString();
		}
		return (total == 0);
	}
	
	private String lerArquivo(String arquivo) {
		String texto = "";
		try {
			File file = new File(arquivo);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String st;
			while ((st = br.readLine()) != null) {
				texto += st + "\n";
			}
			br.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return texto;
	}
	

	public String getMsgErro() {
		return msgErro;
	}
	
	
}
