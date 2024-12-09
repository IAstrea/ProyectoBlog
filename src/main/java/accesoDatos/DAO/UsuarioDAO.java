/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accesoDatos.DAO;
import Conection.Conexion;
import Modelos.Usuarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UsuarioDAO {

    private Connection connection;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }

  

    // Método para agregar un usuario
    public boolean agregarUsuario(Usuarios usuario) {
        String sql = "INSERT INTO Usuario (nombreCompleto, correo, contrasena, telefono, avatar, ciudad, fechaNacimiento, genero, municipio_Id, adm) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombreCompleto());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getContrasena());
            stmt.setString(4, usuario.getTelefono());
          if (usuario.getAvatar() != null) {
                stmt.setBytes(5, usuario.getAvatar());  // Usamos setBytes para almacenar el archivo binario
            } else {
                stmt.setNull(5, java.sql.Types.BINARY);  // En caso de no haber avatar
            }
            
            stmt.setString(6, usuario.getCiudad());
            stmt.setString(7, usuario.getFechaNacimiento());
            stmt.setString(8, usuario.getGenero());
            stmt.setInt(9, usuario.getMunicipioId());
            stmt.setBoolean(10, usuario.isAdm());
            int rowsAffected = stmt.executeUpdate(); // Ejecuta la sentencia
            return rowsAffected > 0; // Si se insertó al menos una fila, es un éxito
        } catch (SQLException e) {
          
            e.printStackTrace();
              return false;
          
        }
    }

    // Método para obtener un usuario por ID
    public Usuarios obtenerUsuarioPorId(int id) {
        String sql = "SELECT * FROM Usuario WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuarios(
                        rs.getInt("id"),
                        rs.getString("nombreCompleto"),
                        rs.getString("correo"),
                        rs.getString("contrasenia"),
                        rs.getString("telefono"),
                        rs.getBytes("avatar"),
                        rs.getString("ciudad"),
                        rs.getString("fechaNacimiento"),
                            rs.getString("genero"),
                        rs.getInt("municipioId"),
                        rs.getBoolean("adm")
                    );
                }
            }
        } catch (SQLException e) {
        }
        return null;
    }
    


    public Usuarios autenticarUsuario(String email, String password) throws SQLException {
        String sql = "SELECT id, nombreCompleto, correo FROM Usuario WHERE correo = ? AND contrasena = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuarios(
                            rs.getInt("id"),
                            rs.getString("nombreCompleto"),
                            rs.getString("correo")
                    );
                } else {
                    return null;
                }
            }
        }
    }

    // Otros métodos...


    // Método para obtener todos los usuarios
    public List<Usuarios> obtenerTodosLosUsuarios() {
        List<Usuarios> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Usuarios usuario = new Usuarios(
                    rs.getInt("id"),
                    rs.getString("nombreCompleto"),
                    rs.getString("correo"),
                    rs.getString("contrasenia"),
                    rs.getString("telefono"),
                    rs.getBytes("avatar"),
                    rs.getString("ciudad"),
                    rs.getString("fechaNacimiento"),
                        rs.getString("genero"),
                    rs.getInt("municipioId"),
                    rs.getBoolean("adm")
                );
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
        }
        return usuarios;
    }

    // Método para actualizar un usuario
    public boolean actualizarUsuario(Usuarios usuario) {
        String sql = "UPDATE Usuario SET nombreCompleto = ?, correo = ?, contrasenia = ?, telefono = ?, avatar = ?, ciudad = ?, fechaNacimiento = ?, genero = ?, municipioId = ?, adm = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombreCompleto());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getContrasena());
            stmt.setString(4, usuario.getTelefono());
            stmt.setBytes(5, usuario.getAvatar());
            stmt.setString(6, usuario.getCiudad());
            stmt.setString(7,usuario.getFechaNacimiento());
            stmt.setString(8, usuario.getGenero());
            stmt.setInt(9, usuario.getMunicipioId());
            stmt.setBoolean(10, usuario.isAdm());
            stmt.setInt(11, usuario.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    // Método para eliminar un usuario
    public boolean eliminarUsuario(int id) {
        String sql = "DELETE FROM Usuario WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            
            return false;
        }
    }
}
