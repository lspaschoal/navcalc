<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="controller.AeronavePersonalizada"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Gerenciamento de Aeronaves</title>
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
		<a href="rotas_salvas.jsp">Rotas Salvas</a> 
		<form name="gerencia_aeronaves" action="servletGerenciaAeronaves" method="post"><a class="active" href="javascript:navegacao.submit()">Aeronaves</a> </form> 
		<form name="logoff" action="servletLogoff" method="post"><label class="logoff" onclick="javascript:logoff.submit()">Logoff</label></form>  
		<label class="usuario"><%=request.getSession().getAttribute("email")%></label>
	</div>
	<%
		ArrayList<AeronavePersonalizada> lista_de_aeronaves = (ArrayList<AeronavePersonalizada>) request.getAttribute("listaAeronavesUsuario");
	%>
	<%
		if (lista_de_aeronaves.isEmpty()) {
		out.println("Voce ainda não tem aeronaves cadastradas");
	} else {
		out.println(
		"<table><tr><th>Prefixo"
		+"</th><th>Tipo"
		+"</th><th>Velocidade de Cruzeiro"
		+"</th><th>Velocidade de Subida"
		+"</th><th>Velocidade de Descida"
		+"</th><th>Razão de Subida"
		+"</th><th>Razão de Descida"
		+"</th><th>Editar"
		+"</th><th>Excluir"
		+ "</th></tr>");
		for (AeronavePersonalizada ap : lista_de_aeronaves) {
			out.println("<tr><td>" + ap.getPrefixo() //prefixo
						+ "</td><td>" + ap.getTipo() //tipo
						+ "</td><td>" + ap.getVelocidadeCruzeiro() //velocidade de cruzeiro
						+ "kt</td><td>" + ap.getVelocidadeSubida() //velocidade de subida
						+ "kt</td><td>" + ap.getVelocidadeDescida() //velocidade de descida
						+ "kt</td><td>" + ap.getRazaoSubida() //razão de subida
						+ "ft/min</td><td>" + ap.getRazaoDescida() //razão de descida
						+ "ft/min</td><td><form action=\"servletEditaAeronave\" method=\"post\"><input name=\"idAeronave\" type=\"hidden\" value=\"" + ap.getId() + "\"><input type=\"image\" class=\"ico_editar_excluir\" src=\"images/icons/editar.png\" alt=\"submit\"></form>" //botão editar
						+ "</td><td><form action=\"servletExcluiAeronave\" method=\"post\" onsubmit=\"return confirm('Tem certeza de que deseja excluir essa aeronave?')\"><input name=\"idAeronave\" type=\"hidden\" value=\"" + ap.getId() + "\"><input type=\"image\" class=\"ico_editar_excluir\" src=\"images/icons/excluir.png\" alt=\"submit\"></form>" //botão excluir
						+ "</td></tr>");
		}
		out.println("</table>");
	}
	%>
	<form action="servletCadastroAeronaves" method="post">
		<input type="submit" value="Cadastrar Aeronave">
	</form>
</body>
</html>