<%-- 
    Document   : index.jsp
    Created on : 13/10/2025, 9:10:26 p. m.
    Author     : Steven
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>VitaLink</title>
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
        .container-box {
            background: rgba(255, 255, 255, 0.1);
            backdrop-filter: blur(12px);
            border-radius: 20px;
            padding: 40px;
            text-align: center;
            box-shadow: 0 8px 20px rgba(0,0,0,0.2);
        }
        .title {
            font-size: 3em;
            font-weight: bold;
            letter-spacing: 2px;
        }
        .btn-custom {
            margin: 10px;
            padding: 10px 30px;
            border-radius: 25px;
            font-weight: 500;
            transition: 0.3s;
        }
        .btn-login {
            background-color: white;
            color: #2f80ed;
        }
        .btn-register {
            background-color: transparent;
            border: 2px solid white;
            color: white;
        }
        .btn-login:hover {
            background-color: #2f80ed;
            color: white;
        }
        .btn-register:hover {
            background-color: white;
            color: #2f80ed;
        }
    </style>
</head>
<body>
<div class="container-box">
    <h1 class="title mb-4">VitaLink</h1>
    <p class="mb-4">Conecta esperanza, comparte vida 💙</p>
    <a href="pages/login.jsp" class="btn btn-custom btn-login">Iniciar Sesión</a>
    <a href="pages/register.jsp" class="btn btn-custom btn-register">Registrarse</a>
</div>
</body>
</html>