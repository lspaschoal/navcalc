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

@WebServlet("/servletExcluiAeronave") // mapeamento do servlet
public class ServletExcluiAeronave extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Lendo o ID da aeronave a ser excluida
			long idAeronave = Long.parseLong(request.getParameter("idAeronave"));

			// Excluindo a aeronave
			ModelAeronavePersonalizada map = new ModelAeronavePersonalizada();
			map.excluirAeronavePersonalizada(idAeronave);

			// Recarregando a lista de aeronaves
			ArrayList<AeronavePersonalizada> listaAeronavesUsuario = new ArrayList<AeronavePersonalizada>();
			listaAeronavesUsuario = map
					.aeronavesUsuario(Long.parseLong(request.getSession().getAttribute("id").toString()));
			request.setAttribute("listaAeronavesUsuario", listaAeronavesUsuario);

			// Redirecionando para a página de aeronaves
			request.getRequestDispatcher("aeronaves.jsp").forward(request, response);
		} catch (NullPointerException npe) {
			request.setAttribute("msgErro", "É necessário estar logado para acessar o sistema.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		
	}
}
