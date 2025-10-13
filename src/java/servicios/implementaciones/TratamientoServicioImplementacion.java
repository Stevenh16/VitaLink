package servicios.implementaciones;

import mapper.TratamientoMapper;
import modelo.EntidadSalud;
import modelo.Tratamiento;
import repositorio.TratamientoRepositorio;
import servicios.TratamientoServicio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TratamientoServicioImplementacion implements TratamientoServicio {
    private final TratamientoRepositorio repositorio = new  TratamientoRepositorio();
    private final EntidadSaludServicioImplementacion entidadServicio = new EntidadSaludServicioImplementacion();

    public TratamientoServicioImplementacion() {}

    public Tratamiento obtenerPorId(int id){
        ResultSet rs = repositorio.obtenerPorId(id);
        if(rs!=null){
            try {
                rs.next();
                EntidadSalud entidad = entidadServicio.obtenerPorId(rs.getInt("id_entidad_salud"));
                if(entidad!=null) return TratamientoMapper.toTratamiento(rs,entidad);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public ArrayList<Tratamiento> obtenerTodosPorIdCampania(int id){
        ArrayList<Tratamiento> tratamientos = new ArrayList<>();
        try(ResultSet rs = repositorio.obtenerTodosPorIdCampania(id)) {
            if(rs!=null){
                while(rs.next()){
                    tratamientos.add(obtenerPorId(rs.getInt("id")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tratamientos;
    }
}
