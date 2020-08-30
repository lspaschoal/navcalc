<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="controller.Planejamento"%>
<%@ page import="controller.Trecho"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Kneeboard</title>
<link rel="stylesheet" type="text/css" href="css/painel.css">
<link rel="stylesheet" type="text/css" href="css/tabela-navegacao.css">
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
		<form name="navegacao" action="servletNavegacao" method="post"><a href="javascript:navegacao.submit()">Planejamento</a></form> 
		<a href="rotas_salvas.jsp">Rotas Salvas</a>
		<form name="gerencia_aeronaves" action="servletGerenciaAeronaves" method="post"><a href="javascript:gerencia_aeronaves.submit()">Aeronaves</a> </form> 
		<form name="logoff" action="servletLogoff" method="post"><label class="logoff" onclick="javascript:logoff.submit()">Logoff</label></form>
		<label class="usuario"><%=request.getSession().getAttribute("email")%></label>
	</div>
	<div style="width: 786px; margin: 0 auto;">
	<% if(!request.getSession().getAttribute("navegacao").equals("")){
	Planejamento planejamento = new Planejamento();
	planejamento = (Planejamento) request.getSession().getAttribute("navegacao");
	ArrayList<Trecho> trechos = planejamento.getTrechos();
	out.println("<input type=\"hidden\" id=\"nTrechos\" value=\"" + trechos.size() + "\">");
	double distanciatotal = 0;
	int tempototal = 0;
	out.println("<div class=\"card\">");
	out.println("<div style=\"text-align: center;\"><h1>Tabela de Navegação</h1></div>");
	out.println("<table cellspacing=\"0\">" 
				+ "<tr style=\"margin: 0;\">"
				+ "<td colspan=\"3\"></td>"
				+ "<td colspan=\"2\" style=\"font-size: 19px; font-weight: bold;vertical-align: bottom;padding-left: 16px;\">hh:mm</td>"
				+ "<td><button type=\"button\" id=\"btnAcionamento\" style=\"text-align: center; width: 96px\" onclick=\"setAcionamento()\">Acionamento</button></td>"
				+ "<td><button type=\"button\" id=\"btnTaxi\" style=\"text-align: center; width: 96px\" onclick=\"setTaxi()\">Taxi</button></td>"
				+ "<td><button type=\"button\" id=\"btnDecolagem\" style=\"text-align: center; width: 96px\" onclick=\"setDecolagem()\">Decolagem</button></td>"
				+ "<td></td></tr>");
	out.println("<tr>"
				+ "<td colspan=\"3\" style=\"font-size: 18px; font-weight: bolder;\">Autonomia Inicial:</td>"
				+ "<td colspan=\"2\" id=\"idAutonomiaDigitada\"><input type=\"time\" id=\"idAutonomiaInicial\" style=\"font-size: 18px;\"><input type=\"button\" value=\"Iniciar\" onclick=\"validaAutonomia()\"></td>"
				+ "<td style=\"text-align: center;\"><input type=\"hidden\" id=\"idDateAcionamento\"><span id=\"idHrAcionamento\" style=\"font-size: 20px; font-weight: bolder;\">00:00</span></td>"
				+ "<td style=\"text-align: center;\"><input type=\"hidden\" id=\"idDateTaxi\"><span id=\"idHrTaxi\" style=\"font-size: 20px; font-weight: bolder;\">00:00</span></td>"
				+ "<td style=\"text-align: center;\"><input type=\"hidden\" id=\"idDateDecolagem\"><span id=\"idHrDecolagem\" style=\"font-size: 20px; font-weight: bolder;\">00:00</span></td>"
				+ "<td></td></tr>");
	out.println("<tr style=\"height: 20px;\"></tr>");
	out.println("<tr><th colspan=\"2\">De</th><th>Para</th><th>Rumo</th><th>Distancia</th><th>EET</th><th>ETA</th><th>REAL</th><th>Autonomia</th></tr>");
	for(int i = 0; i < trechos.size(); i++){
		double distancia = trechos.get(i).getDistancia();
		int tempo = trechos.get(i).getTempoMinutos();
		distanciatotal += distancia;
		tempototal += tempo;
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
				+ "</td><td style=\"border-top: 1px solid; text-align: center;\"><span id=\"idEET" + i +"\" class=\"tempo\" >" + 
				String.format("%02d",(tempo/60)) + ":" + String.format("%02d",(tempo%60)) + "</span>"
				+ "</td><td style=\"border-top: 1px solid; text-align: center;\"><input type=\"hidden\" id=\"idDateETA" + i +"\"><span id=\"idETA" + i +"\" class=\"tempo\"></span>"
				+ "</td><td style=\"border-top: 1px solid; text-align: center;\"><input type=\"hidden\" id=\"idDateReal" + i +"\"><span id=\"idReal" + i +"\" class=\"tempo\"></span>"
				+ "</td><td style=\"border-top: 1px solid; text-align: center;\"><span id=\"idAutonomia" + i +"\" class=\"tempo\"></span>"
				+ "</td></tr>");
	}
	out.println("<tr><td style=\"border-top: 1px solid;\"></td><td style=\"border-top: 1px solid;\"></td><td style=\"border-top: 1px solid;\"></td><td class=\"total\" style=\"border-top: 1px solid;\">Total:</td><td class=\"total distancia\" style=\"border-top: 1px solid;\">" + 
			String.format("%.1fnm",distanciatotal) 
			+ "</td><td class=\"total tempo\" style=\"border-top: 1px solid;\" id=\"idTempoTotal\">" + 
			String.format("%02d",(tempototal/60)) + ":" + String.format("%02d",(tempototal%60)) 
			+ "</td><td class=\"consumo\" style=\"border-top: 1px solid;\">"
			+ "</td><td style=\"border-top: 1px solid;\"></td><td style=\"border-top: 1px solid;\"></td></tr>");
	out.println("</div>");
}%>
	</div>
<script src="./js/kneeboard.js"></script>
</body>
</html>