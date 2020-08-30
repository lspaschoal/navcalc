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
<style>
.propertyContainer{
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    flex: 0 0 450px;
    min-height: 30px;
}
.property {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    margin-bottom: 10px;
    height: 30px;
}
</style>
</head>

<body>
<% 
try{
	String id = request.getSession().getAttribute("id").toString();
}catch(NullPointerException npe){
	request.setAttribute("msgErro", "É necessário estar logado para acessar o sistema.");
	request.getRequestDispatcher("login.jsp").forward(request, response);
} %>
	<div class="topnav">
		<a href="painel.jsp">Painel Principal</a>
		<form name="navegacao" action="servletNavegacao" method="post"><a href="javascript:navegacao.submit()" class="active">Planejamento</a></form> 
		<a href="rotas_salvas.jsp">Rotas Salvas</a>
		<form name="gerencia_aeronaves" action="servletGerenciaAeronaves" method="post"><a href="javascript:gerencia_aeronaves.submit()">Aeronaves</a> </form> 
		<form name="logoff" action="servletLogoff" method="post"><label class="logoff" onclick="javascript:logoff.submit()">Logoff</label></form>
		<label class="usuario"><%=request.getSession().getAttribute("email")%></label>
	</div>

<div class="card">
<form>
<table>
<!-- Linha para input de origem -->
<tr>
<td style="text-align: right; border-style: none;"><label for="idorigem">Aeródromo de origem: </label></td><td style="text-align: left; border-style: none;"><input type="text" placeholder="código ICAO" name="origem" id="idorigem" value="<% if(request.getAttribute("origem_digitada") != null) out.print(request.getAttribute("origem_digitada")); %>"></td>
</tr>
<tr>
<td style="border-style: none;"></td><td style="text-align: left; border-style: none;"><label style="color: #990000; font-size: 13px;"><% if(request.getSession().getAttribute("erroOrigem") != null) out.print(request.getSession().getAttribute("erroOrigem")); %></label></td>
</tr>
<!-- Linhas para inputs de fixos  -->
<tr><td colspan="2" style="text-align: center;">Rota<br>Digite o nome do fixo, ou a coodenada no formato 000000(N/S)0000000(W/E)</td></tr>
<%
int nfixos = Integer.parseInt(request.getSession().getAttribute("nfixos").toString());
for(int i = 0; i < nfixos; i++){
	out.println(
			  "<tr>"
			+ "<td style=\"text-align: right; border-style: none;\">"
			+ "<label>Fixo " + ((i < 9) ? "0" : "") + (i+1) + "</label></td>"
			+ "<td style=\"text-align: left; border-style: none;\"><input type=\"text\" id=\"idfixo" + i + "\" name=\"fixo" + i + "\" placeholder=\"nome ou coordenada\" value=\""
			+ ((request.getAttribute("coordenada_digitada" + i) != null) ? request.getAttribute("coordenada_digitada" + i) : "")
			+ "\"></td>"
			+ "</tr>"
			);
	out.println(
			  "<tr>"
			+ "<td style=\"border-style: none;\"></td>"
			+ "<td style=\"text-align: left; border-style: none;\"><label style=\"color: #990000; font-size: 13px;\" name=\"erroFixo" + i + "\">"
			+ ((request.getSession().getAttribute("erroFixo" + i) != null) ? request.getSession().getAttribute("erroFixo" + i) : "")
			+ "</label></td>"
			+ "</tr>"
			);
}
%>
<!-- Linha do botão de adicionar inputs para fixos  -->
<tr>
<td style="border-style: none;"></td><td style="text-align: left; border-style: none;"><input type="submit" value="Adicionar Fixo" formaction="servletAdicionaFixo" formmethod="post"></td>
</tr>
<!-- Linha para input de destino -->
<tr>
<td style="text-align: right;"><label for="iddestino">Aeródromo de destino: </label></td><td style="text-align: left;"><input type="text" placeholder="código ICAO" name="destino" id="iddestino" value="<% if(request.getAttribute("destino_digitado") != null) out.print(request.getAttribute("destino_digitado")); %>"></td>
</tr>
<tr>
<td style="border-style: none;"></td><td style="text-align: left; border-style: none;"><label style="color: #990000; font-size: 13px;"><% if(request.getSession().getAttribute("erroDestino") != null) out.print(request.getSession().getAttribute("erroDestino")); %></label></td>
</tr>
<!-- Linha para input de altitude -->
<tr>
<td style="text-align: right; border-style: none;"><label for="idaltitude">Altitude: </label></td>
<td style="border-style: none;"><input type="range" min="1000" max="14500" step="500" name="altitude" id="idaltitude" onchange="mostraAltitudeSelecionada(this.value);" value="<% if(request.getAttribute("altitude_escolhida") != null) {out.print(request.getAttribute("altitude_escolhida"));}else{out.print(3500);} %>">
<label id="label_altitude"><% if(request.getAttribute("altitude_escolhida") != null) {out.print(request.getAttribute("altitude_escolhida"));}else{out.print(3500);} %>ft</label>
</td>
</tr>
<!-- Linha para input de aeronave -->
<tr>
<td style="text-align: right; border-style: none;"><label for="idaeronave">Aeronave: </label></td>
<td style="text-align: left; border-style: none;">
<select name="aeronave" id="idaeronave">
<optgroup label="Minhas aeronaves">
<%
		ArrayList<AeronavePersonalizada> aeronaves_usuario = (ArrayList<AeronavePersonalizada>) request.getSession().getAttribute("listaAeronavesUsuario");
	%>
