package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Planejamento;
import controller.PlanejamentoSalvo;
import model.ModelPlanejamentoSalvo;

@WebServlet("/servletGeraKneeboard") // mapeamento do servlet
public class ServletGeraKneeboard extends HttpServlet {

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
		Planejamento p = ps.gerarPlanejamento();
		
		// gravando na sessão
		request.getSession().setAttribute("navegacao", p);
		
		// redirecionando para a página do kneeboard
		request.getRequestDispatcher("kneeboard.jsp").forward(request, response);
	}
}
