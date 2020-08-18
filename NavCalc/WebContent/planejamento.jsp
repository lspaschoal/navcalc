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
<% 
try{
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

<form>
<label for="idorigem">Aeródromo de origem: </label><input type="text" placeholder="código ICAO" name="origem" id="idorigem" value="<% if(request.getAttribute("origem_digitada") != null) out.print(request.getAttribute("origem_digitada")); %>">
<label style="color: #990000; padding-left: 10px;"><% if(request.getSession().getAttribute("erroOrigem") != null) out.print(request.getSession().getAttribute("erroOrigem")); %></label>
<div id="rota">
<% 
int nfixos = Integer.parseInt(request.getSession().getAttribute("nfixos").toString());
for(int i = 0; i < nfixos; i++){
	
		out.print("<label>Fixo ");
		if(i < 9)out.print("0");
		out.print((i+1) + ":</label><input type=\"text\" id=\"idfixo" + i + "\" name=\"fixo" + i + "\" placeholder=\"nome ou coordenada\" value=\"");
		if(request.getAttribute("coordenada_digitada" + i) != null)out.print(request.getAttribute("coordenada_digitada" + i));
		out.print("\">");
		out.print("<label style=\"color: #990000; padding-left: 10px;\" name=\"erroFixo" + i + "\">");
		if(request.getSession().getAttribute("erroFixo" + i) != null){out.print(request.getSession().getAttribute("erroFixo" + i));}
		out.print("</label><br>");
}
%>
</div>
<input type="submit" value="Adicionar" formaction="servletAdicionaFixo" formmethod="post"><br>

<label for="iddestino">Aeródromo de destino: </label><input type="text" placeholder="código ICAO" name="destino" id="iddestino" value="<% if(request.getAttribute("destino_digitado") != null) out.print(request.getAttribute("destino_digitado")); %>">
<label style="color: #990000; padding-left: 10px;"><% if(request.getSession().getAttribute("erroDestino") != null) out.print(request.getSession().getAttribute("erroDestino")); %></label><br>
<label for="idaltitude">Altitude: </label><input type="range" min="1000" max="14500" step="500" name="altitude" id="idaltitude" onchange="mostraAltitudeSelecionada(this.value);" value="<% if(request.getAttribute("altitude_escolhida") != null) {out.print(request.getAttribute("altitude_escolhida"));}else{out.print(3500);} %>">
<label id="label_altitude"><% if(request.getAttribute("altitude_escolhida") != null) {out.print(request.getAttribute("altitude_escolhida"));}else{out.print(3500);} %>ft</label>
<br>
<label for="idaltitude">Aeronave: </label>
<select name="aeronave" id="idaeronave">
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
<input type="submit" value="Gerar Navegação" formaction="servletCalculaNavegacao" formmethod="post">
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
				String.format("%.1d",distancia) + "nm"
				+ "</td><td>" + 
				String.format("%02d",(tempo/60)) + ":" + String.format("%02d",(tempo%60))
				+ "</td><td>" +
				consumo + "l"
				+ "</td></tr>");
	}
	out.print("<tr><td></td><td></td><td style=\"align: right; padding: 10px;\">Total:</td><td>" + 
			String.format("%.1d",distanciatotal) 
			+ "</td><td>" + 
			String.format("%02d",(tempototal/60)) + ":" + String.format("%02d",(tempototal%60)) 
			+ "</td><td>" + 
			consumototal + "l"
			+ "</td></tr></table>");
	out.print("<div>");
	out.print("<form>");
	out.print("<input type=\"submit\" value=\"Gerar Kneeboard\" formaction=\"servletKneeboard\" formmethod=\"post\">");
	out.print("<input type=\"submit\" value=\"Salvar Navegação\" formaction=\"servletSalvarNavegacao\" formmethod=\"post\">");
	out.print("</form>");
	out.print("</div>");
}%>
</div>
 <script src="./js/rota.js"></script>
 <script src="./js/mostraAltitude.js"></script>
</body>

</html>