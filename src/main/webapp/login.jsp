<%--
  Created by IntelliJ IDEA.
  User: joachim
  Date: 30.10.2021
  Time: 02:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="sidebar.jsp"></jsp:include>
    <section class='main'>
        <h1>Login</h1>
        <form action='login' method='POST'>
            <label for='email'>email:</label>
            <input type='email' name='email'>
            <label for='password'>password:</label>
            <input type='password' name='password'>
            <input type='submit' >
        </form>
        <br>
        <a href='register'>Don't have an account already? Register here!</a>
    </section>
</body>
</html>
