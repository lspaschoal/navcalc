<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Planejamento</title>

</head>
<body>
	<div>
		<table>
		<tr><th>Perna</th><th>Distância</th><th>Rumo</th><th>Tempo</th></tr>
			<%
				ArrayList<String> pernas = (ArrayList<String>) request.getAttribute("pernas");
				ArrayList<String> distancias = (ArrayList<String>) request.getAttribute("distancias");
				ArrayList<String> rumos = (ArrayList<String>) request.getAttribute("rumos");
				ArrayList<String> tempos = (ArrayList<String>) request.getAttribute("tempos");
				ArrayList<String> altitudesIniciais = (ArrayList<String>) request.getAttribute("altitudesIniciais");
				ArrayList<String> altitudesFinais = (ArrayList<String>) request.getAttribute("altitudesFinais");
			%>
			<%
				for (int i = 0; i < pernas.size(); i++) {
				out.println(
				"<tr><td>" + pernas.get(i) + "</td><td>" + distancias.get(i) + "</td><td>" + rumos.get(i) + "</td><td>" + tempos.get(i) + "</td></tr>");
			}
			%>
		</table>
	</div>
</body>
</html>