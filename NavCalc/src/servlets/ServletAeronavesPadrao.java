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

@WebServlet("/servletAeronavesPadrao") // mapeamento do servlet
public class ServletAeronavesPadrao extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ModelAeronave ma = new ModelAeronave();
		ArrayList<Aeronave> lista = ma.listaAeronaves();
		request.setAttribute("lista_aeronaves", lista);
		request.getRequestDispatcher("admAeronaves.jsp").forward(request, response);
	}
}