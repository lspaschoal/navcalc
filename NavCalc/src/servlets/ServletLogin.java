package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Usuario;
import model.ModelDados;



@WebServlet("/servletLogin") // mapeamento do servlet
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * inicialização do sistema
		 */
		ModelDados md = new ModelDados();
		// caminhos para os arquivos que contém os dados de aeródromos, fixos e aeronaves padrão do sistema
		String arquivo_aerodromos = getServletContext().getRealPath("\\queries\\query_insert_aerodromos.txt");
		String arquivo_fixos = getServletContext().getRealPath("\\queries\\query_insert_fixos.txt");
		String arquivo_aeronaves = getServletContext().getRealPath("\\queries\\query_insert_aeronaves_padrao.txt");
		// caso a tabela não esteja preenchida, o sistema insere os dados a partir do arquivo txt com a query
		if(md.tabelaVazia("aerodromos")) {
			md.inserir(arquivo_aerodromos);
		}
		if(md.tabelaVazia("fixos")) {
			md.inserir(arquivo_fixos);
		}
		if(md.tabelaVazia("aeronaves_padrao")) {
			md.inserir(arquivo_aeronaves);
		}
		
		// lendo os dados de login
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
				
		
		if(email.equals("") || email == null || senha.equals("") || senha == null) {
			request.setAttribute("msgErro", "Email e senha são obrigatórios para logar.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}else {
			if(email.equals("admin") && senha.equals("admin")) {
				request.getSession().setAttribute("email", "admin");
				request.getRequestDispatcher("admin.jsp").forward(request, response);
			}else {
				Usuario usuario = new Usuario();
				if(usuario.autentica(email, senha)) {
					request.getSession().setAttribute("id", usuario.getId());
					request.getSession().setAttribute("nome", usuario.getNome());
					request.getSession().setAttribute("email", usuario.getEmail());
					request.getRequestDispatcher("painel.jsp").forward(request, response);
				}else {
					request.setAttribute("msgErro", "E-mail ou senha inválidos.");
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}
			}
		}	
	}
}
