<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="controller.Aeronave" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin > Aeródromos > Cadastrar Aeródromo</title>
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
position: fixed;
top: 10px;
left: 50px;
background: white;
width: 110px;
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
input[type="number"]{
width: 90px;
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
</style>
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
<div style="text-align: center; margin: 80px;"><span class="titulo">Cadastrar Aeródromo</span></div>
<div class="btn_voltar"><form action="servletAerodromosICAO" name="aerodromos"><a href="javascript:aerodromos.submit()" style="text-decoration: none; margin: 0 auto;"><img src="images/icons/voltar.png" style="width: 25px;"><span style="color: rgb(46,204,113)"> Voltar</span></a></form></div>
<form action="servletCadastraAerodromo" method="post" onsubmit="return confirm('Tem certeza de que deseja salvar essas alterações?')">
<table class="card">
<tr><td></td><td><label class="erro"><% if(request.getAttribute("erroIcao") != null){out.print(request.getAttribute("erroIcao"));} %></label></td></tr>
<tr><td>Código ICAO:</td><td><input type="text" name="icao" value="<% if(request.getAttribute("icao") != null){out.print(request.getAttribute("icao"));} %>" placeholder="Digite o código ICAO"></td></tr>
<tr><td></td><td><label class="erro"><% if(request.getAttribute("erroNome") != null){out.print(request.getAttribute("erroNome"));} %></label></td></tr>
<tr><td>Nome:</td><td><input type="text" name="nome" value="<% if(request.getAttribute("nome") != null){out.print(request.getAttribute("nome"));} %>" placeholder="Digite nome do aeródromo"></td></tr>
<tr><td></td><td><label class="erro"><% if(request.getAttribute("erroElevacao") != null){out.print(request.getAttribute("erroElevacao"));} %></label></td></tr>
<tr><td>Elevação:</td><td><input type="number" name="elevacao" value="<% if(request.getAttribute("elevacao") != null){out.print(request.getAttribute("elevacao"));} %>" placeholder="pés">ft</td></tr>
<tr><td></td><td><label class="erro"><% if(request.getAttribute("erroLatitude") != null){out.print(request.getAttribute("erroLatitude"));} %></label></td></tr>
<tr><td>Latitude:</td><td><input type="number" name="latGraus" value="<% if(request.getAttribute("latGraus") != null){out.print(request.getAttribute("latGraus"));} %>" placeholder="Graus">°
						  <input type="number" name="latMin" value="<% if(request.getAttribute("latMin") != null){out.print(request.getAttribute("latMin"));} %>" placeholder="Minutos">'
						  <input type="number" name="latSeg" value="<% if(request.getAttribute("elevacao") != null){out.print(request.getAttribute("latSeg"));} %>" placeholder="Segundos">"
						  <select name="NS"><option value="N" <% if(request.getAttribute("NS") != null){if(request.getAttribute("NS").equals("N")){out.print("selected");}} %>>N</option><option value="S" <% if(request.getAttribute("NS") != null){if(request.getAttribute("NS").equals("S")){out.print("selected");}}else{out.print("selected");} %>>S</option></select>
						  </td></tr>
<tr><td></td><td><label class="erro"><% if(request.getAttribute("erroLongitude") != null){out.print(request.getAttribute("erroLongitude"));} %></label></td></tr>
<tr><td>Longitude:</td><td><input type="number" name="lonGraus" value="<% if(request.getAttribute("lonGraus") != null){out.print(request.getAttribute("lonGraus"));} %>" placeholder="Graus">°
						   <input type="number" name="lonMin" value="<% if(request.getAttribute("lonMin") != null){out.print(request.getAttribute("lonMin"));} %>" placeholder="Minutos">'
						   <input type="number" name="lonSeg" value="<% if(request.getAttribute("lonSeg") != null){out.print(request.getAttribute("lonSeg"));} %>" placeholder="Segundos">"
						   <select name="WE"><option value="E" <% if(request.getAttribute("WE") != null){if(request.getAttribute("WE").equals("E")){out.print("selected");}} %>>E</option><option value="W" <% if(request.getAttribute("WE") != null){if(request.getAttribute("WE").equals("W")){out.print("selected");}}else{out.print("selected");} %>>W</option></select>
						   </td></tr>
<tr><td></td><td><input type="submit" value="Salvar Alterações"></td></tr>
<tr><td colspan=3><% if(request.getAttribute("status") != null){out.print(request.getAttribute("status"));} %></td></tr>
</table>
</form>
</body>
</html>