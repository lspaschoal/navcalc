package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Aeronave;
import model.ModelAeronave;

@WebServlet("/servletExcluiAeronavePadrao") // mapeamento do servlet
public class ServletExcluiAeronavePadrao extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

			// Lendo o ID da aeronave a ser excluida
			long idAeronave = Long.parseLong(request.getParameter("idAeronave"));

			// Excluindo a aeronave
			ModelAeronave ma = new ModelAeronave();
			ma.excluirAeronave(idAeronave);

			// Recarregando a lista de aeronaves
			ArrayList<Aeronave> listaAeronaves = new ArrayList<Aeronave>();
			listaAeronaves = ma.listaAeronaves();
			request.setAttribute("lista_aeronaves", listaAeronaves);

			// Redirecionando para a página de aeronaves
			request.getRequestDispatcher("admAeronaves.jsp").forward(request, response);
		
	}
}
