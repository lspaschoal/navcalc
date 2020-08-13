<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Novo cadastro</title>
<!--===============================================================================================-->
    <link rel="icon" type="image/png" href="images/icons/favicon.ico" />
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="vendor/animate/animate.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="vendor/css-hamburgers/hamburgers.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="vendor/animsition/css/animsition.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="vendor/select2/select2.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="vendor/daterangepicker/daterangepicker.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="css/estilos.css">
    <!--===============================================================================================-->
</head>
<body>
    <div class="limiter">
        <div class="container-login100">
            <div class="wrap-login100 p-l-55 p-r-55 p-t-65 p-b-50">
                <form class="login100-form validate-form" action="servletCadastro" method="post">
                    <span class="login100-form-title p-b-33">
                        Cadastro<br>
                        <p class="fs-15 erro">
                        <%if(!request.getAttribute("erroGenerico").equals("")){out.println(request.getAttribute("erroGenerico"));} %>
                        </p>
                    </span>

                    <div>
                        <p class="fs-20">Nome</p>
                    </div>
                    <div>
                        <p class="fs-15 erro">
                        <%if(!request.getAttribute("erroNome").equals("")){out.println(request.getAttribute("erroNome"));} %>
                        </p>
                    </div>
                    <div class="wrap-input100">
                        <input class="input100" type="text" name="nome" placeholder="Nome" value="<%= request.getParameter("nome")%>">
                        <span class="focus-input100-1"></span>
                        <span class="focus-input100-2"></span>
                    </div>

                    <div>
                        <p class="fs-20">Sobrenome</p>
                    </div>
                     <div>
                        <p class="fs-15 erro">
                         <%if(!request.getAttribute("erroSobreNome").equals("")){out.println(request.getAttribute("erroSobreNome"));} %>
                         </p>
                    </div>
                    <div class="wrap-input100">
                        <input class="input100" type="text" name="sobrenome" placeholder="Sobrenome" value="<%= request.getParameter("sobrenome")%>">
                        <span class="focus-input100-1"></span>
                        <span class="focus-input100-2"></span>
                    </div>
                    <div>
                        <p class="fs-20">Data de Nascimento</p>
                    </div>
                     <div>
                        <p class="fs-15 erro"> <%if(!request.getAttribute("erroDtNascimento").equals("")){out.println(request.getAttribute("erroDtNascimento"));} %>
                         </p>
                    </div>
                    <div class="wrap-input100">
                        <input class="input100" type="date" name="dtnascimento" placeholder="dd/mm/aaaa"  value="<%= request.getParameter("dtnascimento")%>">
                        <span class="focus-input100-1"></span>
                        <span class="focus-input100-2"></span>
                    </div>
                    <div>
                        <p class="fs-20">E-mail</p>
                    </div>
                     <div>
                        <p class="fs-15 erro"><%if(!request.getAttribute("erroEmail").equals("")){out.println(request.getAttribute("erroEmail"));} %>
                         </p>
                    </div>
                    <div class="wrap-input100">
                        <input class="input100" type="email" name="email" placeholder="E-mail" value="<%= request.getParameter("email")%>">
                        <span class="focus-input100-1"></span>
                        <span class="focus-input100-2"></span>
                    </div>
                    <div>
                        <p class="fs-20">Senha</p>
                    </div>
                     <div>
                        <p class="fs-15 erro"><%if(!request.getAttribute("erroSenha").equals("")){out.println(request.getAttribute("erroSenha"));} %>
                         </p>
                    </div>
                    <div class="wrap-input100">
                        <input class="input100" type="password" name="senha" placeholder="Senha">
                        <span class="focus-input100-1"></span>
                        <span class="focus-input100-2"></span>
                    </div>
                    <div>
                        <p class="fs-20">Digite novamente a senha</p>
                    </div>
                     <div>
                        <p class="fs-15 erro"><%if(!request.getAttribute("erroConfirmaSenha").equals("")){out.println(request.getAttribute("erroConfirmaSenha"));} %>
                         </p>
                    </div>
                    <div class="wrap-input100">
                        <input class="input100" type="password" name="confirmasenha" placeholder="Confirme a senha">
                        <span class="focus-input100-1"></span>
                        <span class="focus-input100-2"></span>
                    </div>

                    <div class="container-login100-form-btn m-t-20">
                        <button class="login100-form-btn" type="submit">
                            Cadastrar
                        </button>
                    </div>
                     </form>
            </div>
           
        </div>
    </div>
</body>
</html>