<%-- 
    Document   : registro
    Created on : 27 nov 2024, 22:43:07
    Author     : l_car
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>

<sql:setDataSource var="dbsource" driver="com.microsoft.sqlserver.jdbc.SQLServerDriver" url="jdbc:sqlserver://KITO\\SQLEXPRESS:1433;databaseName=blogProyecto;user=LuisC;password=pollo"/>

<sql:query dataSource="${dbsource}" var="estadoList">
    SELECT id, nombre FROM dbo.Estado;
</sql:query>

<c:if test="${not empty param.estado}">
    <sql:query dataSource="${dbsource}" var="municipioList">
        SELECT id, nombre FROM dbo.Municipio WHERE estado_id = ${param.estado};
    </sql:query>
</c:if>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro de usuario</title>
    <link rel="stylesheet" href="css/registro.css">
    <script> function loadMunicipios() 
        { var estadoId = document.getElementById("estado").value; if 
            (estadoId) { window.location.href = "registro.jsp?estado=" + estadoId; } } 
        function validateForm() { var municipioId = document.getElementById("municipio").value; if
            (municipioId === "") { alert("Seleccione un municipio."); return false; } return true; } </script>
</head>
<body>
    <div class="container">
        <h2>Registro de usuario</h2>
        <form id="RegistroU" action="registro" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="nombreC">Nombre:</label>
                <input type="text" id="nombreCompleto" name="nombreCompleto" required>
            </div>
            <div class="form-group">
                <label for="email">Correo Electrónico:</label>
                <input type="email" id="correo" name="correo" required>
            </div>
            <div class="form-group">
                <label for="password">Contraseña:</label>
                <input type="password" id="contrasena" name="contrasena" required>
            </div>
            <div class="form-group">
                <label for="celu">Teléfono:</label>
                <input type="tel" id="telefono" name="telefono" required>
            </div>
            <div class="form-group">
                <label for="PFP"> Avatar:</label>
                <input type="file" id="avatar" name="avatar" accept="image/*">
            </div>
            <div class="form-group">
                <label for="ciudad">Ciudad:</label>
                <input type="text" id="ciudad" name="ciudad" required>
            </div>
            <div class="form-group"> 
                <label for="estado">Estado:</label> 
                <select id="estado" name="estado" required onchange="loadMunicipios()">
                    <option value="">Seleccione un estado</option> 
                    <c:forEach var="estado" items="${estadoList.rows}"> 
                        <option value="${estado.id}" ${param.estado == estado.id ? 'selected' : ''}>${estado.nombre}</option>
                    </c:forEach> 
                </select> 
            </div>
            <div class="form-group"> 
                <label for="municipio">Municipio:</label>
                <select id="municipio" name="municipio_id" required>
                    <option value="">Seleccione un municipio</option> 
                    <c:if test="${not empty param.estado}"> 
                        <c:forEach var="municipio" items="${municipioList.rows}">
                            <option value="${municipio.id}">${municipio.nombre}</option>
                        </c:forEach> 
                    </c:if> 
                </select>
            </div>
            <div class="form-group">
                <label for="Nacimiento">Fecha de Nacimiento:</label>
                <input type="date" id="fechaNacimiento" name="fechaNacimiento" required>
            </div>
            <div class="form-group">
                <label for="genero">Género:</label>
                <select id="genero" name="genero" required>
                    <option value="masculino">Masculino</option>
                    <option value="femenino">Femenino</option>
                </select>
            </div>
            <input type="submit" value="Registrarse">
            <button class="register-button" type="button" onclick="window.location.href='login.jsp'">Login</button>
        </form>

        <c:choose>
            <c:when test="${param.status == 'success'}">
                <p>Registro exitoso</p>
                
            </c:when>
            <c:when test="${param.status == 'error'}">
                <p>Ocurrió un error en el registro</p>
            </c:when>
        </c:choose>
    </div>
</body>
</html>
