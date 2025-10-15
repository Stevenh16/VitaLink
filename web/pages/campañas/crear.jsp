<%-- 
    Document   : crear
    Created on : 14/10/2025, 1:34:56 p. m.
    Author     : Steven
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%
    // Verificar si el usuario ha iniciado sesión
    String username = (String) session.getAttribute("correo");
    String rol = (String) session.getAttribute("rol");
    int id = (int) session.getAttribute("id"); 

    if (username == null || rol == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Crear Campaña</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2>Crear Nueva Campaña</h2>
    <form action="${pageContext.request.contextPath}/CampañaServlet" method="post">
        <input type="hidden" name="action" value="crear">
        <input type="hidden" name="id" value=<%= id %>>
        <div class="mb-3">
            <label for="titulo" class="form-label">Título</label>
            <input type="text" id="titulo" name="titulo" class="form-control" required>
        </div>
        <div class="mb-3">
            <label for="descripcion" class="form-label">Descripción</label>
            <textarea id="descripcion" name="descripcion" class="form-control" required></textarea>
        </div>
        <button type="submit" class="btn btn-primary">Crear</button>
        <a href="../../home.jsp" class="btn btn-secondary">Volver</a>
    </form>
</div>
</body>
</html>

