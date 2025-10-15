<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Lista de usuarios</title>
</head>
<body>
    <h2>Lista de Usuarios</h2>

    <c:if test="${empty usuarios}">
        <p>No hay usuarios registrados.</p>
    </c:if>

    <c:if test="${not empty usuarios}">
        <table border="1" cellpadding="5">
            <tr>
                <th>ID</th>
                <th>Rol</th>
                <th>Nombre</th>
                <th>Correo</th>
            </tr>
            <c:forEach var="u" items="${usuarios}">
                <tr>
                    <td>${u.id}</td>
                    <td>${u.rol}</td>
                    <td>${u.nombre}</td>
                    <td>${u.correo}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <br><a href="../../index.jsp">Volver al inicio</a>
</body>
</html>
