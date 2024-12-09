/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accesoDatos.DAO;


import Modelos.Post;
import Modelos.Usuarios;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;

import java.sql.SQLException;
public class PostDAO {
    private Connection connection;

    public PostDAO(Connection connection) {
        this.connection = connection;
    }

    // Obtener todas las publicaciones
    public List<Post> getPosts() throws SQLException {
        String sql = "SELECT p.id, p.titulo, p.contenido, p.usuario_id, p.anclado, p.fechaHoraCreacion, p.fechaHoraEdicion, u.nombreCompleto " +
                     "FROM Post p JOIN Usuario u ON p.usuario_id = u.id " +
                     "ORDER BY p.id DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            List<Post> posts = new ArrayList<>();
            while (rs.next()) {
                Post post = new Post(
                    rs.getInt("id"),
                    rs.getDate("fechaHoraCreacion"),
                    rs.getString("titulo"),
                    rs.getString("contenido"),
                    rs.getDate("fechaHoraEdicion"),
                    rs.getInt("usuario_id"),
                    rs.getBoolean("anclado"),
                    rs.getString("nombreCompleto")
                );
                posts.add(post);
            }
            return posts;
        }
    }

    // Obtener todos los usuarios
    public List<Usuarios> getUsers() throws SQLException {
        String sql = "SELECT id, nombreCompleto FROM Usuario";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            List<Usuarios> users = new ArrayList<>();
            while (rs.next()) {
                Usuarios user = new Usuarios(rs.getInt("id"), rs.getString("nombreCompleto"));
                users.add(user);
            }
            return users;
        }
    }

    // Insertar una nueva publicación
    public void insertPost(String title, String content, int userId, boolean pinned) throws SQLException {
        String sql = "INSERT INTO dbo.Post (fechaHoraCreacion, titulo, contenido, fechahoraedicion, usuario_id, anclado) VALUES (GETDATE(), ?, ?, GETDATE(), ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, content);
            ps.setInt(3, userId);
            ps.setBoolean(4, pinned);
            ps.executeUpdate();
        }
    }

    // Eliminar una publicación
    public void deletePost(int postId) throws SQLException {
        String sql = "DELETE FROM post WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, postId);
            ps.executeUpdate();
        }
    }
}
