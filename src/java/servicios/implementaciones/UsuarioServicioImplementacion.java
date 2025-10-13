package servicios.implementaciones;

import mapper.UsuarioMapper;
import modelo.Usuario;
import repositorio.UsuarioRepositorio;
import servicios.UsuarioServicio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioServicioImplementacion implements UsuarioServicio {
    private final UsuarioRepositorio usuarioRepositorio;
    public UsuarioServicioImplementacion() {
        usuarioRepositorio = new UsuarioRepositorio();
    }

    public boolean crearUsuario(Usuario usuario){
                int idRol = obtenerIdRolPorNombre(usuario.getRol());
                if(idRol>0){
                    return usuarioRepositorio.crearUsuario(usuario,idRol);
                }
                return false;
    }

    public Usuario obtenerUsuarioId(int id){
        ResultSet rs = usuarioRepositorio.obtenerUsuarioId(id);
        if(rs!=null){
            return UsuarioMapper.toUsuario(rs);
        }
        return null;
    }

    public boolean editarUsuario(int id, Usuario usuario){
            int idRol = obtenerIdRolPorNombre(usuario.getRol());
            Usuario usuarioV = obtenerUsuarioId(id);
            if(usuarioV!=null && idRol > 0){
                return usuarioRepositorio.editarUsuario(id,usuario,idRol);
            }
            return false;
    }

    public boolean existeUsuarioCorreo(String correo){
        return usuarioRepositorio.existeUsuarioCorreo(correo);
    }

    public boolean loginValido(String correo, String contrasenia){
        return usuarioRepositorio.loginValido(correo,contrasenia);
    }

    public ArrayList<Usuario> obtenerTodos(){
        return UsuarioMapper.toListUsuarios(usuarioRepositorio.obtenerTodos());
    }

    public boolean eliminar(int id){
        return usuarioRepositorio.eliminar(id);
    }

    public int obtenerIdRolPorNombre(String nombre){
        return usuarioRepositorio.obtenerIdRolPorNombre(nombre);
    }

    public ArrayList<String> obtenerTodosLosRoles(){
        ArrayList<String> roles = new ArrayList<>();
        ResultSet rs = usuarioRepositorio.obtenerTodosLosRoles();
        if(rs!=null){
            while(true){
                try {
                    if (!rs.next()) break;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    roles.add(rs.getString("nombre"));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return roles;
    }
}
