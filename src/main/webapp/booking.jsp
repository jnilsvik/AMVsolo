<%--
  Created by IntelliJ IDEA.
  User: joachim
  Date: 30.10.2021
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>booking</title>
</head>
<body>
    <jsp:include page="/sidebar.jsp"/>
    <section class="main">
        <div class="booking">
            ${email}
            <h1>Booking Confirmed</h1>
            <p>Booked from ${startDate} to ${endDate}</p>
            <p>By ${fullname}</p>
            <br>
            <p>You may collect the ${tool}</p>
            <p>${startDate} at you convenience</p>
            <button type="submit" formaction="landing.jsp">Continue</button>
        </div>
    </section>
</body>
</html>
