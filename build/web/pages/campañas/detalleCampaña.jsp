<%-- 
    Document   : detalleCampaña
    Created on : 14/10/2025, 11:35:42 p. m.
    Author     : Steven
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Detalle de Campaña</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
</head>
<body class="bg-light">

<div class="container mt-5">
    <c:choose>
        <c:when test="${not empty campaña}">
            <div class="card shadow">
                <div class="card-header bg-primary text-white">
                    <h3>${campaña.titulo}</h3>
                </div>
                <div class="card-body">
                    <p><strong>ID:</strong> ${campaña.id}</p>
                    <p><strong>Descripción:</strong> ${campaña.descripcion}</p>
                    <p><strong>Donatario:</strong> ${campaña.donatario.nombre}</p>
                    <p><strong>Fecha inicio:</strong> ${campaña.fechaInicio}</p>
                    <p><strong>Fecha fin:</strong> ${campaña.fechaFin}</p>

                    <div class="mt-4 text-center">
                        <a href="${pageContext.request.contextPath}/pages/campañas/home.jsp" 
                           class="btn btn-secondary">
                            Volver---------
                        </a>
                          
                        <a href="${pageContext.request.contextPath}/pages/donaciones/donar.jsp?idCampaña=${campaña.id}" 
                           class="btn btn-success">
                            Donar a esta campaña
                        </a>
                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="alert alert-warning text-center">
                No se encontró la campaña solicitada.
            </div>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>

