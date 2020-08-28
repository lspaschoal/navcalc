package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Fixo;
import model.ModelFixo;

@WebServlet("/servletExcluiFixo") // mapeamento do servlet
public class ServletExcluiFixo extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long idFixo = Long.parseLong(request.getParameter("idFixo"));

		ModelFixo mf = new ModelFixo();
		mf.excluirFixo(idFixo);
		ArrayList<Fixo> lista_fixos = new ArrayList<Fixo>();
		lista_fixos = mf.listaFixosPor("nome");
		request.setAttribute("lista_fixos", lista_fixos);

		request.getRequestDispatcher("admFixos.jsp").forward(request, response);

	}
}