<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Painel Principal</title>
<link rel="stylesheet" type="text/css" href="css/painel.css">
</head>

<body>
	<div class="topnav">
		<a class="active" href="painel.jsp">Painel Principal</a> <a
			href="planejamento.jsp">Planejamento</a> <a href="rotas_salvas.jsp">Rotas
			Salvas</a> <a href="aeronaves.jsp">Aeronaves</a> <label class="logoff"
			onclick="">Logoff</label> <label class="usuario"><%=request.getSession().getAttribute("email")%></label>
	</div>

	<div class="container">
	<h1>Painel do Usuário</h1>
		<div class="card">
			<form action="servletNavegacao" method="post">
				<input type="image" class="icone_painel" src="images/icons/planejamento.png" alt="Submit">
				<h4>Novo Planejamento</h4>
			</form>
		</div>
		<div class="card">
			<form action="servletPlanejamentosSalvos" method="post">
				<input type="image" class="icone_painel" src="images/icons/salvos.png" alt="Submit">
				<h4>Rotas Salvas</h4>
			</form>
		</div>
		<div class="card">
			<form action="servletGerenciaAeronaves" method="post">
				<input type="image" class="icone_painel" src="images/icons/aeronaves.png" alt="Submit">
				<h4>Gerenciar Aeronaves</h4>
			</form>
		</div>
	</div>
</body>

</html>