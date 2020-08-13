package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.AeronavePersonalizada;
import model.ModelAeronavePersonalizada;

@WebServlet("/servletCadastraAeronavePersonalizada") // mapeamento do servlet
public class ServletCadastraAeronavePersonalizada extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		boolean cadastro_valido = true;
		
		String prefixo = request.getParameter("prefixo");
		if(prefixo == null || prefixo.equals("")) {
			request.setAttribute("erroPrefixo", "*O preenchimento do prefixo é obrigatório.");
			cadastro_valido = false;
		}else {
			request.setAttribute("erroPrefixo", "");
		}
		
		String tipo = request.getParameter("tipo");
		if(prefixo == null || prefixo.equals("")) {
			request.setAttribute("erroTipo", "*O preenchimento do tipo é obrigatório.");
			cadastro_valido = false;
		}else {
			request.setAttribute("erroTipo", "");
		}
		
		int velocidade_cruzeiro = 0;
		if(request.getParameter("velocidade_cruzeiro").equals("")) {
			request.setAttribute("erroVC", "*O preenchimento da velocidade de cruzeiro é obrigatório.");
			cadastro_valido = false;
		}else {
			velocidade_cruzeiro = Integer.parseInt(request.getParameter("velocidade_cruzeiro"));
			if(velocidade_cruzeiro <= 0) {
				request.setAttribute("erroVC", "*A velocidade de cruzeiro tem que ser maior do que 0.");
				cadastro_valido = false;
			}else {
				request.setAttribute("erroVC", "");
			}
		}
		
		int velocidade_subida = 0;
		if(request.getParameter("velocidade_subida").equals("")) {
			request.setAttribute("erroVS", "*O preenchimento da velocidade de subida é obrigatório.");
			cadastro_valido = false;
		}else {
			velocidade_subida = Integer.parseInt(request.getParameter("velocidade_subida"));
			if(velocidade_subida <= 0) {
				request.setAttribute("erroVS", "*A velocidade de subida tem que ser maior do que 0.");
				cadastro_valido = false;
			}else {
				request.setAttribute("erroVS", "");
			}
		}
		
		int velocidade_descida = 0;
		if(request.getParameter("velocidade_descida").equals("")) {
			request.setAttribute("erroVD", "*O preenchimento da velocidade de descida é obrigatório.");
			cadastro_valido = false;
		}else {
			velocidade_descida = Integer.parseInt(request.getParameter("velocidade_descida"));
			if(velocidade_descida <= 0) {
				request.setAttribute("erroVD", "*A velocidade de descida tem que ser maior do que 0.");
				cadastro_valido = false;
			}else {
				request.setAttribute("erroVD", "");
			}
		}
		
		int razao_subida = 0;
		if(request.getParameter("razao_subida").equals("")) {
			request.setAttribute("erroRS", "*O preenchimento da razão de subida é obrigatório.");
			cadastro_valido = false;
		}else {
			razao_subida = Integer.parseInt(request.getParameter("razao_subida"));
			if(razao_subida <= 0) {
				request.setAttribute("erroRS", "*A razão de subida tem que ser maior do que 0.");
				cadastro_valido = false;
			}else {
				request.setAttribute("erroRS", "");
			}
		}
		
		int razao_descida = 0;
		if(request.getParameter("razao_descida").equals("")) {
			request.setAttribute("erroRD", "*O preenchimento da razão de descida é obrigatório.");
			cadastro_valido = false;
		}else {
			razao_descida = Integer.parseInt(request.getParameter("razao_descida"));
			if(razao_descida <= 0) {
				request.setAttribute("erroRD", "*A razão de descida tem que ser maior do que 0.");
				cadastro_valido = false;
			}else {
				request.setAttribute("erroRD", "");
			}
		}
		
		int consumo = 0;
		if(request.getParameter("consumo").equals("")) {
			request.setAttribute("erroConsumo", "*O preenchimento do consumo é obrigatório.");
			cadastro_valido = false;
		}else {
			consumo = Integer.parseInt(request.getParameter("consumo"));
			if(consumo <= 0) {
				request.setAttribute("erroConsumo", "*O consumo tem que ser maior do que 0.");
				cadastro_valido = false;
			}else {
				request.setAttribute("erroConsumo", "");
			}
		}
		
		if(cadastro_valido) {
			AeronavePersonalizada ap = new AeronavePersonalizada();
			ap.setIdUsuario((Long) request.getSession().getAttribute("id"));
			ap.setPrefixo(prefixo);
			ap.setTipo(tipo);
			ap.setVelocidadeCruzeiro(velocidade_cruzeiro);
			ap.setVelocidadeSubida(velocidade_subida);
			ap.setVelocidadeDescida(velocidade_descida);
			ap.setRazaoSubida(razao_subida);
			ap.setRazaoDescida(razao_descida);
			ap.setConsumo(consumo);
			ModelAeronavePersonalizada map = new ModelAeronavePersonalizada();
			if(map.salvar(ap)) {
				request.setAttribute("status", "<p class=\"display-success\">Aeronave cadastrada com sucesso!</p>");
			}else {
				request.setAttribute("status", "<p class=\"display-error\">ERRO: " + map.getMsgErro() + "</p>");
			}
		}else {
			request.setAttribute("status", "");
		}
		request.getRequestDispatcher("cadastrarAeronave.jsp").forward(request, response);
		
	}
}
