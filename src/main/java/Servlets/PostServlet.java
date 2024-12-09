package Servlets;
import Modelos.Post;
import Modelos.Usuarios;
import accesoDatos.DAO.PostDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


@WebServlet(name = "PostServlet", urlPatterns = {"/PostServlet"})

public class PostServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Método procesador de solicitudes.
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
        try {
            
            String dbURL = "jdbc:sqlserver://KITO\\SQLEXPRESS:1433;databaseName=blogProyecto";
            String dbUser = "LuisC";
            String dbPassword = "pollo";
            
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

    try (Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPassword)) {
        PostDAO postDAO = new PostDAO(connection);

        List<Post> posts = postDAO.getPosts();
        List<Usuarios> users = postDAO.getUsers();
        
        // Mensajes de depuración
        System.out.println("Número de publicaciones recuperadas: " + posts.size()); System.out.println("Número de usuarios recuperados: " + users.size()); for (Usuarios user : users) { System.out.println("Usuario: " + user.getNombreCompleto()); }
        request.setAttribute("postList", posts);
        request.setAttribute("userList", users);

        
        request.getRequestDispatcher("inicio.jsp").forward(request, response);
    }
    } catch (SQLException e) {
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en la base de datos");
    }   catch (ClassNotFoundException ex) {
            Logger.getLogger(PostServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
}


    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            String dbURL = "jdbc:sqlserver://KITO\\SQLEXPRESS:1433;databaseName=blogProyecto";
            String dbUser = "LuisC";
            String dbPassword = "pollo";
Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            try (Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPassword)) {
                PostDAO postDAO = new PostDAO(connection);

                if ("insert".equals(action)) {
                    insertPost(request, postDAO);
                } else if ("delete".equals(action)) {
                    deletePost(request, postDAO);
                }
                response.sendRedirect("PostServlet"); // Redirigir para actualizar la lista
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en la base de datos");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PostServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void insertPost(HttpServletRequest request, PostDAO postDAO) throws SQLException {
        String title = request.getParameter("postTitle");
        String content = request.getParameter("postContent");
        int userId = Integer.parseInt(request.getParameter("usuario_id"));
        boolean pinned = request.getParameter("anclado") != null;

        // Insertar nueva publicación usando el DAO
        postDAO.insertPost(title, content, userId, pinned);
    }

    private void deletePost(HttpServletRequest request, PostDAO postDAO) throws SQLException {
        int postId = Integer.parseInt(request.getParameter("deletePostId"));

        // Eliminar publicación usando el DAO
        postDAO.deletePost(postId);
    }
}
