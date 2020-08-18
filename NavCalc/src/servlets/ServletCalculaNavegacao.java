package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Aerodromo;
import controller.Aeronave;
import controller.AeronavePersonalizada;
import controller.Fixo;
import controller.Planejamento;
import model.ModelAeronave;
import model.ModelAeronavePersonalizada;
import model.ModelFixo;

@WebServlet("/servletCalculaNavegacao") // mapeamento do servlet
public class ServletCalculaNavegacao extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			Planejamento plan = new Planejamento();
			boolean planejamento_valido = true;

			// Lendo a origem
			String origem_digitada = request.getParameter("origem").toUpperCase();
			request.setAttribute("origem_digitada", origem_digitada);
			Aerodromo origem = new Aerodromo().getAerodromo(origem_digitada);
			if (origem != null) {
				plan.setOrigem(origem);
			} else {
				planejamento_valido = false;
				request.getSession().setAttribute("erroOrigem", "Aeródromo de origem inválido.");
			}

			// Lendo o destino
			String destino_digitado = request.getParameter("destino").toUpperCase();
			request.setAttribute("destino_digitado", destino_digitado);
			Aerodromo destino = new Aerodromo().getAerodromo(destino_digitado);
			if (destino != null) {
				plan.setDestino(destino);
			} else {
				planejamento_valido = false;
				request.getSession().setAttribute("erroDestino", "Aeródromo de destino inválido.");
			}

			// Lendo os fixos da rota
			ArrayList<Fixo> rota = new ArrayList<Fixo>();
			ModelFixo mf = new ModelFixo();
			Fixo f;
			ArrayList<Fixo> busca = new ArrayList<Fixo>();
			int nfixos = Integer.parseInt(request.getSession().getAttribute("nfixos").toString());
			for (int i = 0; i < nfixos; i++) {
				String coordenada = request.getParameter("fixo" + i).toUpperCase();
				request.setAttribute("coordenada_digitada" + i, coordenada);
				if (coordenada != null && !coordenada.equals("")) {
					f = null;
					busca = mf.listaFixos(coordenada);
					if (busca.size() > 0) {
						f = busca.get(0);
						rota.add(f);
						request.setAttribute("erroFixo" + i, "");
					} else {
						try {
							int latgraus = Integer.parseInt(coordenada.substring(0, 2));
							int latmin = Integer.parseInt(coordenada.substring(2, 4));
							int latseg = Integer.parseInt(coordenada.substring(4, 6));
							char lathemisferio = coordenada.charAt(6);
							int longraus = Integer.parseInt(coordenada.substring(7, 10));
							int lonmin = Integer.parseInt(coordenada.substring(10, 12));
							int lonseg = Integer.parseInt(coordenada.substring(12, 14));
							char lonhemisferio = coordenada.charAt(14);
							f = new Fixo(latgraus, latmin, latseg, lathemisferio, longraus, lonmin, lonseg,
									lonhemisferio);
							rota.add(f);
						} catch (NumberFormatException nfe) {

						}
						if (f == null) {
							planejamento_valido = false;
							request.getSession().setAttribute("erroFixo" + i, "Coordenada inválida");
						}
					}
				} 
			}
			plan.setRota(rota);

			// Lendo a altitude
			plan.setAltitude(Integer.parseInt(request.getParameter("altitude")));
			request.setAttribute("altitude_escolhida", request.getParameter("altitude"));

			// Lendo a aeronave
			Aeronave acft = null;
			long indiceAeronave = 0L;
			try {
				indiceAeronave = Long.parseLong(request.getParameter("aeronave"));
			} catch (Exception e) {

			}
			if (indiceAeronave > 0L) {
				AeronavePersonalizada ap = new AeronavePersonalizada();
				ModelAeronavePersonalizada map = new ModelAeronavePersonalizada();
				ap = map.getAeronave(indiceAeronave);
				acft = new Aeronave();
				acft.setTipo(ap.getTipo());
				acft.setVelocidadeCruzeiro(ap.getVelocidadeCruzeiro());
				acft.setVelocidadeSubida(ap.getVelocidadeSubida());
				acft.setVelocidadeDescida(ap.getVelocidadeDescida());
				acft.setRazaoSubida(ap.getRazaoSubida());
				acft.setRazaoDescida(ap.getRazaoDescida());
				acft.setConsumo(ap.getConsumo());
			} else {
				ModelAeronave ma = new ModelAeronave();
				acft = ma.getAeronave(request.getParameter("aeronave"));
			}
			if (acft != null) {
				plan.setAeronave(acft);
			}

			// Calcula a navegação
			if (planejamento_valido) {
				plan.calcular();
				request.getSession().setAttribute("navegacao", plan);
			}

			request.getRequestDispatcher("planejamento.jsp").forward(request, response);
		} catch (NullPointerException npe) {
			request.setAttribute("msgErro", "É necessário estar logado para acessar o sistema.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}

	}
}
