package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Fixo;
import model.ModelFixo;

@WebServlet("/servletUpdateFixo") // mapeamento do servlet
public class ServletUpdateFixo extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long idFixo = Long.parseLong(request.getParameter("idFixo"));

		ModelFixo mf = new ModelFixo();
		Fixo f = mf.getFixo(idFixo);

		boolean cadastro_valido = true;

		// Nome
		String nome = request.getParameter("nome").toString();
		if (nome.equals("")) {
			cadastro_valido = false;
			request.setAttribute("erroNome", "O nome é obrigatório");
		} else {
			f.setNome(nome);
			request.setAttribute("erroNome", "");
		}

		// Latitude
		try {
			int graus = Integer.parseInt(request.getParameter("latGraus"));
			int min = Integer.parseInt(request.getParameter("latMin"));
			int seg = Integer.parseInt(request.getParameter("latSeg"));
			char hemisferio = request.getParameter("NS").charAt(0);
			f.setLatitude(graus, min, seg, hemisferio);
			request.setAttribute("erroLatitude", "");
		} catch (NumberFormatException nfe) {
			request.setAttribute("erroLatitude", "Latitude inválida");
			cadastro_valido = false;
		}
		// Longitude
		try {
			int graus = Integer.parseInt(request.getParameter("lonGraus"));
			int min = Integer.parseInt(request.getParameter("lonMin"));
			int seg = Integer.parseInt(request.getParameter("lonSeg"));
			char hemisferio = request.getParameter("WE").charAt(0);
			f.setLongitude(graus, min, seg, hemisferio);
			request.setAttribute("erroLongitude", "");
		} catch (NumberFormatException nfe) {
			request.setAttribute("erroLongitude", "Latitude inválida");
			cadastro_valido = false;
		}
	
		request.setAttribute("fixo", f);
		String latitude = f.latitudeToString();
		request.setAttribute("latGraus", latitude.substring(0, 2));
		request.setAttribute("latMin", latitude.substring(3, 5));
		request.setAttribute("latSeg", latitude.substring(6, 8));
		request.setAttribute("NS", latitude.substring(9));
		String longitude = f.longitudeToString();
		request.setAttribute("lonGraus", longitude.substring(0, 3));
		request.setAttribute("lonMin", longitude.substring(4, 6));
		request.setAttribute("lonSeg", longitude.substring(7, 9));
		request.setAttribute("WE", latitude.substring(10));
		
		if(cadastro_valido) {
			if(mf.atualizaFixo(f)) {
				request.setAttribute("status", "<p class=\"display-success\">Fixo atualizado com sucesso!</p>");
			}else {
				request.setAttribute("status", "<p class=\"display-error\">ERRO: " + mf.getMsgErro() + "</p>");
			}
		}else {
			request.setAttribute("status", "");
		}
		request.getRequestDispatcher("editaFixo.jsp").forward(request, response);
	}
}
