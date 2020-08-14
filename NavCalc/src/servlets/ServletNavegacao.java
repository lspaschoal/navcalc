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

			// Definindo os campos como vazios
			request.setAttribute("origem_digitada", "");
			request.setAttribute("destino_digitado", "");
			request.setAttribute("coordenada_digitada1", "");
			request.setAttribute("coordenada_digitada2", "");
			request.setAttribute("coordenada_digitada3", "");
			request.setAttribute("coordenada_digitada4", "");
			request.setAttribute("coordenada_digitada5", "");
			request.setAttribute("coordenada_digitada6", "");
			request.setAttribute("coordenada_digitada7", "");
			request.setAttribute("coordenada_digitada8", "");
			request.setAttribute("coordenada_digitada9", "");
			request.setAttribute("coordenada_digitada10", "");
			// Definindo as mensagens de erro como vazias
			request.setAttribute("erroOrigem", "");
			request.setAttribute("erroDestino", "");
			request.setAttribute("erroFixo1", "");
			request.setAttribute("erroFixo2", "");
			request.setAttribute("erroFixo3", "");
			request.setAttribute("erroFixo4", "");
			request.setAttribute("erroFixo5", "");
			request.setAttribute("erroFixo6", "");
			request.setAttribute("erroFixo7", "");
			request.setAttribute("erroFixo8", "");
			request.setAttribute("erroFixo9", "");
			request.setAttribute("erroFixo10", "");

			// Definindo a navegação como vazia
			request.getSession().setAttribute("navegacao", "");

			// Direcionando para o jsp de planejamento
			request.getRequestDispatcher("planejamento.jsp").forward(request, response);
		} catch (NullPointerException npe) {
			request.setAttribute("msgErro", "É necessário estar logado para acessar o sistema.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		

	}
}
