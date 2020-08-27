<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin - Principal</title>
</head>
<body style="background: #303030">
<div style="width: 500px; margin: 0 auto;">
<h1 style="width: 500px; text-align: center; color: rgb(167, 236, 38); font-family: sans-serif; font-size: 40px;">Menu do Administrador</h1>
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
	<a style="text-decoration: none;">
	<span style="font-size: 50px; color: rgb(23, 198, 148); font-family: sans-serif">Aeródromos</span>
	<p style="color: rgb(3, 168, 216); margin-top: 0;"><i>Gerenciamento de aeródromos cadastrados</i></p>
	</a>
</td>
</tr>
<tr>
<td>
	<a style="text-decoration: none;" href="AdmFixos.jsp">
	<span style="font-size: 50px; color: rgb(23, 198, 148); font-family: sans-serif">Fixos</span>
	<p style="color: rgb(3, 168, 216); margin-top: 0;"><i>Gerenciamento de fixos cadastrados</i></p>
	</a>
</td>
</tr>
</table>
</div>
</body>
</html>