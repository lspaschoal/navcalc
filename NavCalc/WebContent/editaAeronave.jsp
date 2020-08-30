<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="controller.AeronavePersonalizada"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Editar Aeronave</title>
<link rel="stylesheet" type="text/css" href="css/painel.css">
<link rel="stylesheet" type="text/css" href="css/estilos.css">
<style>
.card{
margin: 0 auto;
box-shadow:0px 13px 21px -5px rgba(0.5, 0.5, 0.5, 1);
background: white;
}
td, input{
font-size: 20px;
}
td{
background: #ffeecc;
}
</style>
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
		<a href="rotas_salvas.jsp">Rotas Salvas</a>
		<form name="gerencia_aeronaves" action="servletGerenciaAeronaves" method="post"><a href="javascript:gerencia_aeronaves.submit()" class="active">Aeronaves</a> </form> 
		<form name="logoff" action="servletLogoff" method="post"><label class="logoff" onclick="javascript:logoff.submit()">Logoff</label></form>
		<label class="usuario"><%=request.getSession().getAttribute("email")%></label>
	</div>

<form action="servletUpdateAeronavePersonalizada" method="post" onsubmit="return confirm('Tem certeza de que deseja salvar essas alterações?')">
<% AeronavePersonalizada aeronave = (AeronavePersonalizada) request.getAttribute("aeronave"); %>
<input type="hidden" name="idAeronave" value="<%= aeronave.getId() %>">
<h2 style="text-align: center; margin: 50px;">Editar Aeronave</h2>
<table style="margin: 50px auto; box-shadow:0px 8px 15px -5px rgba(0.5, 0.5, 0.5, 1);">
<tr><td>Prefixo:</td><td><input type="text" name="prefixo" value="<%= aeronave.getPrefixo() %>" placeholder="Digite o prefixo"></td></tr>
<tr><td></td><td><label class="fs-15 erro"><%= request.getAttribute("erroPrefixo") %></label></td></tr>
<tr><td>Tipo:</td><td><input type="text" name="tipo" value="<%= aeronave.getTipo() %>" placeholder="Digite o ICAO do tipo"></td></tr>
<tr><td></td><td><label class="fs-15 erro"><%= request.getAttribute("erroTipo") %></label></td></tr>
<tr><td>Velocidade de Cruzeiro:</td><td><input type="number" name="velocidade_cruzeiro" value="<%= aeronave.getVelocidadeCruzeiro() %>" placeholder="Velocidade em nós"></td></tr>
<tr><td></td><td><label class="fs-15 erro"><%= request.getAttribute("erroVC") %></label></td></tr>
<tr><td>Velocidade de Subida:</td><td><input type="number" name="velocidade_subida" value="<%= aeronave.getVelocidadeSubida() %>" placeholder="Velocidade em nós"></td></tr>
<tr><td></td><td><label class="fs-15 erro"><%= request.getAttribute("erroVS") %></label></td></tr>
<tr><td>Velocidade de Descida:</td><td><input type="number" name="velocidade_descida" value="<%= aeronave.getVelocidadeDescida() %>" placeholder="Velocidade em nós"></td></tr>
<tr><td></td><td><label class="fs-15 erro"><%= request.getAttribute("erroVD") %></label></td></tr>
<tr><td>Razão de subida:</td><td><input type="number" name="razao_subida" value="<%= aeronave.getRazaoSubida() %>" placeholder="Pés/min"></td></tr>
<tr><td></td><td><label class="fs-15 erro"><%= request.getAttribute("erroRS") %></label></td></tr>
<tr><td>Razão de descida:</td><td><input type="number" name="razao_descida" value="<%= aeronave.getRazaoDescida() %>" placeholder="Pés/min"></td></tr>
<tr><td></td><td><label class="fs-15 erro"><%= request.getAttribute("erroRD") %></label></td></tr>
<tr><td>Consumo:</td><td><input type="number" name="consumo" value="<%= aeronave.getConsumo() %>" placeholder="Litros/hora"></td></tr>
<tr><td></td><td><label class="fs-15 erro"><%= request.getAttribute("erroConsumo") %></label></td></tr>
<tr><td></td><td><input type="submit" value="Salvar Alterações"></td></tr>
<tr><td colspan=2><%= request.getAttribute("status") %></td><td>
</table>
</form>
</body>
</html>