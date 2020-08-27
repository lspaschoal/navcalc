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
font-size: 50px;
font-weight: bold;
color: rgb(46,204,113);
text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
</style>
</head>
<body style="background: #303030">
<div style="text-align: center; margin: 100px;"><span class="titulo">Lista de Planejamentos Salvos</span></div>
<table class="card">
<%
	ArrayList<Usuario> lista_usuarios = (ArrayList<Usuario>) request.getAttribute("lista_usuarios"); 
	if(lista_usuarios.isEmpty()){
		out.println("<tr><td><h2 style=\"color: red;\">Não há usuários cadastrados no momento,<br>consequentemente também não há planejamentos cadastrados.</h2></td></tr>");
		out.println("<tr><td style=\"text-align: center;\"><a href=\"admUsuarios.jsp\"><img src=\"images/icons/voltar.png\"><span class=\"btn_voltar\">Voltar</span></a></td></tr>");
	}else{
		ArrayList<ArrayList<PlanejamentoSalvo>> lista_planejamentos = (ArrayList<ArrayList<PlanejamentoSalvo>>) request.getAttribute("lista_planejamentos"); 
		for(int i = 0; i < lista_usuarios.size(); i++){
			out.println("<tr><td colspan=\"5\" style=\"text-align: left;\"><h2>Usuário: " + lista_usuarios.get(i).getEmail() + "</h2></td></tr>");
			if(lista_planejamentos.get(i).isEmpty()){
				out.println("<tr><td colspan=\"5\">Esse usuário ainda não tem planejamentos salvos</td></tr>");
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
		}
		out.println("<tr><td colspan=\"4\" style=\"text-align: center;\"><a href=\"admUsuarios.jsp\"><img src=\"images/icons/voltar.png\"><span class=\"btn_voltar\">Voltar</span></a></td></tr>");
	}
%>
</table>
</body>
</html>