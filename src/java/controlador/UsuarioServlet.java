package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import modelo.Usuario;
import servicios.implementaciones.UsuarioServicioImplementacion;

/**
 * Servlet para manejar operaciones relacionadas con los usuarios.
 * Acciones soportadas:
 * login, register, getUser, getAll, edit, delete, roles, checkEmail, getRoleId
 */
@WebServlet(name = "usuarioServlet", urlPatterns = {"/usuarioServlet"})
public class UsuarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static int id = 0;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        UsuarioServicioImplementacion servicio = new UsuarioServicioImplementacion();

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            if (action == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no especificada");
                return;
            }

            switch (action) {
                case "getUser" ->  {
                    int userId = Integer.parseInt(request.getParameter("id"));
                    Usuario usuario = servicio.obtenerUsuarioId(userId);
                    if (usuario != null) {
                        out.println("<h3>Usuario encontrado:</h3>");
                        out.println("<p>" + usuario + "</p>");
                    } else {
                        out.println("<h3>No se encontró usuario con id " + userId + "</h3>");
                    }
                }

                case "getAll" ->  {
                    ArrayList<Usuario> usuarios = servicio.obtenerTodos();
                    out.println("<h3>Lista de usuarios:</h3>");
                    for (Usuario u : usuarios) {
                        out.println("<p>" + u + "</p>");
                    }
                }

                case "delete" ->  {
                    int userId = Integer.parseInt(request.getParameter("id"));
                    if (servicio.eliminar(userId)) {
                        out.println("<h3>Usuario eliminado correctamente</h3>");
                    } else {
                        out.println("<h3>Error al eliminar usuario</h3>");
                    }
                }

                case "roles" ->  {
                    ArrayList<String> roles = servicio.obtenerTodosLosRoles();
                    out.println("<h3>Roles disponibles:</h3>");
                    for (String rol : roles) {
                        out.println("<p>" + rol + "</p>");
                    }
                }

                case "checkEmail" ->  {
                    String correo = request.getParameter("correo");
                    boolean existe = servicio.existeUsuarioCorreo(correo);
                    out.println("<h3>El correo " + correo + (existe ? " ya está registrado." : " está disponible.") + "</h3>");
                }

                case "getRoleId" ->  {
                    String nombreRol = request.getParameter("rol");
                    int idRol = servicio.obtenerIdRolPorNombre(nombreRol);
                    out.println("<h3>ID del rol '" + nombreRol + "': " + idRol + "</h3>");
                }

                default -> response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida: " + action);
            }

        } catch (Exception ex) {
            Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error procesando la solicitud");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        UsuarioServicioImplementacion servicio = new UsuarioServicioImplementacion();

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            if (action == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no especificada");
                return;
            }

            switch (action) {
                case "login" ->  {
                    String correo = request.getParameter("correo");
                    String contrasenia = request.getParameter("contrasenia");
                    if (servicio.loginValido(correo, contrasenia)) {
                        out.println("<h2>Bienvenido, " + correo + "</h2>");
                    } else {
                        out.println("<h2>Correo o contraseña incorrectos</h2>");
                    }
                }

                case "register" ->  {
                    Usuario usuario = new Usuario(
                            id++,
                            request.getParameter("rol"),
                            request.getParameter("nombre"),
                            request.getParameter("correo"),
                            request.getParameter("contrasenia")
                    );
                    if (servicio.crearUsuario(usuario)) {
                        out.println("<h2>Usuario registrado correctamente:</h2>");
                        out.println("<p>" + usuario + "</p>");
                    } else {
                        out.println("<h2>No se pudo registrar el usuario. Verifica los datos.</h2>");
                    }
                }

                case "edit" ->  {
                    int userId = Integer.parseInt(request.getParameter("id"));
                    Usuario usuario = new Usuario(
                            userId,
                            request.getParameter("rol"),
                            request.getParameter("nombre"),
                            request.getParameter("correo"),
                            request.getParameter("contrasenia")
                    );
                    if (servicio.editarUsuario(userId, usuario)) {
                        out.println("<h3>Usuario actualizado correctamente</h3>");
                    } else {
                        out.println("<h3>Error al actualizar usuario</h3>");
                    }
                }

                default -> response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida: " + action);
            }

        } catch (Exception ex) {
            Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error procesando la solicitud");
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet para gestionar operaciones CRUD y autenticación de usuarios.";
    }
}
