<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inicio de Sesión</title>
    <link rel="stylesheet" href="css/login.css">
   
</head>
<body>
<header>
    
</header>
<div class="login-container">
    <form action="LoginServlet" method="post" class="form-container">
        <h2>Inicio de Sesión</h2>
        <div class="form-group">
            <label for="email">Correo Electrónico:</label>
            <input type="email" id="email" name="email" required>
        </div>
        <div class="form-group">
            <label for="password">Contraseña:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <input type="submit" value="Iniciar Sesión">
       
    </form>
</div>
    <script> 
        <c:if test="${param.error == 1}">  
        alert('Correo electrónico o contraseña incorrectos'); 
        </c:if>
       </script>
</body>
</html>
