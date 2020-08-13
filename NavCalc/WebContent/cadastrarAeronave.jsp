<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cadastrar Nova Aeronave</title>
<link rel="stylesheet" type="text/css" href="css/painel.css">
<link rel="stylesheet" type="text/css" href="css/estilos.css">
</head>
<body>
<div class="topnav">
		<a class="active" href="painel.jsp">Painel Principal</a> <a
			href="planejamento.jsp">Planejamento</a> <a href="rotas_salvas.jsp">Rotas
			Salvas</a> <a href="aeronaves.jsp">Aeronaves</a> <label class="logoff"
			onclick="">Logoff</label> <label class="usuario"><%=request.getSession().getAttribute("email")%></label>
</div>
<form action="servletCadastraAeronavePersonalizada" method="post">
<table>
<tr><td>Prefixo:</td><td><input type="text" name="prefixo" placeholder="Digite o prefixo"><label class="fs-15 erro"><%= request.getAttribute("erroPrefixo") %></label></td></tr>
<tr><td>Tipo:</td><td><input type="text" name="tipo" placeholder="Digite o ICAO do tipo"><label class="fs-15 erro"><%= request.getAttribute("erroTipo") %></label></td></tr>
<tr><td>Velocidade de Cruzeiro:</td><td><input type="number" name="velocidade_cruzeiro" placeholder="Velocidade em nós"><label class="fs-15 erro"><%= request.getAttribute("erroVC") %></label></td></tr>
<tr><td>Velocidade de Subida:</td><td><input type="number" name="velocidade_subida" placeholder="Velocidade em nós"><label class="fs-15 erro"><%= request.getAttribute("erroVS") %></label></td></tr>
<tr><td>Velocidade de Descida:</td><td><input type="number" name="velocidade_descida" placeholder="Velocidade em nós"><label class="fs-15 erro"><%= request.getAttribute("erroVD") %></label></td></tr>
<tr><td>Razão de subida:</td><td><input type="number" name="razao_subida" placeholder="Pés/min"><label class="fs-15 erro"><%= request.getAttribute("erroRS") %></label></td></tr>
<tr><td>Razão de descida:</td><td><input type="number" name="razao_descida" placeholder="Pés/min"><label class="fs-15 erro"><%= request.getAttribute("erroRD") %></label></td></tr>
<tr><td>Consumo:</td><td><input type="number" name="consumo" placeholder="Litros/hora"><label class="fs-15 erro"><%= request.getAttribute("erroConsumo") %></label></td></tr>
<tr><td></td><td><input type="submit" value="Salvar"></td></tr>
<tr><td colspan=2><%= request.getAttribute("status") %></td><td>
</table>

</form>
</body>
</html>