<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="controller.Usuario" %>
<%@ page import="controller.AeronavePersonalizada" %>
<%@ page import="model.ModelAeronave"%>
<%@ page import="model.ModelAeronavePersonalizada"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin > Usuários > Relatório de Aeronaves</title>
<link rel="stylesheet" type="text/css" href="css/tabela_aeronaves.css">
<style>
.titulo{
font-family: sans-serif;
font-size: 50px;
font-weight: bold;
color: white;
text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}
.card{
margin: 30px auto;
box-shadow:0px 13px 21px -5px rgba(0.5, 0.5, 0.5, 1);
background: white;
}
.btn_voltar{
font-family: sans-serif;
font-size:25px;
font-weight: bold;
color: rgb(46,204,113);
text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
position: fixed;
top: 10px;
left: 25px;
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
<div style="text-align: right;"><form name="logoff" action="servletLogoff" method="post"><a href="javascript:logoff.submit()" style="text-decoration: none;"><label class="btn_logoff">Logoff</label></a></form></div>
<div class="btn_voltar"><a href="admUsuarios.jsp" style="text-decoration: none; margin: 0 auto;"><img src="images/icons/voltar.png" style="width: 25px;"><span style="color: rgb(46,204,113)"> Voltar</span></a></div>
<div style="text-align: center; margin: 100px;"><span class="titulo">Lista de Aeronaves de Usuários Salvas</span></div>
<span style="width: 700px;">
<%
	ArrayList<Usuario> lista_usuarios = (ArrayList<Usuario>) request.getAttribute("lista_usuarios"); 
	if(lista_usuarios.isEmpty()){
		out.println("<table class=\"card\">");
		out.println("<tr><td><h2 style=\"color: red;\">Não há usuários cadastrados no momento,<br>consequentemente também não há aeronaves pesssoais cadastradas.</h2></td></tr>");
		out.println("</table>");
	}else{
		ArrayList<ArrayList<AeronavePersonalizada>> lista_aeronaves = (ArrayList<ArrayList<AeronavePersonalizada>>) request.getAttribute("lista_aeronaves"); 
		for(int i = 0; i < lista_usuarios.size(); i++){
			out.println("<table class=\"card\" style=\"width: 800px\">");
			out.println("<tr><td colspan=\"5\" style=\"text-align: left;\"><h2>Usuário: " + lista_usuarios.get(i).getEmail() + "</h2></td></tr>");
			if(lista_aeronaves.get(i).isEmpty()){
				out.println("<tr><td colspan=\"5\"><h3>Esse usuário ainda não tem aeronaves pessoais salvas</h3></td></tr>");
			}else{
				out.println(
						"<tr><th>Prefixo"
						+"</th><th>Tipo"
						+"</th><th>Velocidade de Cruzeiro"
						+"</th><th>Velocidade de Subida"
						+"</th><th>Velocidade de descida"
						+"</th><th>Razão de Subida"
						+"</th><th>Razão de Descida"
						+"</th><th>Consumo"
						+ "</th></tr>");
				for(AeronavePersonalizada ap : lista_aeronaves.get(i)){
					out.print("<tr><td>" + ap.getPrefixo() //prefixo
							+ "</td><td>" + ap.getTipo() //tipo
							+ "</td><td>" + ap.getVelocidadeCruzeiro() // velocidade de cruzeiro
							+"kt</td><td>" + ap.getVelocidadeSubida() //velocidade de subida
							+"kt</td><td>" + ap.getVelocidadeDescida() //velocidade de descida
							+"kt</td><td>" + ap.getRazaoSubida() //razão de subida
							+"ft/min</td><td>" + ap.getRazaoDescida() //razão de descida
							+"ft/min</td><td>" + ap.getConsumo() //consumo
							+ "l/h</td></tr>"); //velocidade de subida
				}
			}
			out.println("</table>");
		}
	}
%>
</span>
</body>
</html>