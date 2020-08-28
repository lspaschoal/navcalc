package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Aerodromo;
import model.ModelAerodromo;

@WebServlet("/servletAerodromosICAO") // mapeamento do servlet
public class ServletAerodromosICAO extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ModelAerodromo ma = new ModelAerodromo();
		ArrayList<Aerodromo> lista = ma.listaAerodromos("icao");
		request.setAttribute("lista_aerodromos", lista);
		request.getRequestDispatcher("admAerodromos.jsp").forward(request, response);
	}
}