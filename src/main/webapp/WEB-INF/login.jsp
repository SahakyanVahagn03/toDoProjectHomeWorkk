<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" href="/css/style.css"/>
    <title>loin form
    </title>
</head>
<%String msg = (String) request.getSession().getAttribute("msg");%>
<body>
<div class="login">
    <h2>Login</h2>
    <form action="/login" method="post">
        <%if (msg != null) {%>
        <h3 style="color:red"><%=msg%>
        </h3>
        <%
                request.getSession().removeAttribute("msg");
            }
        %>
        <div class="user">
            <input type="email" name="email" placeholder="email">
        </div>
        <div class="user">
            <input type="password" name="password" placeholder="password">

        </div>
        <input class="btn" type="submit" value="Entrar" onclick="clicar();">

        <div class="link">
            <a class="register_login-btn" href="/register">I haven't an account</a>
        </div>

    </form>
</div>
<script src="/js/script.js"></script>
</body>
</html>
