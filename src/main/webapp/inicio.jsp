<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Publicaciones</title>
    <link rel="stylesheet" href="css/estiloInicio.css">
</head>
<body>
<header>
</header>
<section id="posts">
    <h2>Publicaciones Recientes</h2>
    <c:forEach var="post" items="${postList}">
        <article class="post">
            <h3>${post.titulo}</h3>
            <p>${post.contenido}</p>
            <p><em>Autor: ${post.nombreCompleto}</em></p>
            <p>Creado: <fmt:formatDate value="${post.fechaHoraCreacion}" pattern="dd/MM/yyyy"/></p>
            <p>Editado: <fmt:formatDate value="${post.fechaHoraEdicion}" pattern="dd/MM/yyyy"/></p>
            <form method="post" action="PostServlet">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="deletePostId" value="${post.id}">
                <button type="submit" class="delete-btn" aria-label="Eliminar publicación">Eliminar</button>
            </form>
        </article>
    </c:forEach>
</section>
<button class="add-post-btn" onclick="openForm()">Añadir Publicación</button>
<div class="form-popup" id="myForm">
    <form id="newPostForm" class="form-container" action="PostServlet" method="post">
        <input type="hidden" name="action" value="insert">
        <h2>Añadir Nueva Publicación</h2>
        <label for="postTitle">Título:</label>
        <input type="text" id="postTitle" name="postTitle" required>
        <label for="postContent">Contenido:</label>
        <textarea id="postContent" name="postContent" required></textarea>
        <label for="usuario_id">Usuario:</label>
        <select id="usuario_id" name="usuario_id" required>
            <c:forEach var="user" items="${userList}">
                <option value="${user.id}">${user.nombreCompleto}</option>
            </c:forEach>
        </select>
        <label for="anclado">Anclado:</label>
        <input type="checkbox" id="anclado" name="anclado">
        <button type="submit">Añadir Publicación</button>
        <button type="button" class="cancel" onclick="closeForm()">Cerrar</button>
    </form>
</div>
<script>
    function openForm() {
        document.getElementById("myForm").style.display = "block";
    }

    function closeForm() {
        document.getElementById("myForm").style.display = "none";
    }
</script>
</body>
</html>
