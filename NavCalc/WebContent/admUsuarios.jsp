<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin > Usuários</title>
<style>
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
<div style="text-align: right"><form name="logoff" action="servletLogoff" method="post"><a href="javascript:logoff.submit()" style="text-decoration: none;"><label class="btn_logoff">Logoff</label></a></form></div>
<div class="btn_voltar"><a href="admin.jsp" style="text-decoration: none; margin: 0 auto;"><img src="images/icons/voltar.png" style="width: 25px;"><span style="color: rgb(46,204,113)"> Voltar</span></a></div>
<div style="width: 770px; margin: 0 auto;">
<h1 style="width: 770px; text-align: center; color: rgb(167, 236, 38); font-family: sans-serif; font-size: 40px;">Gerenciamento de Usuários</h1>
<table style="margin-top: 100px;">
<tr>
<td>
	<form name="relatoriousuarios" action="servletRelatorioUsuariosEmail" method="post">
	<a style="text-decoration: none;" href="javascript:relatoriousuarios.submit()">
	<span style="font-size: 50px; color: rgb(23, 198, 148); font-family: sans-serif">Relatório de usuários cadastrados</span>
	<p style="color: rgb(3, 168, 216); margin-top: 0;"><i>Informações dos usuários cadastrados</i></p>
	</a>
	</form>
</td>
</tr>
<tr>
<td>
	<form name="relatorioplanejamentos" action="servletRelatorioPlanejamentos" method="post">
	<a style="text-decoration: none;" href="javascript:relatorioplanejamentos.submit()">
	<span style="font-size: 50px; color: rgb(23, 198, 148); font-family: sans-serif">Planejamentos de voo</span>
	<p style="color: rgb(3, 168, 216); margin-top: 0;"><i>Lista de planejamentos salvos, agrupados por usuário</i></p>
	</a>
	</form>
</td>
</tr>
</table>
</div>
</body>
</html>