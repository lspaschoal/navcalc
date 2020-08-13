package servlets;

import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Usuario;

@WebServlet("/servletCadastro") // mapeamento do servlet
public class ServletCadastro extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Usuario user;
		boolean cadastro_valido = true;
		// lendo on nome
		String nome = request.getParameter("nome");
		if(nome == null || nome.equals("")) {
			request.setAttribute("erroNome", "*O preenchimento do nome é obrigatório.");
			cadastro_valido = false;
		}else {
			request.setAttribute("erroNome", "");
		}
		// lendo o sobrenome
		String sobrenome = request.getParameter("sobrenome");
		if(sobrenome == null || sobrenome.equals("")) {
			request.setAttribute("erroSobreNome", "*O preenchimento do sobrenome é obrigatório.");
			cadastro_valido = false;
		}else {
			request.setAttribute("erroSobreNome", "");
		}
		// lendo a data de nascimento
		String dtnasc = request.getParameter("dtnascimento");
		int ano = 0, mes = 0, dia = 0;
		if(dtnasc == null || dtnasc.equals("")) {
			request.setAttribute("erroDtNascimento", "*Data de nascimento obrigatória.");
			cadastro_valido = false;
		}else {
			request.setAttribute("erroDtNascimento", "");
			ano = Integer.parseInt(dtnasc.substring(0, 4));
			mes = Integer.parseInt(dtnasc.substring(5, 7));
			dia = Integer.parseInt(dtnasc.substring(8));
			if(LocalDate.of(ano, mes, dia).isAfter(LocalDate.now())) {
				request.setAttribute("erroDtNascimento", "*A data de nascimento não pode ser futura.");
				cadastro_valido = false;
			}
		}
		// lendo o email
		String email = request.getParameter("email");
		if(email == null || email.equals("")) {
			request.setAttribute("erroEmail", "*O preenchimento do email é obrigatório.");
			cadastro_valido = false;
		}else {
			request.setAttribute("erroEmail", "");
		}
		// lendo a senha
		String senha = request.getParameter("senha");
		if(senha == null || senha.equals("")) {
			request.setAttribute("erroSenha", "*É necessário cadastrar uma senha.");
			cadastro_valido = false;
		}else {
			request.setAttribute("erroSenha", "");
		}
		// lendo a confirmação de senha
		String confirmasenha = request.getParameter("confirmasenha");
		if(confirmasenha == null || confirmasenha.equals("")) {
			request.setAttribute("erroConfirmaSenha", "*Confirme a senha.");
			cadastro_valido = false;
		}else {
			if(senha != null && !senha.equals("")) {
				if(!senha.equals(confirmasenha)) {
					request.setAttribute("erroConfirmaSenha", "*As senhas não conferem.");
					cadastro_valido = false;
				}
			}
			request.setAttribute("erroConfirmaSenha", "");
		}
		// efetuando o cadastro
		if(cadastro_valido) {
			System.out.println("cadastro valido");
			user = new Usuario();
			System.out.println("criou o usuario");
			user.setNome(nome);
			user.setSobrenome(sobrenome);
			LocalDate dtnascimento = LocalDate.of(ano, mes, dia);
			user.setDtNascimento(dtnascimento);
			user.setEmail(email);
			user.setSenha(senha);
			if(user.salvar()) {
				// se cadastrado, direciona para o index
				request.getRequestDispatcher("index.html").forward(request, response);
			}else {
				// se não cadastrado, retorna para o cadastro exibindo a mensagem de erro
				request.setAttribute("erroGenerico", user.getMsgErro());
				request.getRequestDispatcher("cadastro.jsp").forward(request, response);
			}
		}else {
			// direcionamento para a tela de erro
			request.setAttribute("erroGenerico", "");
			request.getRequestDispatcher("cadastro.jsp").forward(request, response);
		}
	}
}
