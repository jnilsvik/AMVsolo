<%--
  Created by IntelliJ IDEA.
  User: joachim
  Date: 30.10.2021
  Time: 03:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <section class=\"main\">
        <h1>Find Tool</h1>
        <form action='register' method='POST'>
            <label for = 'firstname'>First Name: </label><br>
            <input type = 'text' name = 'firstname' required><br>
            <label for = 'lastname' required>Last Name: </label><br>
            <input type = 'text' name = 'lastname' required><br>
            <label for = 'email'>Email: </label><br>
            <input type = 'email' name = 'email' required><br>
            <label for = 'password'>Password: </label><br>
            <input type = 'password' name = 'password' required><br>
            <label for = 'phone'>Phone: </label><br>
            <input type = 'tel' name = 'phone' required pattern=\"[0-9]{5,9}\"><br>
            <label for = 'unionmember'>Union Member: </label><br>
            <input type = 'checkbox' name = 'unionmember' value = 'true'><br>
            <label for = 'userAdmin'>Admin: </label><br>
            <input type = 'checkbox' name = 'userAdmin' value = 'true'><br>
            <input type = 'submit' value = 'Register User'>
            </form>
    </section>
</body>
</html>
