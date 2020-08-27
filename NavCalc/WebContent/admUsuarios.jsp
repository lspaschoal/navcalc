<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin > Usuários</title>
</head>
<body style="background: #303030">
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