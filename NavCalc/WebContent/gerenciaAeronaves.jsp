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
	<div class="topnav">
		<a href="painel.jsp">Painel Principal</a> <a
			href="planejamento.jsp">Planejamento</a> <a href="rotas_salvas.jsp">Rotas
			Salvas</a> <a class="active" href="aeronaves.jsp">Aeronaves</a> <label class="logoff"
			onclick="">Logoff</label> <label class="usuario"><%=request.getSession().getAttribute("email")%></label>
	</div>
	<%
		ArrayList<AeronavePersonalizada> lista_de_aeronaves = (ArrayList<AeronavePersonalizada>) request
			.getAttribute("listaAeronavesUsuario");
	%>
	<%
		if (lista_de_aeronaves.isEmpty()) {
		out.println("Voce ainda não tem aeronaves cadastradas");
	} else {
		out.println(
		"<table><tr><th>Prefixo</th><th>Tipo</th><th>Velocidade de Cruzeiro</th><th>Velocidade de Subida</th><th>Velocidade de Descida</th><th>Razão de Subida</th><th>Razão de Descida</th></tr>");
		for (AeronavePersonalizada ap : lista_de_aeronaves) {
			out.println("<tr><td>" + ap.getPrefixo() + "</td><td>" + ap.getTipo() + "</td><td>" + ap.getVelocidadeCruzeiro()
			+ "kt</td><td>" + ap.getVelocidadeSubida() + "kt</td><td>" + ap.getVelocidadeDescida() + "kt</td><td>"
			+ ap.getRazaoSubida() + "ft/min</td><td>" + ap.getRazaoDescida() + "ft/min</td></tr>");
		}
		out.println("</table>");
	}
	%>
	<form action="servletCadastroAeronaves" method="post">
		<input type="submit" value="Cadastrar Aeronave">
	</form>
</body>
</html>