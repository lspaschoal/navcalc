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
<title>Planejamento de Vôo</title>
<link rel="stylesheet" type="text/css" href="css/painel.css">
<link rel="stylesheet" type="text/css" href="css/tabela-navegacao.css">
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

<div class="card">
<form>
<table>
<!-- Linha para input de origem -->
<tr>
<td style="text-align: right;"><label for="idorigem">Aeródromo de origem: </label></td><td style="text-align: left;"><input type="text" placeholder="código ICAO" name="origem" id="idorigem" value="<% if(request.getAttribute("origem_digitada") != null) out.print(request.getAttribute("origem_digitada")); %>"></td>
</tr>
<tr>
<td></td><td style="text-align: left;"><label style="color: #990000; font-size: 13px;"><% if(request.getSession().getAttribute("erroOrigem") != null) out.print(request.getSession().getAttribute("erroOrigem")); %></label></td>
</tr>
<!-- Linhas para inputs de fixos  -->
<%
int nfixos = Integer.parseInt(request.getSession().getAttribute("nfixos").toString());
for(int i = 0; i < nfixos; i++){
	out.println(
			  "<tr>"
			+ "<td style=\"text-align: right;\">"
			+ "<label>Fixo " + ((i < 9) ? "0" : "") + (i+1) + "</label></td>"
			+ "<td style=\"text-align: left;\"><input type=\"text\" id=\"idfixo" + i + "\" name=\"fixo" + i + "\" placeholder=\"nome ou coordenada\" value=\""
			+ ((request.getAttribute("coordenada_digitada" + i) != null) ? request.getAttribute("coordenada_digitada" + i) : "")
			+ "\"></td>"
			+ "</tr>"
			);
	out.println(
			  "<tr>"
			+ "<td></td>"
			+ "<td style=\"text-align: left;\"><label style=\"color: #990000; font-size: 13px;\" name=\"erroFixo" + i + "\">"
			+ ((request.getSession().getAttribute("erroFixo" + i) != null) ? request.getSession().getAttribute("erroFixo" + i) : "")
			+ "</label></td>"
			+ "</tr>"
			);
}
%>
<!-- Linha do botão de adicionar inputs para fixos  -->
<tr>
<td></td><td style="text-align: left;"><input type="submit" value="Adicionar Fixo" formaction="servletAdicionaFixo" formmethod="post"></td>
</tr>
<!-- Linha para input de destino -->
<tr>
<td style="text-align: right;"><label for="iddestino">Aeródromo de destino: </label></td><td style="text-align: left;"><input type="text" placeholder="código ICAO" name="destino" id="iddestino" value="<% if(request.getAttribute("destino_digitado") != null) out.print(request.getAttribute("destino_digitado")); %>"></td>
</tr>
<tr>
<td></td><td style="text-align: left;"><label style="color: #990000; font-size: 13px;"><% if(request.getSession().getAttribute("erroDestino") != null) out.print(request.getSession().getAttribute("erroDestino")); %></label></td>
</tr>
<!-- Linha para input de altitude -->
<tr>
<td style="text-align: right;"><label for="idaltitude">Altitude: </label></td>
<td><input type="range" min="1000" max="14500" step="500" name="altitude" id="idaltitude" onchange="mostraAltitudeSelecionada(this.value);" value="<% if(request.getAttribute("altitude_escolhida") != null) {out.print(request.getAttribute("altitude_escolhida"));}else{out.print(3500);} %>">
<label id="label_altitude"><% if(request.getAttribute("altitude_escolhida") != null) {out.print(request.getAttribute("altitude_escolhida"));}else{out.print(3500);} %>ft</label>
</td>
</tr>
<!-- Linha para input de aeronave -->
<tr>
<td style="text-align: right;"><label for="idaeronave">Aeronave: </label></td>
<td style="text-align: left;">
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
</select>
</td>
</tr>
<tr><td colspan="2" style="text-align: center;"><input type="submit" value="Gerar Navegação" formaction="servletCalculaNavegacao" formmethod="post"></td></tr>
</table>
</form>
</div>


<% if(!request.getSession().getAttribute("navegacao").equals("")){
	Planejamento planejamento = new Planejamento();
	planejamento = (Planejamento) request.getSession().getAttribute("navegacao");
	ArrayList<Trecho> trechos = planejamento.getTrechos();
	double distanciatotal = 0;
	int tempototal = 0;
	int consumototal = 0;
	out.println("<div class=\"card\">");
	out.println("<table cellspacing=\"0\"><tr><th colspan=\"2\">De</th><th>Para</th><th>Rumo</th><th>Distancia</th><th>Tempo</th><th>Consumo</th></tr>");
	for(int i = 0; i < trechos.size(); i++){
		double distancia = trechos.get(i).getDistancia();
		int tempo = trechos.get(i).getTempoMinutos();
		int consumo = (tempo * planejamento.getAeronave().getConsumo()) / 60;
		distanciatotal += distancia;
		tempototal += tempo;
		consumototal += consumo;
		out.println("<tr" + 
				((i%2 == 0) ? " class=\"linha-par\"" : "") +
				"><td  class=\"nome\">" + 
				(trechos.get(i).getNomeInicio() != null ? trechos.get(i).getNomeInicio() : trechos.get(i).getInicio().latitudeToString() + "<br>" + trechos.get(i).getInicio().longitudeToString())
				+"</td><td class=\"nome\">/</td><td class=\"nome\">" +
				(trechos.get(i).getNomeFim() != null ? trechos.get(i).getNomeFim() : trechos.get(i).getFim().latitudeToString() + "<br>" + trechos.get(i).getFim().longitudeToString())
				+"</td><td  class=\"rumo\">" +
				String.format("%03d",trechos.get(i).getRumo()) + "°"
				+"</td><td  class=\"distancia\">" + 
				String.format("%.1f",distancia) + "nm"
				+ "</td><td class=\"tempo\">" + 
				String.format("%02d",(tempo/60)) + ":" + String.format("%02d",(tempo%60))
				+ "</td><td class=\"consumo\">" +
				consumo + " L"
				+ "</td></tr>");
	}
	out.println("<tr><td></td><td></td><td></td><td class=\"total\">Total:</td><td class=\"total distancia\">" + 
			String.format("%.1fnm",distanciatotal) 
			+ "</td><td class=\"total tempo\">" + 
			String.format("%02d",(tempototal/60)) + ":" + String.format("%02d",(tempototal%60)) 
			+ "</td><td class=\"consumo\">" + 
			consumototal + " L"
			+ "</td></tr></table>");
	out.println("<div>");
	out.println("<form>");
	out.println("<input type=\"submit\" value=\"Gerar Kneeboard\" formaction=\"servletKneeboard\" formmethod=\"post\">");
	out.println("<input type=\"submit\" value=\"Salvar Navegação\" formaction=\"servletSalvarNavegacao\" formmethod=\"post\">");
	out.println("</form>");
	out.println("</div>");
	out.println("</div>");
}%>
 <script src="./js/rota.js"></script>
 <script src="./js/mostraAltitude.js"></script>
</body>

</html>