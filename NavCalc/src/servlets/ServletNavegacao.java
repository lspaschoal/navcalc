package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Aeronave;
import controller.AeronavePersonalizada;
import model.ModelAeronave;
import model.ModelAeronavePersonalizada;

@WebServlet("/servletNavegacao") // mapeamento do servlet
public class ServletNavegacao extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// Carregando lista de aeronaves do usuário
			ModelAeronavePersonalizada map = new ModelAeronavePersonalizada();
			ArrayList<AeronavePersonalizada> listaAeronavesUsuario = new ArrayList<AeronavePersonalizada>();
			listaAeronavesUsuario = map
					.aeronavesUsuario(Long.parseLong(request.getSession().getAttribute("id").toString()));
			request.getSession().setAttribute("listaAeronavesUsuario", listaAeronavesUsuario);

			// Carregando lista de aeronaves padrão
			ModelAeronave ma = new ModelAeronave();
			ArrayList<Aeronave> listaAeronavesPadrao = new ArrayList<Aeronave>();
			listaAeronavesPadrao = ma.listaAeronaves();
			request.getSession().setAttribute("listaAeronavesPadrao", listaAeronavesPadrao);
			
			// Iniciando o número de campos para fixos como 5
			request.getSession().setAttribute("nfixos", "5");

			// Definindo a navegação como vazia
			request.getSession().setAttribute("navegacao", "");

			// Direcionando para o jsp de planejamento
			request.getRequestDispatcher("planejamento2.jsp").forward(request, response);
		} catch (NullPointerException npe) {
			request.setAttribute("msgErro", "É necessário estar logado para acessar o sistema.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		

	}
}
