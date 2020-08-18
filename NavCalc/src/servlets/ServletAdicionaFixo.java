package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/servletAdicionaFixo") // mapeamento do servlet
public class ServletAdicionaFixo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Lendo a origem e erro de origem já existentes
		request.setAttribute("origem_digitada", request.getParameter("origem").toUpperCase());
		request.setAttribute("erroOrigem", request.getParameter("erroOrigem"));
		
		// Lendo os fixos já digitados
		int nfixos = Integer.parseInt(request.getSession().getAttribute("nfixos").toString());
		for (int i = 0; i < nfixos; i++) {
			request.setAttribute("coordenada_digitada"+i,request.getParameter("fixo"+i).toUpperCase());	
		}
		// Aumentando o número de campos
		nfixos++;
		request.getSession().setAttribute("nfixos",nfixos);
		
		// Lendo a altitude escolhida
		request.setAttribute("altitude_escolhida",request.getParameter("altitude"));
		
		// Lendo o destino já digitado
		request.setAttribute("destino_digitado", request.getParameter("destino").toUpperCase());
		
		// Direcionando para o jsp de planejamento
		request.getRequestDispatcher("planejamento.jsp").forward(request, response);
	}
}
