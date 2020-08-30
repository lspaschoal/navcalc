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
<% try{
	String id = request.getSession().getAttribute("id").toString();
}catch(NullPointerException npe){
	request.setAttribute("msgErro", "É necessário estar logado para acessar o sistema.");
	request.getRequestDispatcher("login.jsp").forward(request, response);
} %>
	<div class="topnav">
		<a class="active" href="painel.jsp">Painel Principal</a>
		<form name="navegacao" action="servletNavegacao" method="post"><a href="javascript:navegacao.submit()">Planejamento</a></form> 
		<a href="rotas_salvas.jsp">Rotas Salvas</a>
		<form name="gerencia_aeronaves" action="servletGerenciaAeronaves" method="post"><a href="javascript:gerencia_aeronaves.submit()">Aeronaves</a> </form> 
		<form name="logoff" action="servletLogoff" method="post"><label class="logoff" onclick="javascript:logoff.submit()">Logoff</label></form>
		<label class="usuario"><%=request.getSession().getAttribute("email")%></label>
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
			<a href="rotas_salvas.jsp">
				<input type="image" class="icone_painel" src="images/icons/salvos.png" alt="Submit">
			</a>
			<h4>Rotas Salvas</h4>
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