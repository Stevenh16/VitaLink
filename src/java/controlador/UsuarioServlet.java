package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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

        try {
            if (action == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no especificada");
                return;
            }

            switch (action) {
                case "getUser" -> {
                    int userId = Integer.parseInt(request.getParameter("id"));
                    Usuario usuario = servicio.obtenerUsuarioId(userId);
                    request.setAttribute("usuario", usuario);
                    request.getRequestDispatcher("/pages/usuario/usuario-detalle.jsp").forward(request, response);
                }

                case "getAll" -> {
                    ArrayList<Usuario> usuarios = servicio.obtenerTodos();
                    request.setAttribute("usuarios", usuarios);
                    request.getRequestDispatcher("/pages/usuario/usuario-lista.jsp").forward(request, response);
                }

                case "delete" -> {
                    int userId = Integer.parseInt(request.getParameter("id"));
                    boolean eliminado = servicio.eliminar(userId);
                    request.setAttribute("resultado", eliminado ? 
                            "Usuario eliminado correctamente" : "Error al eliminar usuario");
                    request.getRequestDispatcher("/pages/usuario/usuario-resultado.jsp").forward(request, response);
                }

                case "roles" -> {
                    ArrayList<String> roles = servicio.obtenerTodosLosRoles();
                    request.setAttribute("roles", roles);
                    request.getRequestDispatcher("/pages/usuario/usuario-roles.jsp").forward(request, response);
                }

                case "checkEmail" -> {
                    String correo = request.getParameter("correo");
                    boolean existe = servicio.existeUsuarioCorreo(correo);
                    request.setAttribute("mensaje", "El correo " + correo +
                            (existe ? " ya está registrado." : " está disponible."));
                    request.getRequestDispatcher("/pages/usuario/usuario-resultado.jsp").forward(request, response);
                }

                case "getRoleId" -> {
                    String nombreRol = request.getParameter("rol");
                    int idRol = servicio.obtenerIdRolPorNombre(nombreRol);
                    request.setAttribute("rolId", idRol);
                    request.getRequestDispatcher("/pages/usuario/usuario-rol-id.jsp").forward(request, response);
                }

                default -> response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida: " + action);
            }

        } catch (ServletException | IOException | NumberFormatException ex) {
            Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error procesando la solicitud");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        UsuarioServicioImplementacion servicio = new UsuarioServicioImplementacion();

        try {
            if (action == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no especificada");
                return;
            }

            switch (action) {
                case "login" -> {
                    String correo = request.getParameter("correo");
                    String contrasenia = request.getParameter("contrasenia");
                    Usuario usuario = servicio.loginValido(correo, contrasenia);
                    if (usuario!=null) {
                        HttpSession session = request.getSession();
                        session.setAttribute("id", usuario.getId());
                        session.setAttribute("correo", usuario.getCorreo());
                        session.setAttribute("rol", usuario.getRol());
                        request.getRequestDispatcher("/pages/home.jsp").forward(request, response);
                    } else {
                        request.setAttribute("mensaje", "Correo o contraseña incorrectos");
                        request.getRequestDispatcher("/pages/usuario/usuario-error.jsp").forward(request, response);
                    }
                }

                case "register" -> {
                    Usuario usuario = new Usuario(
                            id++,
                            request.getParameter("rol"),
                            request.getParameter("nombre"),
                            request.getParameter("correo"),
                            request.getParameter("contrasena")
                    );
                    boolean creado = servicio.crearUsuario(usuario);

                    request.setAttribute("mensaje", creado ?
                            "Usuario registrado correctamente" :
                            "No se pudo registrar el usuario. Verifica los datos.");
                    request.setAttribute("usuario", usuario);
                    request.getRequestDispatcher("/pages/usuario/usuario-resultado.jsp").forward(request, response);
                }

                case "edit" -> {
                    int userId = Integer.parseInt(request.getParameter("id"));
                    Usuario usuario = new Usuario(
                            userId,
                            request.getParameter("rol"),
                            request.getParameter("nombre"),
                            request.getParameter("correo"),
                            request.getParameter("contrasenia")
                    );
                    boolean actualizado = servicio.editarUsuario(userId, usuario);

                    request.setAttribute("mensaje", actualizado ?
                            "Usuario actualizado correctamente" :
                            "Error al actualizar usuario");
                    request.setAttribute("usuario", usuario);
                    request.getRequestDispatcher("/pages/usuario/usuario-resultado.jsp").forward(request, response);
                }

                default -> response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida: " + action);
            }

        } catch (ServletException | IOException | NumberFormatException ex) {
            Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error procesando la solicitud");
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet para gestionar operaciones CRUD y autenticación de usuarios.";
    }
}
