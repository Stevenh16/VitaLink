package servicios.implementaciones;

import mapper.UsuarioMapper;
import modelo.Usuario;
import repositorio.UsuarioRepositorio;
import servicios.UsuarioServicio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioServicioImplementacion implements UsuarioServicio {
    private final UsuarioRepositorio usuarioRepositorio;
    public UsuarioServicioImplementacion() {
        usuarioRepositorio = new UsuarioRepositorio();
    }

    @Override
    public boolean crearUsuario(Usuario usuario){
                int idRol = obtenerIdRolPorNombre(usuario.getRol());
                Logger.getLogger(UsuarioServicioImplementacion.class.getName()).log(Level.INFO, "SERVICIO : '{' idRol: {0}, ROL: {1}'}'", new Object[]{idRol,usuario.getRol()});

                if(idRol>0){
                    return usuarioRepositorio.crearUsuario(usuario,idRol);
                }
                return false;
    }

    @Override
    public Usuario obtenerUsuarioId(int id){
        return usuarioRepositorio.obtenerUsuarioId(id);
    }

    @Override
    public boolean editarUsuario(int id, Usuario usuario){
            int idRol = obtenerIdRolPorNombre(usuario.getRol());
            Usuario usuarioV = obtenerUsuarioId(id);
            if(usuarioV!=null && idRol > 0){
                return usuarioRepositorio.editarUsuario(id,usuario,idRol);
            }
            return false;
    }

    @Override
    public boolean existeUsuarioCorreo(String correo){
        return usuarioRepositorio.existeUsuarioCorreo(correo);
    }

    @Override
    public Usuario loginValido(String correo, String contrasenia){
        return usuarioRepositorio.loginValido(correo,contrasenia);
    }

    @Override
    public ArrayList<Usuario> obtenerTodos(){
        return UsuarioMapper.toListUsuarios(usuarioRepositorio.obtenerTodos());
    }

    @Override
    public boolean eliminar(int id){
        return usuarioRepositorio.eliminar(id);
    }

    @Override
    public int obtenerIdRolPorNombre(String nombre){
        return usuarioRepositorio.obtenerIdRolPorNombre(nombre);
    }

    @Override
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
