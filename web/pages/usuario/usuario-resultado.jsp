<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Resultado</title>
</head>
<body>
    <h2>Resultado de la operación</h2>

    <p>${mensaje}</p>

    <c:if test="${usuario != null}">
        <p><strong>Usuario:</strong> ${usuario.nombre} (${usuario.correo})</p>
    </c:if>

    <br><a href="../../index.jsp">Volver al inicio</a>
</body>
</html>
