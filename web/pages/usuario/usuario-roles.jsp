<%-- 
    Document   : usuario-roles
    Created on : 13/10/2025, 9:08:32 p. m.
    Author     : Steven
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Roles</title>
</head>
<body>
    <h2>Roles disponibles</h2>

    <ul>
        <c:forEach var="rol" items="${roles}">
            <li>${rol}</li>
        </c:forEach>
    </ul>

    <br><a href="../../index.jsp">Volver al inicio</a>
</body>
</html>

