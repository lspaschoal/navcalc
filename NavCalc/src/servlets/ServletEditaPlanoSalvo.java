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
import controller.Planejamento;
import controller.PlanejamentoSalvo;
import model.ModelAeronave;
import model.ModelAeronavePersonalizada;
import model.ModelFixo;
import model.ModelPlanejamentoSalvo;

@WebServlet("/servletEditaPlanoSalvo") // mapeamento do servlet
public class ServletEditaPlanoSalvo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// lendo o id do planejamento a ser editado
		long id_planejamento = Long.parseLong(request.getParameter("idPlano"));

		// recuperando do banco de dados
		ModelPlanejamentoSalvo mps = new ModelPlanejamentoSalvo();
		PlanejamentoSalvo ps = mps.getPlanejamento(id_planejamento);

		// gravando os atributos no request para serem lidos pela página de edição
		request.setAttribute("id_planejamento", id_planejamento);
		Planejamento p = ps.gerarPlanejamento();
		request.setAttribute("origem_digitada", p.getOrigem().getIcao());
		request.setAttribute("destino_digitado", p.getDestino().getIcao());
		ModelFixo mf = new ModelFixo();
		if (ps.getRota() != null) {
			String rota[] = ps.getRota().split(",");
			for (int i = 0; i < rota.length; i++) {
				try {
					long idFixo = Long.parseLong(rota[i]);
					request.setAttribute("coordenada_digitada" + i, mf.getFixo(idFixo).getNome());
				} catch (NumberFormatException nfe) {
					request.setAttribute("coordenada_digitada" + i, rota[i]);
				}
			}
			if (rota.length > 5) {
				request.getSession().setAttribute("nfixos", rota.length);
			} else {
				request.getSession().setAttribute("nfixos", "5");
			}
		}else {
			request.getSession().setAttribute("nfixos", "5");
		}

		request.setAttribute("altitude_escolhida", ps.getAltitude());
		if (ps.isAeronave_personalizada()) {
			request.getSession().setAttribute("aeronave_personalizada", "true");
			request.getSession().setAttribute("id_aeronave", ps.getId_aeronave());
		} else {
			request.getSession().setAttribute("aeronave_personalizada", "false");
			request.getSession().setAttribute("id_aeronave", ps.getId_aeronave());
			request.getSession().setAttribute("tipo_acft", p.getAeronave().getTipo());
		}
		request.getSession().setAttribute("navegacao", p);

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

		// redirecionando para a página de planejamento
		request.getRequestDispatcher("planejamento.jsp").forward(request, response);
	}
}
