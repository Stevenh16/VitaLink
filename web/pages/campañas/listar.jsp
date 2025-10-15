<%-- 
    Document   : listar.jsp
    Created on : 14/10/2025, 3:34:35 p. m.
    Author     : Steven
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="modelo.Campaña" %>

<%
    ArrayList<Campaña> campañas = (ArrayList<Campaña>) request.getAttribute("campañas");
    String rol = (String) session.getAttribute("rol");
    if (rol == null) {
        response.sendRedirect(request.getContextPath() + "/pages/usuario/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Lista de Campañas</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 40px;
            background-color: #f9f9f9;
        }
        h1 {
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background: #fff;
            border-radius: 10px;
            overflow: hidden;
        }
        th, td {
            padding: 12px;
            border-bottom: 1px solid #ccc;
            text-align: left;
        }
        th {
            background-color: #007bff;
            color: white;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        .boton {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 8px 14px;
            border-radius: 5px;
            cursor: pointer;
        }
        .boton:hover {
            background-color: #0056b3;
        }
        .acciones {
            display: flex;
            gap: 10px;
        }
        .mensaje {
            color: green;
        }
        .error {
            color: red;
        }
    </style>
</head>
<body>

    <h1>Lista de Campañas</h1>

    <p class="mensaje"><%= request.getAttribute("mensaje") != null ? request.getAttribute("mensaje") : "" %></p>
    <p class="error"><%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %></p>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Título</th>
                <th>Descripción</th>
                <th>Estado</th>
                <th>Fecha Inicio</th>
                <th>Fecha Fin</th>
                <th>Donatario</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <%
                if (campañas != null && !campañas.isEmpty()) {
                    for (Campaña c : campañas) {
            %>
            <tr>
                <td><%= c.getId() %></td>
                <td><%= c.getTitulo() %></td>
                <td><%= c.getDescripcion() %></td>
                <td><%= c.getEstado() %></td>
                <td><%= c.getFechaInicio() %></td>
                <td><%= c.getFechaFin() != null ? c.getFechaFin() : "En curso" %></td>
                <td><%= c.getDonatario() != null ? c.getDonatario().getNombre() : "Sin donatario" %></td>
                <td class="acciones">
                    <form action="${pageContext.request.contextPath}/CampañaServlet" method="get">
                        <input type="hidden" name="action" value="ver">
                        <input type="hidden" name="id" value="<%= c.getId() %>">
                        <button type="submit" class="boton">Ver</button>
                    </form>

                    <% if (rol.equalsIgnoreCase("Donatario") && "ACTIVA".equalsIgnoreCase(c.getEstado())) { %>
                        <form action="<%= request.getContextPath() %>/CampañaServlet" method="get">
                            <input type="hidden" name="action" value="finalizar">
                            <input type="hidden" name="id" value="<%= c.getId() %>">
                            <button type="submit" class="boton">Finalizar</button>
                        </form>
                    <% } %>
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr><td colspan="7" style="text-align:center;">No hay campañas registradas.</td></tr>
            <%
                }
            %>
        </tbody>
    </table>

    <br>
    <a href="<%= request.getContextPath() %>/pages/home.jsp" class="boton">Volver al Inicio</a>

</body>
</html>

