<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="controller.Usuario" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin > Usuários > Relatório de Usuários</title>
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
th{
text-align: left;
font-family: sans-serif;
font-size: 28px;
}
.email, .nome, .dt_nascimento{
font-size: 25px;
}
.nome{
min-width: 150px
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
}
a{
text-decoration: none;
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
<div style="text-align: right"><form name="logoff" action="servletLogoff" method="post"><a href="javascript:logoff.submit()" style="text-decoration: none;"><label class="btn_logoff">Logoff</label></a></form></div>
<div class="btn_voltar"><a href="admUsuarios.jsp" style="text-decoration: none; margin: 0 auto;"><img src="images/icons/voltar.png" style="width: 25px;"><span style="color: rgb(46,204,113)"> Voltar</span></a></div>
<div style="text-align: center; margin: 100px;"><span class="titulo">Lista de Usuários Cadastrados</span></div>
<% 
	ArrayList<Usuario> lista = (ArrayList<Usuario>) request.getAttribute("lista_usuarios"); 
	if(lista.isEmpty()){
		out.println("<h2 style=\"color: red;\">Não há usuários cadastrados no momento.</h2>");
	}else{
		out.println("<table class=\"card\">");
		out.println("<tr>");
		out.println("<th><form name=\"organizaEmail\" action=\"servletRelatorioUsuariosEmail\" method=\"post\"><a href=\"javascript:organizaEmail.submit()\"><img src=\"images/icons/sort.png\">Email</a></form></th>");
		out.println("<th><form name=\"organizaNome\" action=\"servletRelatorioUsuariosNome\" method=\"post\"><a href=\"javascript:organizaNome.submit()\"><img src=\"images/icons/sort.png\">Nome</a></form></th>");
		out.println("<th><form name=\"organizaSobrenome\" action=\"servletRelatorioUsuariosSobrenome\" method=\"post\"><a href=\"javascript:organizaSobrenome.submit()\"><img src=\"images/icons/sort.png\">Sobrenome</a></form></th>");
		out.println("<th><form name=\"organizaDataNacimento\" action=\"servletRelatorioUsuariosDataNascimento\" method=\"post\"><a href=\"javascript:organizaDataNacimento.submit()\"><img src=\"images/icons/sort.png\">Nascimento</a></form></th>");
		out.println("</tr>");
		for(int i = 0; i < lista.size(); i++){
			out.println("<tr style=\"padding: 0;\"><td colspan=\"4\" style=\"padding: 0;\"><hr></td></tr>");
			out.println("<tr>");
			out.println("<td class=\"email\">" + lista.get(i).getEmail() + "</td>");
			out.println("<td class=\"nome\">" + lista.get(i).getNome() + "</td>");
			out.println("<td class=\"nome\">" + lista.get(i).getSobrenome() + "</td>");
			out.println("<td class=\"dt_nascimento\">" + (lista.get(i).getDtNascimento().getDayOfMonth() < 10? "0" : "") + lista.get(i).getDtNascimento().getDayOfMonth() + "/" + (lista.get(i).getDtNascimento().getMonthValue() < 10? "0" : "") + lista.get(i).getDtNascimento().getMonthValue() + "/" + lista.get(i).getDtNascimento().getYear() + "</td>");
			out.println("</tr>");
		}
		out.println("</table>");
	}
%>

</body>
</html>