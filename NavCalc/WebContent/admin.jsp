<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin - Principal</title>
<style>
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
<div style="width: 500px; margin: 150px auto;">
<h1 style="width: 450px; text-align: center; color: rgb(167, 236, 38); font-family: sans-serif; font-size: 40px;">Menu do Administrador</h1>
<table style="margin-top: 100px;">
<tr>
<td>
	<a style="text-decoration: none;" href="admUsuarios.jsp">
	<span style="font-size: 50px; color: rgb(23, 198, 148); font-family: sans-serif">Usuários</span>
	<p style="color: rgb(3, 168, 216); margin-top: 0;"><i>Gerenciamento e relatórios de usuários cadastrados</i></p>
	</a>
</td>
</tr>
<tr>
<td>
	<form name="aeronaves_padrao" action="servletAeronavesPadrao" method="post">
	<a style="text-decoration: none;" href="javascript:aeronaves_padrao.submit()">
	<span style="font-size: 50px; color: rgb(23, 198, 148); font-family: sans-serif">Aeronaves Padrão</span>
	<p style="color: rgb(3, 168, 216); margin-top: 0;"><i>Gerenciamento de aeronaves padrão do sistema</i></p>
	</a>
	</form>
</td>
</tr>
<tr>
<td>
	<form name="aerodromos" action="servletAerodromosICAO" method="post">
	<a style="text-decoration: none;" href="javascript:aerodromos.submit()">
	<span style="font-size: 50px; color: rgb(23, 198, 148); font-family: sans-serif">Aeródromos</span>
	<p style="color: rgb(3, 168, 216); margin-top: 0;"><i>Gerenciamento de aeródromos cadastrados</i></p>
	</a>
	</form>
</td>
</tr>
<tr>
<td>
	<form name="fixos" action="servletFixosNome" method="post">
	<a style="text-decoration: none;" href="javascript:fixos.submit()">
	<span style="font-size: 50px; color: rgb(23, 198, 148); font-family: sans-serif">Fixos</span>
	<p style="color: rgb(3, 168, 216); margin-top: 0;"><i>Gerenciamento de fixos cadastrados</i></p>
	</a>
	</form>
</td>
</tr>
</table>
</div>
</body>
</html>