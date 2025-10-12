/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Usuario;
import repositorio.UsuarioRepositorio;

/**
 *
 * @author Steven
 */
@WebServlet(name = "usuarioServlet", urlPatterns = {"/usuarioServlet"})
public class UsuarioServlet extends HttpServlet {
    public static int id = 0;
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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UsuarioServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UsuarioServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action"); // "login" o "register"

        if ("login".equalsIgnoreCase(action)) {
            login(request, response);
        } else if ("register".equalsIgnoreCase(action)) {
            register(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
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

    private void login(HttpServletRequest request, HttpServletResponse response) {
        String correo = request.getParameter("correo");
        String contrasenia = request.getParameter("contrasenia");
        UsuarioRepositorio repositorio = new UsuarioRepositorio();
        
        try{
            if(repositorio.loginValido(correo, contrasenia)){
                response.getWriter().println("<h2>Bienvenido, " + correo + "</h2>");
            }else{
                response.getWriter().println("<h2>Correo o contraseña incorrectos</h2>");
            } 
        } catch (IOException ex) {
                Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response) {
        Usuario usuario = new Usuario(
                id++,
                request.getParameter("rol"),
                request.getParameter("nombre"),
                request.getParameter("correo"),
                request.getParameter("contrasenia")
        );
        UsuarioRepositorio repositorio = new UsuarioRepositorio();
        
        try{
            if(repositorio.crearUsuario(usuario)){
                response.getWriter().println("<h2>Usuario registrado: " + usuario + "</h2>");
            }else{
                response.getWriter().println("<h2>Contraseña diferentes o correo ya registrado</h2>");
            }
        } catch (IOException ex) {
                Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
