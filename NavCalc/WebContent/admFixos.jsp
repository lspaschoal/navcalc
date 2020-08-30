<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="controller.Fixo" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin > Fixos</title>
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
font-size: 17px;
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
font-size:25px;
font-weight: bold;
color: rgb(46,204,113);
text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
position: fixed;
top: 10px;
left: 50px;
background: white;
width: 110px;
}
.btn_logoff{
display: inline-block;
color: white;
background-color: #b30000;
text-align: center;
font-family: sans-serif;
font-size: 20px;
width: 100px;
height: 30px;
padding-top: 10px;
}
.btn_logoff:hover{
background-color: #800000;
font-weight: bolder;
}
</Style>
</head>
<body style="background: #303030">
<% try{
	String usuario = request.getSession().getAttribute("email").toString();
	if(!usuario.equals("admin")){
		request.setAttribute("msgErro", "É necessário estar logado como admin para acessar esse sistema.");
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}
}catch(NullPointerException npe){
	request.setAttribute("msgErro", "É necessário estar logado para acessar o sistema.");
	request.getRequestDispatcher("login.jsp").forward(request, response);
} %>
<div style="text-align: right"><form name="logoff" action="servletLogoff" method="post"><a href="javascript:logoff.submit()" style="text-decoration: none;"><label class="btn_logoff">Logoff</label></a></form></div>
<div class="btn_voltar"><a href="admin.jsp" style="text-decoration: none; margin: 0 auto;"><img src="images/icons/voltar.png" style="width: 25px;"><span style="color: rgb(46,204,113)"> Voltar</span></a></div>
<div style="text-align: center; margin: 80px;"><span class="titulo">Lista de Fixos</span></div>
<div style="text-align: center; margin: 50px;">
<a href="cadastrarFixo.jsp" style="text-decoration: none;">
<img src="images/icons/novo.png"><br>
<label class="titulo" style="font-size: 30px;">Adicionar Novo Fixo</label>
</a>
</div>
<% 
	ArrayList<Fixo> lista = (ArrayList<Fixo>) request.getAttribute("lista_fixos"); 
	if(lista.isEmpty()){
		out.println("<h2 style=\"color: red;\">Não há Fixos cadastradas no momento.</h2>");
	}else{
		out.println("<table class=\"card\">");
		out.println("<tr>");
		out.println("<th><form name=\"organizaNome\" action=\"servletFixosNome\" method=\"post\"><a href=\"javascript:organizaNome.submit()\"><img src=\"images/icons/sort.png\">Nome</a></form></th>");
		out.println("<th><form name=\"organizaLatitude\" action=\"servletFixosLatitude\" method=\"post\"><a href=\"javascript:organizaLatitude.submit()\"><img src=\"images/icons/sort.png\">Latitude</a></form></th>");
		out.println("<th><form name=\"organizaLongitude\" action=\"servletFixosLongitude\" method=\"post\"><a href=\"javascript:organizaLongitude.submit()\"><img src=\"images/icons/sort.png\">Longitude</a></form></th>");
		out.println("<th colspan=\"2\"></td></tr>");
		for(int i = 0; i < lista.size(); i++){
			out.println("<tr style=\"padding: 0;\"><td colspan=\"10\" style=\"padding: 0;\"><hr></td></tr>");
			out.println("<tr>");
			out.println("<td>" + lista.get(i).getNome() + "</td>");
			out.println("<td>" + lista.get(i).latitudeToString() + "</td>");
			out.println("<td>" + lista.get(i).longitudeToString() + "</td>");
			out.println("<td class=\"centro\"><form name=\"edita_fixo\" action=\"servletEditaFixo\" method=\"post\"><input name=\"idFixo\" type=\"hidden\" value=\"" + lista.get(i).getId() + "\"><input type=\"image\" class=\"ico_editar_excluir\" src=\"images/icons/editar.png\" alt=\"submit\"></form></td>");
			out.println("<td class=\"centro\"><form name=\"excui_fixo\" action=\"servletExcluiFixo\" method=\"post\" onsubmit=\"return confirm('Tem certeza de que deseja excluir esse fixo?')\"><input name=\"idFixo\" type=\"hidden\" value=\"" + lista.get(i).getId() + "\"><input type=\"image\" class=\"ico_editar_excluir\" src=\"images/icons/excluir.png\" alt=\"submit\"></form></td>");
			out.println("</tr>");
		}
		out.println("</table>");
	}
%>
</body>
</html>