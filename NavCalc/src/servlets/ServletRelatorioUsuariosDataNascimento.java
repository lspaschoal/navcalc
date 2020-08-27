package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Usuario;
import model.ModelUsuario;

@WebServlet("/servletRelatorioUsuariosDataNascimento") // mapeamento do servlet
public class ServletRelatorioUsuariosDataNascimento extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ModelUsuario mu = new ModelUsuario();
		ArrayList<Usuario> lista = mu.listaUsuariosDN();
		request.setAttribute("lista_usuarios", lista);
		request.getRequestDispatcher("admRelatorioUsuarios.jsp").forward(request, response);
	}
}
