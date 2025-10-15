<%-- 
    Document   : login
    Created on : 13/10/2025, 9:12:20 p. m.
    Author     : Steven
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Iniciar Sesión | VitaLink</title>
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
        .login-box {
            background: rgba(255, 255, 255, 0.1);
            border-radius: 20px;
            padding: 40px;
            width: 350px;
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
<div class="login-box">
    <h2 class="mb-4">Iniciar Sesión</h2>
    <form action="${pageContext.request.contextPath}/usuarioServlet" method="post">
        <input type="hidden" name="action" value="login">
        <div class="mb-3">
            <input type="text" name="correo" class="form-control" placeholder="Correo electrónico" required>
        </div>
        <div class="mb-3">
            <input type="password" name="contrasenia" class="form-control" placeholder="Contraseña" required>
        </div>
        <button type="submit" class="btn btn-custom w-100">Entrar</button>
    </form>
    <p class="mt-3">¿No tienes cuenta? <a href="register.jsp">Regístrate</a></p>
    <p class="mt-2"><a href="../index.jsp">Volver al inicio</a></p>
</div>
</body>
</html>

