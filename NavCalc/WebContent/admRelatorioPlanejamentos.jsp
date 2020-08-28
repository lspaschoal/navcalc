<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="controller.Usuario" %>
<%@ page import="controller.Fixo"%>
<%@ page import="controller.Planejamento" %>
<%@ page import="controller.PlanejamentoSalvo" %>
<%@ page import="model.ModelAeronave"%>
<%@ page import="model.ModelAeronavePersonalizada"%>
<%@ page import="model.ModelAerodromo"%>
<%@ page import="model.ModelFixo"%>
<%@ page import="model.ModelPlanejamentoSalvo"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin > Usuários > Relatório de Planejamentos</title>
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
<div style="text-align: center; margin: 100px;"><span class="titulo">Lista de Planejamentos Salvos</span></div>
<span style="width: 700px;">
<%
	ArrayList<Usuario> lista_usuarios = (ArrayList<Usuario>) request.getAttribute("lista_usuarios"); 
	if(lista_usuarios.isEmpty()){
		out.println("<table class=\"card\">");
		out.println("<tr><td><h2 style=\"color: red;\">Não há usuários cadastrados no momento,<br>consequentemente também não há planejamentos cadastrados.</h2></td></tr>");
		out.println("</table>");
	}else{
		ArrayList<ArrayList<PlanejamentoSalvo>> lista_planejamentos = (ArrayList<ArrayList<PlanejamentoSalvo>>) request.getAttribute("lista_planejamentos"); 
		for(int i = 0; i < lista_usuarios.size(); i++){
			out.println("<table class=\"card\" style=\"width: 800px\">");
			out.println("<tr><td colspan=\"5\" style=\"text-align: left;\"><h2>Usuário: " + lista_usuarios.get(i).getEmail() + "</h2></td></tr>");
			if(lista_planejamentos.get(i).isEmpty()){
				out.println("<tr><td colspan=\"5\"><h3>Esse usuário ainda não tem planejamentos salvos</h3></td></tr>");
			}else{
				out.println(
						"<tr><th>Origem"
						+"</th><th>Destino"
						+"</th><th>Rota"
						+"</th><th>Altitude"
						+"</th><th>Aeronave"
						+ "</th></tr>");
				for(PlanejamentoSalvo ps : lista_planejamentos.get(i)){
					Planejamento plan = ps.gerarPlanejamento();
					out.print("<tr><td>" +  plan.getOrigem().getIcao() //origem
							+ "</td><td>" + plan.getDestino().getIcao() //destino
							+ "</td><td>");
				//rota
				if(plan.getRota().isEmpty()){
					out.print("DCT");
				}else{
					for(Fixo f : plan.getRota()){
						if(f.getNome() != null){
							out.print(f.getNome());
						}else{
							out.print(f.latitudeToString()+"/"+f.longitudeToString());
						}
						if(plan.getRota().indexOf(f) < plan.getRota().size()-1){ 
							out.print(" , ");
						}
					}
				}
				out.println("</td><td>" + ps.getAltitude() //altitude
							+"</td><td>" + plan.getAeronave().getTipo()); //aeronave
							if(ps.isAeronave_personalizada()){ //prefixo, se houver
								String prefixo = new ModelAeronavePersonalizada().getAeronave(ps.getId_aeronave()).getPrefixo();
								out.print(" (" + prefixo + ")");
							}
				out.println("</td></tr>");
				}
			}
			out.println("</table>");
		}
	}
%>
</span>
</body>
</html>