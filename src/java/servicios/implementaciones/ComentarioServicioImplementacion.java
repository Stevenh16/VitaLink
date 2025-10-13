package servicios.implementaciones;

import mapper.ComentarioMapper;
import modelo.Comentario;
import repositorio.ComentarioRepositorio;
import servicios.ComentarioServicio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ComentarioServicioImplementacion implements ComentarioServicio {
    private final ComentarioRepositorio repositorio = new ComentarioRepositorio();

    @Override
    public ArrayList<Comentario> obtenerTodosPorIdCampaña(int id) {
        ArrayList<Comentario> comentarios = new ArrayList<>();
        try(ResultSet rs = repositorio.obtenerTodosPorIdCampaña(id)){
            comentarios = ComentarioMapper.toListComentario(rs);
        } catch (SQLException ex){
            Logger.getLogger(ComentarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return comentarios;
    }
}
