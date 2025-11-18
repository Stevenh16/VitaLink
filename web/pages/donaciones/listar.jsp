<%-- 
    Document   : listar
    Created on : 15/10/2025, 11:44:07 a. m.
    Author     : Steven
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Listado de Donaciones</title>
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <c:choose>
        <c:when test="${not empty donaciones}">
            <table class="table table-striped table-hover shadow-sm">
                <thead class="table-primary text-center">
                    <tr>
                        <th>ID</th>
                        <th>Tipo</th>
                        <th>Cantidad</th>
                        <th>Fecha</th>
                        <th>Campaña</th>
                        <th>Donante</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="donacion" items="${donaciones}">
                        <tr>
                            <td class="text-center">${donacion.id}</td>
                            <td>${donacion.tipo}</td>
                            <td>${donacion.cantidad}</td>
                            <td>
                                <fmt:formatDate value="${donacion.fecha}" pattern="yyyy-MM-dd HH:mm" />
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/CampañaServlet?action=ver&id=${donacion.campaña.id}"
                                   class="text-decoration-none">
                                    ${donacion.campaña.titulo}
                                </a>
                            </td>
                            <td>${donacion.donante.nombre}</td>
                            <td class="text-center">
                                <a href="${pageContext.request.contextPath}/DonacionServlet?action=ver&id=${donacion.id}" 
                                   class="btn btn-info btn-sm">Ver</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="alert alert-warning text-center shadow-sm">
                No hay donaciones registradas.
            </div>
        </c:otherwise>
    </c:choose>
</div>

<script src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js"></script>
</body>
</html>

