<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="controller.Aeronave" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin > Aeronaves</title>
<Style>
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
th{
text-align: left;
font-family: sans-serif;
font-size: 20px;
}
.dt_nascimento{
text-align: center;
}
th{
padding: 15px;
}
td{
padding-top: 5px;
padding-right: 5px;
padding-bottom: 5px;
padding-left: 45px;
font-size: 25px;
}
.direita{
text-align: right;
}
.centro{
text-align: center;
padding-right: 10px;
padding-left: 25px;
}
a{
text-decoration: none;
}
.ico_editar_excluir{
	width: 25px;
}
.btn_voltar{
font-family: sans-serif;
font-size: 50px;
font-weight: bold;
color: rgb(46,204,113);
text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}
</Style>
</head>
<body style="background: #303030">
<div style="text-align: center; margin: 80px;"><span class="titulo">Lista de Aeronaves Padrão</span></div>
<% 
	ArrayList<Aeronave> lista = (ArrayList<Aeronave>) request.getAttribute("lista_aeronaves"); 
	if(lista.isEmpty()){
		out.println("<h2 style=\"color: red;\">Não há aeronaves cadastradas no momento.</h2>");
	}else{
		out.println("<table class=\"card\">");
		out.println("<tr>");
		out.println("<th><form name=\"organizaTipo\" action=\"servletAeronavesPadrao\" method=\"post\"><a href=\"javascript:organizaTipo.submit()\"><img src=\"images/icons/sort.png\">Tipo</a></form></th>");
		out.println("<th><form name=\"organizaVC\" action=\"servletAeronavesPadraoVC\" method=\"post\"><a href=\"javascript:organizaVC.submit()\"><img src=\"images/icons/sort.png\">Velocidade de Cruzeiro</a></form></th>");
		out.println("<th><form name=\"organizaVS\" action=\"servletAeronavesPadraoVS\" method=\"post\"><a href=\"javascript:organizaVS.submit()\"><img src=\"images/icons/sort.png\">Velocidade de Subida</a></form></th>");
		out.println("<th><form name=\"organizaVD\" action=\"servletAeronavesPadraoVD\" method=\"post\"><a href=\"javascript:organizaVD.submit()\"><img src=\"images/icons/sort.png\">Velocidade de Descida</a></form></th>");
		out.println("<th><form name=\"organizaRS\" action=\"servletAeronavesPadraoRS\" method=\"post\"><a href=\"javascript:organizaRS.submit()\"><img src=\"images/icons/sort.png\">Razão de Subida</a></form></th>");
		out.println("<th><form name=\"organizaRD\" action=\"servletAeronavesPadraoRD\" method=\"post\"><a href=\"javascript:organizaRD.submit()\"><img src=\"images/icons/sort.png\">Razão de Descida</a></form></th>");
		out.println("<th><form name=\"organizaConsumo\" action=\"servletAeronavesPadraoConsumo\" method=\"post\"><a href=\"javascript:organizaConsumo.submit()\"><img src=\"images/icons/sort.png\">Consumo</a></form></th>");
		out.println("<th colspan=\"2\"></td></tr>");
		for(int i = 0; i < lista.size(); i++){
			out.println("<tr style=\"padding: 0;\"><td colspan=\"10\" style=\"padding: 0;\"><hr></td></tr>");
			out.println("<tr>");
			out.println("<td>" + lista.get(i).getTipo() + "</td>");
			out.println("<td class=\"direita\">" + lista.get(i).getVelocidadeCruzeiro() + "kt</td>");
			out.println("<td class=\"direita\">" + lista.get(i).getVelocidadeSubida() + "kt</td>");
			out.println("<td class=\"direita\">" + lista.get(i).getVelocidadeDescida() + "kt</td>");
			out.println("<td class=\"direita\">" + lista.get(i).getRazaoSubida() + "ft/min</td>");
			out.println("<td class=\"direita\">" + lista.get(i).getRazaoDescida() + "ft/min</td>");
			out.println("<td class=\"direita\">" + lista.get(i).getConsumo() + "litros/hora</td>");
			out.println("<td class=\"centro\"><form name=\"edita_aeronave\" action=\"servletEditaAeronavePadrao\" method=\"post\"><input name=\"idAeronave\" type=\"hidden\" value=\"" + lista.get(i).getId() + "\"><input type=\"image\" class=\"ico_editar_excluir\" src=\"images/icons/editar.png\" alt=\"submit\"></form></td>");
			out.println("<td class=\"centro\"><form name=\"edita_aeronave\" action=\"servletExcluiAeronavePadrao\" method=\"post\" onsubmit=\"return confirm('Tem certeza de que deseja excluir essa aeronave?')\"><input name=\"idAeronave\" type=\"hidden\" value=\"" + lista.get(i).getId() + "\"><input type=\"image\" class=\"ico_editar_excluir\" src=\"images/icons/excluir.png\" alt=\"submit\"></form></td>");
			out.println("</tr>");
		}
		out.println("<tr><td colspan=\"10\" style=\"text-align: center;\"><a href=\"admin.jsp\"><img src=\"images/icons/voltar.png\"><span class=\"btn_voltar\">Voltar</span></a></td></tr>");
		out.println("</table>");
	}
%>
<div style="text-align: center; margin: 50px;">
<a href="cadastrarAeronavePadrao.jsp" style="text-decoration: none;">
<img src="images/icons/novo.png"><br>
<label class="titulo" style="font-size: 30px;">Adicionar Nova</label>
</a>
</div>
</body>
</html>