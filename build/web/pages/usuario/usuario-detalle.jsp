<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Detalle de Usuario</title>
</head>
<body>
    <h2>Detalle del Usuario</h2>

    <c:if test="${usuario != null}">
        <p><strong>ID:</strong> ${usuario.id}</p>
        <p><strong>Rol:</strong> ${usuario.rol}</p>
        <p><strong>Nombre:</strong> ${usuario.nombre}</p>
        <p><strong>Correo:</strong> ${usuario.correo}</p>
    </c:if>

    <c:if test="${usuario == null}">
        <p>No se encontró el usuario solicitado.</p>
    </c:if>

    <br><a href="../../index.jsp">Volver al inicio</a>
</body>
</html>
