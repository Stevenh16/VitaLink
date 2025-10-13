package servicios;

import modelo.Usuario;

import java.util.ArrayList;

public interface UsuarioServicio {
    boolean crearUsuario(Usuario usuario);
    Usuario obtenerUsuarioId(int id);
    boolean editarUsuario(int id, Usuario usuario);
    boolean existeUsuarioCorreo(String correo);
    boolean loginValido(String correo, String contrasenia);
    ArrayList<Usuario> obtenerTodos();
    boolean eliminar(int id);
    int obtenerIdRolPorNombre(String nombre);
    ArrayList<String> obtenerTodosLosRoles();
}
