package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Fixo;
import model.ModelFixo;

@WebServlet("/servletFixoLongitude") // mapeamento do servlet
public class ServletFixosLongitude extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ModelFixo mf = new ModelFixo();
		ArrayList<Fixo> lista = mf.listaFixosPor("longitude");
		request.setAttribute("lista_fixos", lista);
		request.getRequestDispatcher("admFixos.jsp").forward(request, response);
	}
}