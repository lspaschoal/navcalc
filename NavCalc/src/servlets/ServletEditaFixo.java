package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Fixo;
import model.ModelFixo;

@WebServlet("/servletEditaFixo") // mapeamento do servlet
public class ServletEditaFixo extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// Lendo o ID do aeródromo a ser editado
		long idFixo = Long.parseLong(request.getParameter("idFixo"));
		
		// Carregando o Fixo
		ModelFixo mf = new ModelFixo();
		Fixo f = mf.getFixo(idFixo);
		request.setAttribute("fixo", f);

		// Convertendo latitude e longitude para graus, minutos e segundos
		String latitude = f.latitudeToString();
		request.setAttribute("latGraus", latitude.subSequence(0, 2));
		request.setAttribute("latMin", latitude.subSequence(3, 5));
		request.setAttribute("latSeg", latitude.subSequence(6, 8));
		request.setAttribute("NS", latitude.substring(9));
		String longitude = f.longitudeToString();
		request.setAttribute("lonGraus", longitude.subSequence(0, 3));
		request.setAttribute("lonMin", longitude.subSequence(4, 6));
		request.setAttribute("lonSeg", longitude.subSequence(7, 9));
		request.setAttribute("WE", latitude.substring(10));
		
		// Definindo as mensagens de erro como vazias
		request.setAttribute("erroNome", "");
		request.setAttribute("erroLatitude", "");
		request.setAttribute("erroLongitude", "");
		request.setAttribute("status", "");
		
		// Redirecionando para a página de edição
		request.getRequestDispatcher("editaFixo.jsp").forward(request, response);
		
	}
}