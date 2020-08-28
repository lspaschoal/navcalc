package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Fixo;
import model.ModelFixo;

@WebServlet("/servletCadastraFixo") // mapeamento do servlet
public class ServletCadastraFixo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		boolean cadastro_valido = true;

		Fixo f = new Fixo();

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
			if((graus == 90 && (min != 0 || seg != 0)) || (graus > 179 || min > 59 || seg > 59) || (graus < 0 || min < 0 || seg < 0)) {
				request.setAttribute("erroLatitude", "Latitude inválida");
			}else {
				f.setLatitude(graus, min, seg, hemisferio);
				request.setAttribute("erroLatitude", "");
			}
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
			if((graus == 180 && (min != 0 || seg != 0)) || (graus > 179 || min > 59 || seg > 59) || (graus < 0 || min < 0 || seg < 0)) {
				request.setAttribute("erroLongitude", "Longitude inválida");
			}else {
				f.setLongitude(graus, min, seg, hemisferio);
				request.setAttribute("erroLongitude", "");
			}
		} catch (NumberFormatException nfe) {
			request.setAttribute("erroLongitude", "Longitude inválida");
			cadastro_valido = false;
		}
		
		request.setAttribute("nome", nome);
		request.setAttribute("latGraus", request.getParameter("latGraus"));
		request.setAttribute("latMin", request.getParameter("latMin"));
		request.setAttribute("latSeg", request.getParameter("latSeg"));
		request.setAttribute("NS", request.getParameter("NS"));
		request.setAttribute("lonGraus", request.getParameter("lonGraus"));
		request.setAttribute("lonMin", request.getParameter("lonMin"));
		request.setAttribute("lonSeg", request.getParameter("lonSeg"));
		request.setAttribute("WE", request.getParameter("WE"));
		
		if(cadastro_valido) {
			ModelFixo mf = new ModelFixo();
			if(mf.cadastraFixo(f)) {
				request.setAttribute("status", "<p class=\"display-success\">Aeródromo atualizado com sucesso!</p>");
			}else {
				request.setAttribute("status", "<p class=\"display-error\">ERRO: " + mf.getMsgErro() + "</p>");
			}
		}else {
			request.setAttribute("status", "");
		}
		request.getRequestDispatcher("cadastrarFixo.jsp").forward(request, response);

	}
}