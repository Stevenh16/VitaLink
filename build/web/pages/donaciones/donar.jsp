<%-- 
    Document   : donar
    Created on : 14/10/2025, 11:34:48 p. m.
    Author     : Steven
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.*, jakarta.servlet.*" %>
<%@ page import="java.time.LocalDateTime" %>

<%
    // Recuperar id de la campaña desde la URL
    String idCampañaParam = request.getParameter("idCampaña");
    if (idCampañaParam == null) {
        idCampañaParam = "0";
    }

    // Verificar si hay usuario logueado
    HttpSession sesion = request.getSession(false);
    Integer idUsuario = (sesion != null) ? (Integer) sesion.getAttribute("id") : null;
%>

<!DOCTYPE html>
<html>
<head>
    <title>Donar a campaña</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap.min.css">
</head>
<body class="container mt-5">
    <h2 class="mb-4">Donar a la Campaña</h2>

    <%
        if (idUsuario == null) {
    %>
        <div class="alert alert-danger">
            Debes iniciar sesión para realizar una donación.
        </div>
        <a href="${pageContext.request.contextPath}/pages/usuario/login.jsp" class="btn btn-primary">Iniciar sesión</a>
    <%
        } else {
    %>

    <form action="${pageContext.request.contextPath}/DonacionServlet" method="post">
        <input type="hidden" name="action" value="crear">
        <input type="hidden" name="idCampaña" value="<%= idCampañaParam %>">
        <input type="hidden" name="idDonante" value="<%= idUsuario %>">

        <div class="mb-3">
            <label for="tipo" class="form-label">Tipo de Donación</label>
            <select id="tipo" name="tipo" class="form-select" required>
                <option value="ECONOMICA">Económica</option>
                <option value="MEDICAMENTOS">Medicamentos</option>
                <option value="SANGRE">Sangre</option>
            </select>
        </div>

        <div class="mb-3">
            <label for="cantidad" class="form-label">Cantidad</label>
            <input type="number" id="cantidad" name="cantidad" class="form-control" required min="1">
        </div>

        <button type="submit" class="btn btn-success">Donar</button>
        <a href="${pageContext.request.contextPath}/pages/home.jsp" class="btn btn-secondary">Cancelar</a>
    </form>

    <%
        }
    %>
</body>
</html>

