package servicios.implementaciones;

import mapper.DonacionMapper;
import modelo.Campaña;
import modelo.Donacion;
import modelo.Usuario;
import repositorio.DonacionRepositorio;
import servicios.DonacionServicio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DonacionServicioImplementacion implements DonacionServicio {
    private final DonacionRepositorio repositorio = new  DonacionRepositorio();
    private final CampañaServicioImplementacion campañaServicio = new CampañaServicioImplementacion();
    private final UsuarioServicioImplementacion usuarioServicio = new UsuarioServicioImplementacion();

    DonacionServicioImplementacion() {}

    @Override
    public int obtenerIdTipoPorNombre(String tipo) {
       return repositorio.obtenerIdtipoPorNombre(tipo);
    }

    @Override
    public boolean crear(Donacion donacion) {
        int idTipo = obtenerIdTipoPorNombre(donacion.getTipo());
        return repositorio.crear(donacion, idTipo);
    }

    @Override
    public Donacion obtenerPorId(int id) {
        try(ResultSet rs = repositorio.obtenerPorId(id)){
            Campaña campaña =  campañaServicio.buscarPorIdSinDonaciones(rs.getInt("id_campaña"));
            Usuario usuario = usuarioServicio.obtenerUsuarioId(rs.getInt("id_donante"));
            return DonacionMapper.toDonacion(rs, campaña, usuario);
        } catch (SQLException ex){
            Logger.getLogger(DonacionRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Donacion obtenerPorIdSinCampaña(int id) {
        try(ResultSet rs = repositorio.obtenerPorIdSinCampaña(id)){
            Usuario usuario = usuarioServicio.obtenerUsuarioId(rs.getInt("id_donante"));
            return DonacionMapper.toDonacion(rs, null, usuario);
        } catch (SQLException ex){
            Logger.getLogger(DonacionRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<Donacion> obtenerTodos() {
        ArrayList<Donacion> donaciones = new ArrayList<>();
        try(ResultSet rs = repositorio.obtenerTodos()){
            while(rs.next()){
                donaciones.add(obtenerPorId(rs.getInt("id")));
            }
        } catch  (SQLException ex){
            Logger.getLogger(DonacionRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return donaciones;
    }

    public ArrayList<Donacion> obtenerTodosPorIdCampaña(int id) {
        ArrayList<Donacion> donaciones = new ArrayList<>();
        try(ResultSet rs = repositorio.obtenerTodos()){
            while(rs.next()){
                donaciones.add(obtenerPorIdSinCampaña(rs.getInt("id")));
            }
        } catch  (SQLException ex){
            Logger.getLogger(DonacionRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return donaciones;
    }
}
