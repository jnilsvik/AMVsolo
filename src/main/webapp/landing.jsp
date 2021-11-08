<%--
  Created by IntelliJ IDEA.
  User: joachim
  Date: 30.10.2021
  Time: 02:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Index</title>
</head>
<body>
<jsp:include page="/_sidebar.jsp"/>
<!--content goes here-->
<section class="main">
    <h1>Hello ${email}</h1>
    <h1>Hello ${name}</h1>

    <h1>Some stuff is missing here :)</h1>
    <br>
    <a href="login">Login </a>
    <br>
    <a href="register">Register</a>
    <br>
    <a href="tl">tool list</a>
    <br>
    <a href="lu">employee list</a>
    <a href="lt">tool list</a>
    <br>
    <br>
    <a href="">TEST</a>
</section>
</body>
</html>
