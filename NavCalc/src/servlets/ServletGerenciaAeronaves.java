package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.AeronavePersonalizada;
import model.ModelAeronavePersonalizada;

@WebServlet("/servletGerenciaAeronaves") // mapeamento do servlet
public class ServletGerenciaAeronaves extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			ModelAeronavePersonalizada map = new ModelAeronavePersonalizada();
			ArrayList<AeronavePersonalizada> listaAeronavesUsuario = new ArrayList<AeronavePersonalizada>();
			listaAeronavesUsuario = map.aeronavesUsuario(Long.parseLong(request.getSession().getAttribute("id").toString()));
			request.setAttribute("listaAeronavesUsuario", listaAeronavesUsuario);
			request.getRequestDispatcher("aeronaves.jsp").forward(request, response);
		}catch(NullPointerException npe) {
			request.setAttribute("msgErro", "É necessário estar logado para acessar o sistema.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
			

	}
}
