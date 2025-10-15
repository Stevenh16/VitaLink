<%-- 
    Document   : register
    Created on : 13/10/2025, 9:13:17 p. m.
    Author     : Steven
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registrarse | VitaLink</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #56ccf2, #2f80ed);
            color: white;
            font-family: 'Poppins', sans-serif;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .register-box {
            background: rgba(255, 255, 255, 0.1);
            backdrop-filter: blur(12px);
            border-radius: 20px;
            padding: 40px;
            width: 400px;
            box-shadow: 0 8px 20px rgba(0,0,0,0.2);
            text-align: center;
        }
        .form-control {
            border-radius: 25px;
            border: none;
            padding: 10px 20px;
        }
        .btn-custom {
            border-radius: 25px;
            background-color: white;
            color: #2f80ed;
            font-weight: 500;
            transition: 0.3s;
        }
        .btn-custom:hover {
            background-color: #2f80ed;
            color: white;
        }
        a {
            color: white;
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="register-box">
    <h2 class="mb-4">Crear cuenta</h2>
    <form action="${pageContext.request.contextPath}/usuarioServlet" method="post"/>
        <input type="hidden" name="action" value="register">
        <div class="mb-3">
            <input type="text" name="nombre" class="form-control" placeholder="Nombre completo" required>
        </div
        <div class="mb-3">
            <input type="email" name="correo" class="form-control" placeholder="Correo electrónico" required>
        </div>
        <div class="mb-3">
            <input type="password" name="contrasena" class="form-control" placeholder="Contraseña" required>
        </div>
        <div class="mb-3">
            <select name="rol" class="form-select rounded-pill" required>
                <option value="">Selecciona tu rol</option>
                <option value="DONANTE">Donante</option>
                <option value="DONATARIO">Donatario</option>
                <option value="GERENTESALUD">Gerente Salud</option>
            </select>
        </div>
        <button type="submit" class="btn btn-custom w-100">Registrarse</button>
    </form>
    <p class="mt-3">¿Ya tienes cuenta? <a href="login.jsp">Inicia sesión</a></p>
    <p class="mt-2"><a href="../index.jsp">Volver al inicio</a></p>
</div>
</body>
</html>

