<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="controller.Fixo"%>
<%@ page import="controller.PlanejamentoSalvo"%>
<%@ page import="controller.Planejamento"%>
<%@ page import="model.ModelAeronave"%>
<%@ page import="model.ModelAeronavePersonalizada"%>
<%@ page import="model.ModelAerodromo"%>
<%@ page import="model.ModelFixo"%>
<%@ page import="model.ModelPlanejamentoSalvo"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Minhas Rotas</title>
<link rel="stylesheet" type="text/css" href="css/painel.css">
<link rel="stylesheet" type="text/css" href="css/tabela_rotas.css">
</head>

<body>
<% try{
	String id = request.getSession().getAttribute("id").toString();
}catch(NullPointerException npe){
	request.setAttribute("msgErro", "É necessário estar logado para acessar o sistema.");
	request.getRequestDispatcher("login.jsp").forward(request, response);
} %>
	<div class="topnav">
		<a href="painel.jsp">Painel Principal</a>
		<form name="navegacao" action="servletNavegacao" method="post"><a href="javascript:navegacao.submit()">Planejamento</a></form> 
		<a href="rotas_salvas.jsp" class="active">Rotas Salvas</a>
		<form name="gerencia_aeronaves" action="servletGerenciaAeronaves" method="post"><a href="javascript:gerencia_aeronaves.submit()">Aeronaves</a> </form> 
		<form name="logoff" action="servletLogoff" method="post"><label class="logoff" onclick="javascript:logoff.submit()">Logoff</label></form>
		<label class="usuario"><%=request.getSession().getAttribute("email")%></label>
	</div>

	
	<%
	long id_usuario = (Long) request.getSession().getAttribute("id");
	ModelPlanejamentoSalvo mps = new ModelPlanejamentoSalvo();	
	ArrayList<PlanejamentoSalvo> planejamentos = mps.getPlanosUsuario(id_usuario);
	%>
	<%
		if (planejamentos.isEmpty()) {
		out.println("<div class=\"container\" style=\"margin-left: 400px; margin-top: 150px;\">");
		out.println("<h2>Você ainda não tem planejamentos salvos.</h2>");
		out.println("<img src=\"images/cr3.jpg\" style=\"margin-left: 370px;\">");
		out.println("<form action=\"servletNavegacao\" method=\"post\"><input type=\"submit\" value=\"Iniciar Novo Planejamento\" style=\"margin-top: 50px; height: 40px;\"></form>");
	} else {
		out.println("<div class=\"card\" style=\"margin-left: 350px; margin-top: 150px;\"><h1 style=\"text-align: center; margin-bottom: 100px; margin-top: 20px;\">Minhas Rotas</h1>");
		out.println(
		"<table><tr><th>Origem"
		+"</th><th>Destino"
		+"</th><th>Rota"
		+"</th><th>Altitude"
		+"</th><th>Aeronave"
		+"</th><th>Editar"
		+"</th><th>Excluir"
		+"</th><th>Kneeboard"
		+ "</th></tr>");
		for (PlanejamentoSalvo ps : planejamentos) {
			Planejamento plan = ps.gerarPlanejamento();
			out.print("<tr><td>" +  plan.getOrigem().getIcao() //origem
						+ "</td><td>" + plan.getDestino().getIcao() //destino
						+ "</td><td>");
			//rota
			if(plan.getRota().isEmpty()){
				out.print("DCT");
			}else{
				for(Fixo f : plan.getRota()){
					if(f.getNome() != null){
						out.print(f.getNome());
					}else{
						out.print(f.latitudeToString()+"/"+f.longitudeToString());
					}
					if(plan.getRota().indexOf(f) < plan.getRota().size()-1){ 
						out.print(" , ");
					}
				}
			}
			out.println("</td><td>" + ps.getAltitude() //altitude
						+"</td><td>" + plan.getAeronave().getTipo()); //aeronave
						if(ps.isAeronave_personalizada()){ //prefixo, se houver
							String prefixo = new ModelAeronavePersonalizada().getAeronave(ps.getId_aeronave()).getPrefixo();
							out.print(" (" + prefixo + ")");
						}
			out.println("</td><td><form action=\"servletEditaPlanoSalvo\" method=\"post\"><input name=\"idPlano\" type=\"hidden\" value=\"" + ps.getId() + "\"><input type=\"image\" class=\"ico_editar_excluir\" src=\"images/icons/editar.png\" alt=\"submit\"></form>" //botão editar
						+ "</td><td><form action=\"servletExcluiPlanoSalvo\" method=\"post\" onsubmit=\"return confirm('Tem certeza de que deseja excluir esse planejamento?')\"><input name=\"idPlano\" type=\"hidden\" value=\"" + ps.getId() + "\"><input type=\"image\" class=\"ico_editar_excluir\" src=\"images/icons/excluir.png\" alt=\"submit\"></form>" //botão excluir
						+ "</td><td><form action=\"servletGeraKneeboard\" method=\"post\"><input name=\"idPlano\" type=\"hidden\" value=\"" + ps.getId() + "\"><input type=\"image\" class=\"ico_editar_excluir\" src=\"images/icons/kneeboard.png\" alt=\"submit\"></form>" //botão kneeboard
						+ "</td></tr>");
		}
		out.println("</table>");
	}
	%>
	</div>

</body>

</html>