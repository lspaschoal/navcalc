package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ModelPlanejamentoSalvo;

@WebServlet("/servletExcluiPlanoSalvo") // mapeamento do servlet
public class ServletExcluiPlanoSalvo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			// Lendo o id do planejamento a ser excluído
			long id_planejamento = Long.parseLong(request.getParameter("idPlano"));
			
			// Excluindo
			ModelPlanejamentoSalvo mps = new ModelPlanejamentoSalvo();
			mps.excluir(id_planejamento);
			
			//redirecionando para a página de planos salvos
			request.getRequestDispatcher("rotas_salvas.jsp").forward(request, response);
			
		} catch (NullPointerException npe) {
			request.setAttribute("msgErro", "É necessário estar logado para acessar o sistema.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
}
