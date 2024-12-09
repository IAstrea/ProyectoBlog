/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import Modelos.Usuarios;
import accesoDatos.DAO.UsuarioDAO;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import java.io.InputStream;

/**
 *
 * @author l_car
 */
@WebServlet(name = "registro", urlPatterns = {"/registro"})
@MultipartConfig
public class RegistroServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
        //processRequest(request, response);
        String nombreCompleto = request.getParameter("nombreCompleto");
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");
        String telefono = request.getParameter("telefono");
        String avatar = request.getParameter("avatar"); 
        String ciudad = request.getParameter("ciudad");
        String fechaNacimiento = request.getParameter("fechaNacimiento");
        String genero = request.getParameter("genero");
        String municipioIdStr = request.getParameter("municipio_id");
     
       
        int municipioId;
        try {
            municipioId = Integer.parseInt(municipioIdStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("registro.jsp?status=error&message=invalid%20municipio_id");
            return;
        }
           if (municipioIdStr == null || municipioIdStr.trim().isEmpty()) {
            response.sendRedirect("registro.jsp?status=error&message=municipio_id%20is%20required");
            return;
        }
          // Obtener el archivo avatar
        Part avatarPart = request.getPart("avatar");
        byte[] avatarBytes = null;

        if (avatarPart != null && avatarPart.getSize() > 0) {
            try (InputStream avatarInputStream = avatarPart.getInputStream()) {
                avatarBytes = avatarInputStream.readAllBytes(); // Convertir el archivo a un arreglo de bytes
            }
        }


       
       
           Usuarios usuario = new Usuarios(0, nombreCompleto, correo, contrasena, telefono,avatarBytes, ciudad, fechaNacimiento, genero, municipioId, false);
        
           try {
            // Ajusta la URL de conexión, usuario y contraseña según tu configuración
            String dbURL = "jdbc:sqlserver://KITO\\SQLEXPRESS:1433;databaseName=blogProyecto";
            String dbUser = "LuisC";
            String dbPassword = "pollo";

            try (Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPassword)) {
                UsuarioDAO usuarioDAO = new UsuarioDAO(connection);
                
                if (usuarioDAO.agregarUsuario(usuario)) {
                    response.sendRedirect("registro.jsp?status=success");
                } else {
                    response.sendRedirect("registro.jsp?status=error");
                }
            }
        } catch (SQLException e) {
            response.sendRedirect("registro.jsp?status=error"+e.getMessage());
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

}