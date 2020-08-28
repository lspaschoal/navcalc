package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Aerodromo;
import model.ModelAerodromo;

@WebServlet("/servletEditaAerodromo") // mapeamento do servlet
public class ServletEditaAerodromo extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// Lendo o ID do aeródromo a ser editado
		long idAerodromo = Long.parseLong(request.getParameter("idAerodromo"));
		
		// Carregando o aeródromo
		ModelAerodromo ma = new ModelAerodromo();
		Aerodromo a = ma.getAerodromo(idAerodromo);
		request.setAttribute("aerodromo", a);

		// Convertendo latitude e longitude para graus, minutos e segundos
		String latitude = a.latitudeToString();
		request.setAttribute("latGraus", latitude.subSequence(0, 2));
		request.setAttribute("latMin", latitude.subSequence(3, 5));
		request.setAttribute("latSeg", latitude.subSequence(6, 8));
		request.setAttribute("NS", latitude.substring(9));
		String longitude = a.longitudeToString();
		request.setAttribute("lonGraus", longitude.subSequence(0, 3));
		request.setAttribute("lonMin", longitude.subSequence(4, 6));
		request.setAttribute("lonSeg", longitude.subSequence(7, 9));
		request.setAttribute("WE", latitude.substring(10));
		
		// Definindo as mensagens de erro como vazias
		request.setAttribute("erroIcao", "");
		request.setAttribute("erroNome", "");
		request.setAttribute("erroElevacao", "");
		request.setAttribute("erroLatitude", "");
		request.setAttribute("erroLongitude", "");
		request.setAttribute("status", "");
		
		// Redirecionando para a página de edição
		request.getRequestDispatcher("editaAerodromo.jsp").forward(request, response);
		
	}
}
