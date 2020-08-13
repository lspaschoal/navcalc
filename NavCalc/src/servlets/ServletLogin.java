package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Usuario;



@WebServlet("/servletLogin") // mapeamento do servlet
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		
		if(email.equals("") || email == null || senha.equals("") || senha == null) {
			request.setAttribute("msgErro", "Email e senha são obrigatórios para logar.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
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
