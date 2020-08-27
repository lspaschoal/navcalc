package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Aeronave;
import model.ModelAeronave;

@WebServlet("/servletEditaAeronavePadrao") // mapeamento do servlet
public class ServletEditaAeronavePadrao extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// Lendo o ID da aeronave a ser editada
		long idAeronave = Long.parseLong(request.getParameter("idAeronave"));
		
		// Carregando a aeronave
		ModelAeronave ma = new ModelAeronave();
		Aeronave a = ma.getAeronave(idAeronave);
		request.setAttribute("aeronave", a);
		
		// Definindo as mensagens de erro como vazias
		request.setAttribute("erroPrefixo", "");
		request.setAttribute("erroTipo", "");
		request.setAttribute("erroVC", "");
		request.setAttribute("erroVS", "");
		request.setAttribute("erroVD", "");
		request.setAttribute("erroRS", "");
		request.setAttribute("erroRD", "");
		request.setAttribute("erroConsumo", "");
		request.setAttribute("status", "");
		
		// Redirecionando para a página de edição
		request.getRequestDispatcher("editaAeronavePadrao.jsp").forward(request, response);
		
	}
}
