<%--
  Created by IntelliJ IDEA.
  User: Joachim
  Date: 29.10.2021
  Time: 10:24
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="sidebar.jsp"/>
<section class="main">
    <h1>Find Tool</h1>
    <form action='GetTool' method='GET'>
        <label for='toolID'>Tool ID:</label>
        <input type='number' name='toolID'/>
        <input type='submit' />
    </form>
    <form action='GetTool2' method='GET'>
        <label for='toolName'>Tool name:</label>
        <input type='text' name='toolName'/>
        <input type='submit' />
    </form>

    <h3>Tool Stuff</h3>
    <table>
        <tr>
            <th>toolName</th>
            <th>category</th>
            <th>priceFirst</th>
            <th>priceAfter</th>
            <th>maintenance</th>
            <th>certificateID</th>
        </tr>

    </table>
</section>
</body>
</html>