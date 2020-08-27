<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="controller.Aeronave" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin > Aeronaves > Editar Aeronave</title>
<style>
.card{
margin: 0 auto;
box-shadow:0px 13px 21px -5px rgba(0.5, 0.5, 0.5, 1);
background: white;
}
.titulo{
font-family: sans-serif;
font-size: 50px;
font-weight: bold;
color: white;
text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}
.btn_voltar{
font-family: sans-serif;
font-size:25px;
font-weight: bold;
color: rgb(46,204,113);
text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}
.erro{
color: #941e00;
}
td, input{
font-size: 18px;
}
.display-error{
width: 400px;
border: 1px solid #D8D8D8;
padding: 5px;
border-radius: 5px;
font-family: Arial;
font-size: 11px;
text-transform: uppercase;
background-color: rgb(255, 249, 242);
color: rgb(211, 0, 0);
text-align: center;
}

.display-success{
width: 400px;
border: 1px solid #D8D8D8;
padding: 10px;
border-radius: 5px;
font-family: Arial;
font-size: 11px;
text-transform: uppercase;
background-color: rgb(236, 255, 216);
color: green;
text-align: center;
margin-top: 30px;
}
</style>
</head>
<body style="background: #303030">
<div style="text-align: center; margin: 100px;"><span class="titulo">Editar Aeronave</span></div>
<form action="servletUpdateAeronavePadrao" method="post" onsubmit="return confirm('Tem certeza de que deseja salvar essas alterações?')">
<% Aeronave aeronave = (Aeronave) request.getAttribute("aeronave"); %>
<input type="hidden" name="idAeronave" value="<%= aeronave.getId() %>">
<table class="card">
<tr><td>Tipo:</td><td><input type="text" name="tipo" value="<%= aeronave.getTipo() %>" placeholder="Digite o ICAO do tipo"><label class="erro"><%= request.getAttribute("erroTipo") %></label></td></tr>
<tr><td>Velocidade de Cruzeiro:</td><td><input type="number" name="velocidade_cruzeiro" value="<%= aeronave.getVelocidadeCruzeiro() %>" placeholder="Velocidade em nós"><label class="erro"><%= request.getAttribute("erroVC") %></label></td></tr>
<tr><td>Velocidade de Subida:</td><td><input type="number" name="velocidade_subida" value="<%= aeronave.getVelocidadeSubida() %>" placeholder="Velocidade em nós"><label class="erro"><%= request.getAttribute("erroVS") %></label></td></tr>
<tr><td>Velocidade de Descida:</td><td><input type="number" name="velocidade_descida" value="<%= aeronave.getVelocidadeDescida() %>" placeholder="Velocidade em nós"><label class="erro"><%= request.getAttribute("erroVD") %></label></td></tr>
<tr><td>Razão de subida:</td><td><input type="number" name="razao_subida" value="<%= aeronave.getRazaoSubida() %>" placeholder="Pés/min"><label class="erro"><%= request.getAttribute("erroRS") %></label></td></tr>
<tr><td>Razão de descida:</td><td><input type="number" name="razao_descida" value="<%= aeronave.getRazaoDescida() %>" placeholder="Pés/min"><label class="erro"><%= request.getAttribute("erroRD") %></label></td></tr>
<tr><td>Consumo:</td><td><input type="number" name="consumo" value="<%= aeronave.getConsumo() %>" placeholder="Litros/hora"><label class="erro"><%= request.getAttribute("erroConsumo") %></label></td></tr>
<tr><td></td><td><input type="submit" value="Salvar Alterações"></td></tr>
<tr><td colspan=2><%= request.getAttribute("status") %></td><td>
<tr><td><form name="aeronaves" action="servletAeronavesPadrao" method="post"><a href="javascript:aeronaves.submit()" style="text-decoration: none; margin: 0 auto;"><img src="images/icons/voltar.png" style="width: 25px;"><span class="btn_voltar"> Voltar</span></a></form></td><td></td></tr>
</table>
</form>
</body>
</html>