<% if (!aeronaves_usuario.isEmpty()) {
	for (AeronavePersonalizada ap : aeronaves_usuario){
		out.print("<option value=\"" + ap.getId() + "\"");
		if(request.getSession().getAttribute("aeronave_personalizada") != null){
			if(request.getSession().getAttribute("aeronave_personalizada").equals("true") && request.getSession().getAttribute("id_aeronave").equals(ap.getId())){
				out.print(" selected");
			}
		}
		out.println(">" + ap.getPrefixo() + "</option>");
	}
} %>
</optgroup>
<optgroup label="Aeronaves genéricas">
<%
		ArrayList<Aeronave> aeronaves_padrao = (ArrayList<Aeronave>) request.getSession().getAttribute("listaAeronavesPadrao");
	%>
<% if (!aeronaves_padrao.isEmpty()) {
	for (Aeronave a : aeronaves_padrao){
		out.print("<option value=\"" + a.getTipo() + "\"");
		if(request.getSession().getAttribute("aeronave_personalizada") != null){
			if(request.getSession().getAttribute("aeronave_personalizada").equals("false") && request.getSession().getAttribute("tipo_acft").equals(a.getTipo())){
				out.print(" selected");
			}
		}
		out.println(">" + a.getTipo() + "</option>");
	}
} %>
</optgroup>
</select>
</td>
</tr>
<tr><td colspan="2" style="text-align: center; border-style: none;"><input type="submit" value="Gerar Navegação" formaction="servletCalculaNavegacao" formmethod="post"></td></tr>
</table>
<input type="hidden" name="id_planejamento" value="<%= (request.getAttribute("id_planejamento") != null ? request.getAttribute("id_planejamento") : 0) %>">
</form>
</div>
<% if(!request.getSession().getAttribute("navegacao").equals("")){
	Planejamento planejamento = new Planejamento();
	planejamento = (Planejamento) request.getSession().getAttribute("navegacao");
	ArrayList<Trecho> trechos = planejamento.getTrechos();
	double distanciatotal = 0;
	int tempototal = 0;
	int consumototal = 0;
	out.println("<div style=\"margin-top: 70px;\"><label style=\"font-size: 20px; font-weight: bold;\">Origem: </label><label style=\"font-size: 20px; font-family: monospace;\">" + planejamento.getOrigem().getNome() + "</label><br>");
	out.println("<label style=\"font-size: 20px; font-weight: bold;\">Destino: </label><label style=\"font-size: 20px; font-family: monospace;\">" + planejamento.getDestino().getNome() + "</label></div>");
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
				"><td  class=\"nome\" style=\"border-top: 1px solid;\">" + 
				(trechos.get(i).getNomeInicio() != null ? trechos.get(i).getNomeInicio() : trechos.get(i).getInicio().latitudeToString() + "<br>" + trechos.get(i).getInicio().longitudeToString())
				+"</td><td class=\"nome\" style=\"border-top: 1px solid;\">/</td><td class=\"nome\" style=\"border-top: 1px solid;\">" +
				(trechos.get(i).getNomeFim() != null ? trechos.get(i).getNomeFim() : trechos.get(i).getFim().latitudeToString() + "<br>" + trechos.get(i).getFim().longitudeToString())
				+"</td><td  class=\"rumo\" style=\"border-top: 1px solid;\">" +
				String.format("%03d",trechos.get(i).getRumo()) + "°"
				+"</td><td  class=\"distancia\" style=\"border-top: 1px solid;\">" + 
				String.format("%.1f",distancia) + "nm"
				+ "</td><td class=\"tempo\" style=\"border-top: 1px solid;\">" + 
				String.format("%02d",(tempo/60)) + ":" + String.format("%02d",(tempo%60))
				+ "</td><td class=\"consumo\" style=\"border-top: 1px solid;\">" +
				consumo + " L"
				+ "</td></tr>");
	}
	out.println("<tr><td style=\"border-top: 1px solid;\"></td><td style=\"border-top: 1px solid;\"></td><td style=\"border-top: 1px solid;\"></td><td class=\"total\" style=\"border-top: 1px solid;\">Total:</td><td class=\"total distancia\" style=\"border-top: 1px solid;\">" + 
			String.format("%.1fnm",distanciatotal) 
			+ "</td><td class=\"total tempo\" style=\"border-top: 1px solid;\">" + 
			String.format("%02d",(tempototal/60)) + ":" + String.format("%02d",(tempototal%60)) 
			+ "</td><td class=\"consumo\" style=\"border-top: 1px solid;\">" + 
			consumototal + " L"
			+ "</td></tr></table>");
	out.println("<div>");
	out.println("<form>");
	out.println("<input type=\"submit\" value=\"Gerar Kneeboard\" formaction=\"kneeboard.jsp\" formmethod=\"post\">");
	out.println("<input type=\"hidden\" name=\"id_planejamento_salvo\" " + (request.getAttribute("id_planejamento") != null ? "value=\""+request.getAttribute("id_planejamento")+"\" " : "value=\"0\" ") + "><input type=\"submit\" value=\"Salvar Navegação\" formaction=\"servletSalvarPlanejamento\" formmethod=\"post\">");
	out.println("</form>");
	out.println("</div>");
	if(request.getAttribute("salvo") != null){
		out.println(request.getAttribute("salvo"));
	}
	out.println("</div>");
}%>
 <script src="./js/rota.js"></script>
 <script src="./js/mostraAltitude.js"></script>
</body>

</html>