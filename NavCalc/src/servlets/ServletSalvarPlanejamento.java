package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Planejamento;
import controller.PlanejamentoSalvo;

@WebServlet("/servletSalvarPlanejamento") // mapeamento do servlet
public class ServletSalvarPlanejamento extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PlanejamentoSalvo ps = new PlanejamentoSalvo();
		ps.setIdUsuario(Long.parseLong(request.getSession().getAttribute("id").toString()));
		ps.setAeronave_personalizada(request.getSession().getAttribute("aeronave_personalizada").toString().equals("true"));
		ps.setId_aeronave((Long) request.getSession().getAttribute("id_aeronave"));
		Planejamento planejamento = (Planejamento) request.getSession().getAttribute("navegacao");
		
		if(ps.salvar(planejamento)) {
			request.setAttribute("salvo", "Planejamento salvo com sucesso!");
		}else {
			request.setAttribute("salvo", ps.getMsgErro());
		}
		
		request.getRequestDispatcher("planejamento.jsp").forward(request, response);
	}

}
