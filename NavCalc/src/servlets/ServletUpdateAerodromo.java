package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Aerodromo;
import model.ModelAerodromo;

@WebServlet("/servletUpdateAerodromo") // mapeamento do servlet
public class ServletUpdateAerodromo extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		long idAerodromo = Long.parseLong(request.getParameter("idAerodromo"));
		
		ModelAerodromo ma = new ModelAerodromo();
		Aerodromo ad = ma.getAerodromo(idAerodromo);
		
		boolean cadastro_valido = true;
		
		
		//ICAO
		String icao = request.getParameter("icao").toString();
		if(icao.length() == 4) {
			ad.setIcao(icao);
			request.setAttribute("erroIcao", "");
		}else {
			cadastro_valido = false;
			request.setAttribute("erroIcao", "Código ICAO inválido");
		}
		//Nome
		String nome = request.getParameter("nome").toString();
		if(nome.equals("")) {
			cadastro_valido = false;
			request.setAttribute("erroNome", "O nome é obrigatório");
		}else {
			ad.setNome(nome);
			request.setAttribute("erroNome", "");
		}
		//Elevacao
		try {
			int elevacao = Integer.parseInt(request.getParameter("elevacao"));
			ad.setElevacao(elevacao);
			request.setAttribute("erroElevacao", "");
		}catch(NumberFormatException nfe) {
			request.setAttribute("erroElevacao", "Número inválido");
			cadastro_valido = false;
		}
		//Latitude
		try {
			int graus = Integer.parseInt(request.getParameter("latGraus"));
			int min = Integer.parseInt(request.getParameter("latMin"));
			int seg = Integer.parseInt(request.getParameter("latSeg"));
			char hemisferio = request.getParameter("NS").charAt(0);
			ad.setLatitude(graus, min, seg, hemisferio);
			request.setAttribute("erroLatitude", "");
		}catch(NumberFormatException nfe) {
			request.setAttribute("erroLatitude", "Latitude inválida");
			cadastro_valido = false;
		}
		//Longitude
		try {
			int graus = Integer.parseInt(request.getParameter("lonGraus"));
			int min = Integer.parseInt(request.getParameter("lonMin"));
			int seg = Integer.parseInt(request.getParameter("lonSeg"));
			char hemisferio = request.getParameter("WE").charAt(0);
			ad.setLongitude(graus, min, seg, hemisferio);
			request.setAttribute("erroLongitude", "");
		}catch(NumberFormatException nfe) {
			request.setAttribute("erroLongitude", "Latitude inválida");
			cadastro_valido = false;
		}
		
		request.setAttribute("aerodromo", ad);
		String latitude = ad.latitudeToString();
		request.setAttribute("latGraus", latitude.subSequence(0, 2));
		request.setAttribute("latMin", latitude.subSequence(3, 5));
		request.setAttribute("latSeg", latitude.subSequence(6, 8));
		String longitude = ad.longitudeToString();
		request.setAttribute("lonGraus", longitude.subSequence(0, 3));
		request.setAttribute("lonMin", longitude.subSequence(4, 6));
		request.setAttribute("lonSeg", longitude.subSequence(7, 9));
		
		if(cadastro_valido) {
			if(ma.atualizaAerodromo(ad)) {
				request.setAttribute("status", "<p class=\"display-success\">Aeródromo atualizado com sucesso!</p>");
			}else {
				request.setAttribute("status", "<p class=\"display-error\">ERRO: " + ma.getMsgErro() + "</p>");
			}
		}else {
			request.setAttribute("status", "");
		}
		request.getRequestDispatcher("editaAerodromo.jsp").forward(request, response);
	}
}
