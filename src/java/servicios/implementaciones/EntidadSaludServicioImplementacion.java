package servicios.implementaciones;

import mapper.EntidadSaludMapper;
import modelo.EntidadSalud;
import modelo.Usuario;
import repositorio.EntidadSaludRepositorio;
import servicios.EntidadSaludServicio;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EntidadSaludServicioImplementacion implements EntidadSaludServicio {
    UsuarioServicioImplementacion  usuarioServicio = new UsuarioServicioImplementacion();
    EntidadSaludRepositorio entidadSaludRepositorio = new  EntidadSaludRepositorio();

    public EntidadSalud obtenerPorId(int id){
        ResultSet rs = entidadSaludRepositorio.obtenerPorId(id);
        if(rs!=null){
            try {
                rs.next();
                Usuario usuario = usuarioServicio.obtenerUsuarioId(rs.getInt("id_gerente"));
                if(usuario!=null) return EntidadSaludMapper.toEntidadSalud(rs,usuario);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return null;
    }
}
