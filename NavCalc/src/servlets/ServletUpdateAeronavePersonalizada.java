package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.AeronavePersonalizada;
import model.ModelAeronavePersonalizada;

@WebServlet("/servletUpdateAeronavePersonalizada") // mapeamento do servlet
public class ServletUpdateAeronavePersonalizada extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// Lendo o ID da aeronave a ser editada
		long idAeronave = Long.parseLong(request.getParameter("idAeronave"));
		
		// Carregando a aeronave
		ModelAeronavePersonalizada map = new ModelAeronavePersonalizada();
		AeronavePersonalizada ap = map.getAeronave(idAeronave);
		
		/*
		 * Validação e atualização dos dados
		 */
		
		// Controle de validade das informações
		boolean cadastro_valido = true;
		
		// Prefixo
		String prefixo = request.getParameter("prefixo");
		if(prefixo == null || prefixo.equals("")) {
			request.setAttribute("erroPrefixo", "*O preenchimento do prefixo é obrigatório.");
			cadastro_valido = false;
		}else {
			ap.setPrefixo(prefixo);
			request.setAttribute("erroPrefixo", "");
		}
		
		// Tipo
		String tipo = request.getParameter("tipo");
		if(prefixo == null || prefixo.equals("")) {
			request.setAttribute("erroTipo", "*O preenchimento do tipo é obrigatório.");
			cadastro_valido = false;
		}else {
			ap.setTipo(tipo);
			request.setAttribute("erroTipo", "");
		}
		
		// Velocidade de Cruzeiro
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
				ap.setVelocidadeCruzeiro(velocidade_cruzeiro);
				request.setAttribute("erroVC", "");
			}
		}
		
		// Velocidade deSubida
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
				ap.setVelocidadeSubida(velocidade_subida);
				request.setAttribute("erroVS", "");
			}
		}
		
		// Veocidade de Descida
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
				ap.setVelocidadeDescida(velocidade_descida);
				request.setAttribute("erroVD", "");
			}
		}
		
		// Razão de Subida
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
				ap.setRazaoSubida(razao_subida);
				request.setAttribute("erroRS", "");
			}
		}
		
		// Razão de Descida
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
				ap.setRazaoDescida(razao_descida);
				request.setAttribute("erroRD", "");
			}
		}
		
		
		// Consumo
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
				ap.setConsumo(consumo);
				request.setAttribute("erroConsumo", "");
			}
		}
		
		/*
		 * Persistência no banco de dados
		 */
		if(cadastro_valido) {
			if(map.atualizar(ap)) {
				request.setAttribute("status", "<p class=\"display-success\">Aeronave atualizada com sucesso!</p>");
			}else {
				request.setAttribute("status", "<p class=\"display-error\">ERRO: " + map.getMsgErro() + "</p>");
			}
		}else {
			request.setAttribute("status", "");
		}
		
		/*
		 * Redirecionamento para a página de edição com status da atualização
		 */
		request.setAttribute("aeronave", ap);
		request.getRequestDispatcher("editaAeronave.jsp").forward(request, response);
		
	}
}