<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="controller.AeronavePersonalizada"%>
<%@ page import="controller.Aeronave"%>
<%@ page import="controller.Fixo"%>
<%@ page import="controller.Planejamento"%>
<%@ page import="controller.Trecho"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Painel Principal</title>
<link rel="stylesheet" type="text/css" href="css/painel.css">
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
  		<form name="navegacao" action="servletNavegacao" method="post"><a class="active" href="javascript:navegacao.submit()">Planejamento</a></form> 
		<form name="planejamentos_salvos" action="servletPlanejamentosSalvos" method="post"><a href="javascript:navegacao.submit()">Rotas Salvas</a> </form> 
		<form name="gerencia_aeronaves" action="servletGerenciaAeronaves" method="post"><a href="javascript:navegacao.submit()">Aeronaves</a> </form> 
		<form name="logoff" action="servletLogoff" method="post"><label class="logoff" onclick="javascript:logoff.submit()">Logoff</label></form>
		<label class="usuario"><%= request.getSession().getAttribute("email").toString() %></label>
</div>

<form action="servletCalculaNavegacao" method="post">
<label for="idorigem">Aeródromo de origem: </label><input type="text" placeholder="código ICAO" name="origem" id="idorigem" value="<%= request.getAttribute("origem_digitada") %>" required>
<label style="color: #990000; padding-left: 10px;"><%= request.getAttribute("erroOrigem") %></label>
<div id="rota">
Para cada ponto, digite o nome do fixo ou uma coordenada no formato ######(N/S)#######(W/E):<br>
<label>Fixo 01: </label><input type="text" name="fixo1" placeholder="nome ou coordenada" value="<%= request.getAttribute("coordenada_digitada1") %>"><label style="color: #990000; padding-left: 10px;"><%= request.getAttribute("erroFixo1") %></label><br>
<label>Fixo 02: </label><input type="text" name="fixo2" placeholder="nome ou coordenada" value="<%= request.getAttribute("coordenada_digitada2") %>"><label style="color: #990000; padding-left: 10px;"><%= request.getAttribute("erroFixo2") %></label><br>
<label>Fixo 03: </label><input type="text" name="fixo3" placeholder="nome ou coordenada" value="<%= request.getAttribute("coordenada_digitada3") %>"><label style="color: #990000; padding-left: 10px;"><%= request.getAttribute("erroFixo3") %></label><br>
<label>Fixo 04: </label><input type="text" name="fixo4" placeholder="nome ou coordenada" value="<%= request.getAttribute("coordenada_digitada4") %>"><label style="color: #990000; padding-left: 10px;"><%= request.getAttribute("erroFixo4") %></label><br>
<label>Fixo 05: </label><input type="text" name="fixo5" placeholder="nome ou coordenada" value="<%= request.getAttribute("coordenada_digitada5") %>"><label style="color: #990000; padding-left: 10px;"><%= request.getAttribute("erroFixo5") %></label><br>
<label>Fixo 06: </label><input type="text" name="fixo6" placeholder="nome ou coordenada" value="<%= request.getAttribute("coordenada_digitada6") %>"><label style="color: #990000; padding-left: 10px;"><%= request.getAttribute("erroFixo6") %></label><br>
<label>Fixo 07: </label><input type="text" name="fixo7" placeholder="nome ou coordenada" value="<%= request.getAttribute("coordenada_digitada7") %>"><label style="color: #990000; padding-left: 10px;"><%= request.getAttribute("erroFixo7") %></label><br>
<label>Fixo 08: </label><input type="text" name="fixo8" placeholder="nome ou coordenada" value="<%= request.getAttribute("coordenada_digitada8") %>"><label style="color: #990000; padding-left: 10px;"><%= request.getAttribute("erroFixo8") %></label><br>
<label>Fixo 09: </label><input type="text" name="fixo9" placeholder="nome ou coordenada" value="<%= request.getAttribute("coordenada_digitada9") %>"><label style="color: #990000; padding-left: 10px;"><%= request.getAttribute("erroFixo9") %></label><br>
<label>Fixo 10: </label><input type="text" name="fixo10" placeholder="nome ou coordenada" value="<%= request.getAttribute("coordenada_digitada10") %>"><label style="color: #990000; padding-left: 10px;"><%= request.getAttribute("erroFixo10") %></label><br>
</div>

