package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.AeronavePersonalizada;
import controller.Usuario;
import model.ModelAeronavePersonalizada;
import model.ModelUsuario;

@WebServlet("/servletRelatorioAeronavesPersonalizadas") // mapeamento do servlet
public class ServletRelatorioAeronavesPersonalizadas extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ModelUsuario mu = new ModelUsuario();
		ArrayList<Usuario> lista_usuarios = mu.listaUsuariosEmail();
		ModelAeronavePersonalizada map = new ModelAeronavePersonalizada();
		ArrayList<ArrayList<AeronavePersonalizada>> lista_aeronaves = new ArrayList<ArrayList<AeronavePersonalizada>>();
		for(int i = 0; i < lista_usuarios.size(); i++) {
			lista_aeronaves.add(map.aeronavesUsuario(lista_usuarios.get(i).getId()));
		}
		
		request.setAttribute("lista_usuarios", lista_usuarios);
		request.setAttribute("lista_aeronaves", lista_aeronaves);
		request.getRequestDispatcher("admRelatorioAeronavesPersonalizadas.jsp").forward(request, response);
	}
}
