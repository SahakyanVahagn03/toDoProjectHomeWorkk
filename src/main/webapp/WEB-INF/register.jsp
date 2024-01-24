<%--
  Created by IntelliJ IDEA.
  User: 37494
  Date: 22.01.2024
  Time: 7:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" href="/css/style.css"/>
    <!-- CSS only -->

    <title>register</title>
</head>
<%String msg = (String) request.getSession().getAttribute("msg");%>
<body>
<div class="login">
    <h2>register form</h2>
    <%if (msg != null) {%>
    <h3 style="color:red"><%=msg%>
    </h3>
    <%
            request.getSession().removeAttribute("msg");
        }
    %>
    <form action="/register" method="post" enctype="multipart/form-data">
        <div class="user">
            <input type="text" name="name" placeholder="name">
        </div>
        <div class="user">
            <input type="text" name="surname" placeholder="surname">
        </div>
        <div class="user">
            <input type="email" name="email" placeholder="email">
        </div>
        <div class="user">
            <input type="password" name="password" placeholder="password">
        </div>
        <div class="user">
            <input type="file" name="picture" placeholder="picture">
        </div>
        <input class="btn" type="submit" value="Enter" onclick="clicar();">

        <div class="links">
            <a href="/login">go back</a>
        </div>

    </form>
</div>
<script src="/js/script.js"></script>

</body>
</html>