<label for="iddestino">Aeródromo de destino: </label><input type="text" placeholder="código ICAO" name="destino" id="iddestino" value="<%= request.getAttribute("destino_digitado") %>" required>
<label style="color: #990000; padding-left: 10px;"><%= request.getAttribute("erroDestino") %></label><br>
<label for="idaltitude">Altitude: </label><input type="range" min="1000" max="14500" step="500" value="3500" name="altitude" id="idaltitude" onchange="mostraAltitudeSelecionada(this.value);" required>
<label id="label_altitude">3500ft</label>
<br>
<label for="idaltitude">Aeronave: </label>
<select name="aeronave" id="idaeronave" required>
<optgroup label="Minhas aeronaves">
<%
		ArrayList<AeronavePersonalizada> aeronaves_usuario = (ArrayList<AeronavePersonalizada>) request.getSession().getAttribute("listaAeronavesUsuario");
	%>
<% if (!aeronaves_usuario.isEmpty()) {
	for (AeronavePersonalizada ap : aeronaves_usuario){
		out.println("<option value=\"" + ap.getId() + "\">" + ap.getPrefixo() + "</option>");
	}
} %>
</optgroup>
<optgroup label="Aeronaves genéricas">
<%
		ArrayList<Aeronave> aeronaves_padrao = (ArrayList<Aeronave>) request.getSession().getAttribute("listaAeronavesPadrao");
	%>
<% if (!aeronaves_padrao.isEmpty()) {
	for (Aeronave a : aeronaves_padrao){
		out.println("<option value=\"" + a.getTipo() + "\">" + a.getTipo() + "</option>");
	}
} %>
</optgroup>
</select><br>
<input type="submit" value="Gerar Navegação">
</form>
<div id="resultado">
<% if(!request.getSession().getAttribute("navegacao").equals("")){
	Planejamento planejamento = new Planejamento();
	planejamento = (Planejamento) request.getSession().getAttribute("navegacao");
	ArrayList<Trecho> trechos = planejamento.getTrechos();
	double distanciatotal = 0;
	int tempototal = 0;
	int consumototal = 0;
	out.println("Consumo da aeronave em litros: " + planejamento.getAeronave().getConsumo());
	out.print("<table><tr><th>De</th><th>Para</th><th>Rumo</th><th>Distancia</th><th>Tempo</th><th>Consumo</th></tr>");
	for(int i = 0; i < trechos.size(); i++){
		double distancia = trechos.get(i).getDistancia();
		int tempo = trechos.get(i).getTempoMinutos();
		int consumo = (tempo * planejamento.getAeronave().getConsumo()) / 60;
		distanciatotal += distancia;
		tempototal += tempo;
		consumototal += consumo;
		out.print("<tr><td>" + 
				(trechos.get(i).getNomeInicio() != null ? trechos.get(i).getNomeInicio() : trechos.get(i).getInicio().latitudeToString() + "<br>" + trechos.get(i).getInicio().longitudeToString())
				+"</td><td>" +
				(trechos.get(i).getNomeFim() != null ? trechos.get(i).getNomeFim() : trechos.get(i).getFim().latitudeToString() + "<br>" + trechos.get(i).getFim().longitudeToString())
				+"</td><td>" +
				String.format("%03d",trechos.get(i).getRumo()) + "°"
				+"</td><td>" + 
				distancia + "nm"
				+ "</td><td>" + 
				String.format("%02d",(tempo/60)) + ":" + String.format("%02d",(tempo%60))
				+ "</td><td>" +
				consumo + "l"
				+ "</td></tr>");
	}
	out.print("<tr><td></td><td></td><td style=\"align: right; padding: 10px;\">Total:</td><td>" + 
			distanciatotal 
			+ "</td><td>" + 
			String.format("%02d",(tempototal/60)) + ":" + String.format("%02d",(tempototal%60)) 
			+ "</td><td>" + 
			consumototal + "l"
			+ "</td></tr></table>");
}%>
</div>
 <!-- <script src="./js/rota.js"></script> -->
 <script src="./js/mostraAltitude.js"></script>
</body>

</html>