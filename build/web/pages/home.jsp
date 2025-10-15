<%-- 
    Document   : home
    Created on : 14/10/2025, 10:45:08 a. m.
    Author     : Steven
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%
    // Verificar si el usuario ha iniciado sesión
    String username = (String) session.getAttribute("correo");
    String rol = (String) session.getAttribute("rol");

    if (username == null || rol == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Inicio - VitaLink</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f6f9fc;
            margin: 0;
            padding: 0;
        }

        header {
            background-color: #003366;
            color: white;
            padding: 15px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        header h1 {
            margin: 0;
        }

        .logout {
            color: white;
            text-decoration: none;
            background-color: #e63946;
            padding: 8px 16px;
            border-radius: 5px;
        }

        .logout:hover {
            background-color: #c72b38;
        }

        main {
            max-width: 600px;
            margin: 50px auto;
            text-align: center;
            background: white;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0px 4px 8px rgba(0,0,0,0.1);
        }

        h2 {
            color: #003366;
        }

        .boton {
            display: inline-block;
            margin: 10px;
            padding: 12px 20px;
            background-color: #007bff;
            color: white;
            border-radius: 8px;
            text-decoration: none;
            font-weight: bold;
        }

        .boton:hover {
            background-color: #0056b3;
        }

        footer {
            text-align: center;
            padding: 15px;
            margin-top: 50px;
            color: #666;
        }
    </style>
</head>
<body>
    <header>
        <h1>Bienvenido a VitaLink</h1>
        <a href="LogoutServlet" class="logout">Cerrar sesión</a>
    </header>

    <main>
        <h2>Hola, <%= username %></h2>
        <p>Tu rol es: <strong><%= rol %></strong></p>

        <hr style="margin: 20px 0;">

        <% if ("donatario".equalsIgnoreCase(rol)) { %>
            <h3>Funciones del Donatario</h3>
            <form action="${pageContext.request.contextPath}/CampañaServlet" method="get">
                <input type="hidden" name="action" value="formCrear">
                <button type="submit" class="boton">Crear Campaña</button>
            </form>

            <form action="${pageContext.request.contextPath}/CampañaServlet" method="get">
                <input type="hidden" name="action" value="formFinalizar">
                <button type="submit" class="boton">Finalizar Campaña</button>
            </form>
        <% } else if ("donante".equalsIgnoreCase(rol)) { %>
            <h3>Funciones del Donante</h3>
             <a href="${pageContext.request.contextPath}/pages/donaciones/donar.jsp?id=${campaña.id}" 
                class="boton">
                Donar
             </a>
        <% } else { %>
            <p style="color: red;">Rol desconocido. Contacta al administrador.</p>
        <% } %>
         <form action="${pageContext.request.contextPath}/CampañaServlet" method="get">
            <input type="hidden" name="action" value="listar">
            <button type="submit" class="boton">Listar Campañas</button>
        </form>
    </main>

    <footer>
        <p>© 2025 VitaLink - Todos los derechos reservados</p>
    </footer>
</body>
</html>

