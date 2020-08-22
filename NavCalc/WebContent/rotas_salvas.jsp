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
<title>Painel Principal</title>
<link rel="stylesheet" type="text/css" href="css/painel.css">
<link rel="stylesheet" type="text/css" href="css/tabela_aeronaves.css">
</head>

<body>
<% try{
	String usuario = request.getSession().getAttribute("email").toString();
}catch(NullPointerException npe){
	request.setAttribute("msgErro", "É necessário estar logado para acessar o sistema.");
	request.getRequestDispatcher("login.jsp").forward(request, response);
} %>
	<div class="topnav">
		<a href="painel.jsp">Painel Principal</a>
		<form name="navegacao" action="servletNavegacao" method="post"><a href="javascript:navegacao.submit()">Planejamento</a></form> 
		<form name="planejamentos_salvos" action="servletPlanejamentosSalvos" method="post"><a class="active" href="javascript:navegacao.submit()">Rotas Salvas</a> </form> 
		<form name="gerencia_aeronaves" action="servletGerenciaAeronaves" method="post"><a href="javascript:navegacao.submit()">Aeronaves</a> </form> 
		<form name="logoff" action="servletLogoff" method="post"><label class="logoff" onclick="javascript:logoff.submit()">Logoff</label></form>
		<label class="usuario"><%=request.getSession().getAttribute("email")%></label>
</div>

	<div class="container">
	<%
	long id_usuario = (Long) request.getSession().getAttribute("id");
	ModelPlanejamentoSalvo mps = new ModelPlanejamentoSalvo();	
	ArrayList<PlanejamentoSalvo> planejamentos = mps.getPlanosUsuario(id_usuario);
	%>
	<%
		if (planejamentos.isEmpty()) {
		out.println("Você ainda não tem planejamentos salvos.");
	} else {
		out.println(
		"<table><tr><th>Origem"
		+"</th><th>Destino"
		+"</th><th>Rota"
		+"</th><th>Altitude"
		+"</th><th>Aeronave"
		+"</th><th>Editar"
		+"</th><th>Excluir"
		+ "</th></tr>");
		for (PlanejamentoSalvo ps : planejamentos) {
			Planejamento plan = ps.gerarPlanejamento();
			out.print("<tr><td>" +  plan.getOrigem().getIcao() //origem
						+ "</td><td>" + plan.getDestino().getIcao() //destino
						+ "</td><td>");
			
			for(Fixo f : plan.getRota()){ //rota
				if(f.getNome() != null){
					out.print(f.getNome());
				}else{
					out.print(f.latitudeToString()+"/"+f.longitudeToString());
				}
				if(plan.getRota().indexOf(f) < plan.getRota().size()-1){ 
					out.print(" , ");
				}
			}
			out.println("</td><td>" + ps.getAltitude() //altitude
						+"</td><td>" + plan.getAeronave().getTipo()); //aeronave
						if(ps.isAeronave_personalizada()){ //prefixo, se houver
							String prefixo = new ModelAeronavePersonalizada().getAeronave(ps.getId_aeronave()).getPrefixo();
							out.print(" (" + prefixo + ")");
						}
			out.println("</td><td><form action=\"servletEditaPlanoSalvo\" method=\"post\"><input name=\"idAeronave\" type=\"hidden\" value=\"" + ps.getId() + "\"><input type=\"image\" class=\"ico_editar_excluir\" src=\"images/icons/editar.png\" alt=\"submit\"></form>" //botão editar
						+ "</td><td><form action=\"servletExcluiPlanoSalvo\" method=\"post\" onsubmit=\"return confirm('Tem certeza de que deseja excluir esse planejamento?')\"><input name=\"idAeronave\" type=\"hidden\" value=\"" + ps.getId() + "\"><input type=\"image\" class=\"ico_editar_excluir\" src=\"images/icons/excluir.png\" alt=\"submit\"></form>" //botão excluir
						+ "</td></tr>");
		}
		out.println("</table>");
	}
	%>
	</div>

</body>

</html>