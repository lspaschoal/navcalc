package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/servletCadastroAeronaves") // mapeamento do servlet
public class ServletCadastroAeronaves extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("erroPrefixo", "");
		request.setAttribute("erroTipo", "");
		request.setAttribute("erroVC", "");
		request.setAttribute("erroVS", "");
		request.setAttribute("erroVD", "");
		request.setAttribute("erroRS", "");
		request.setAttribute("erroRD", "");
		request.setAttribute("erroConsumo", "");
		request.setAttribute("status", "");
		request.getRequestDispatcher("cadastrarAeronave.jsp").forward(request, response);
	}
}
