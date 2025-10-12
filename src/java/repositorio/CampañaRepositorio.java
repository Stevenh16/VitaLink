/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;

import mapper.CampañaMapper;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Campaña;
import modelo.Comentario;
import modelo.Donacion;
import modelo.Tratamiento;
import modelo.Usuario;

/**
 *
 * @author Steven
 */
public class CampañaRepositorio {
    private UsuarioRepositorio usuarioRepositorio;
    private DonacionRepositorio donacionRepositorio;
    private ComentarioRepositorio comentarioRepositorio;
    private TratamientoRepositorio tratamientoRepositorio;
    
    
    public Connection conectarBaseDeDatos() throws ClassNotFoundException, SQLException{
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/VitaLink","postgres","1605");
    }
    
    public boolean crear(Campaña campaña){
        try{
            int rowsAffected;
            int idEstado = obtenerIdEstadoPorNombre(campaña.getEstado());
            try (Connection c = conectarBaseDeDatos()) {
                try (PreparedStatement st = c.prepareStatement("INSERT INTO campañas (id,id_estado,id_donatario,descripcion,fecha_inicio) VALUES(?,?,?,?,?)")) {
                    st.setInt(1, campaña.getId());
                    st.setInt(2,idEstado);
                    st.setInt(3, campaña.getDonatario().getId());
                    st.setString(4, campaña.getDescripcion());
                    st.setString(5, campaña.getFechaInicio().toString());
                    rowsAffected = st.executeUpdate();
                }
                System.out.println(rowsAffected + " row(s) inserted.");
            }
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CampañaRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public Campaña buscarPorId(int id){
        try{
            ResultSet rs;
            try (Connection c = conectarBaseDeDatos()) {
                PreparedStatement ps = c.prepareStatement(
                    "SELECT "
                    + "c.id, "
                    + "c.id_donatario, "
                    + "c.descripcion, "
                    + "c.fecha_inicio, "
                    + "c.fecha_fin, "
                    + "e.nombre AS estado "
                    + "FROM campañas AS c "
                    + "JOIN estado_campañas AS e ON e.id = c.id_estado "
                    + "WHERE c.id = ?"
                );
                ps.setInt(1, id);
                rs = ps.executeQuery();
                
                Usuario usuario = usuarioRepositorio.obtenerUsuarioId(rs.getInt("id_donatario"));
                ArrayList<Donacion> donaciones = donacionRepositorio.obtenerTodosPorIdCampaña(id);
                ArrayList<Comentario> comentarios = comentarioRepositorio.obtenerTodosPorIdCampaña(id);
                ArrayList<Tratamiento> tratamientos = tratamientoRepositorio.obtenerTodosPorIdCampaña(id);
                return CampañaMapper.toCampaña(rs, usuario, donaciones, comentarios, tratamientos);
            }
        } catch (SQLException | ClassNotFoundException ex){
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Campaña buscarPorIdSinDonaciones(int id){
        try{
            ResultSet rs;
            try (Connection c = conectarBaseDeDatos()) {
                PreparedStatement ps = c.prepareStatement(
                    "SELECT "
                    + "c.id, "
                    + "c.id_donatario, "
                    + "c.descripcion, "
                    + "c.fecha_inicio, "
                    + "c.fecha_fin, "
                    + "e.nombre AS estado "
                    + "FROM campañas AS c "
                    + "JOIN estado_campañas AS e ON e.id = c.id_estado "
                    + "WHERE c.id = ?"
                );
                ps.setInt(1, id);
                rs = ps.executeQuery();
                
                Usuario usuario = usuarioRepositorio.obtenerUsuarioId(rs.getInt("id_donatario"));
                ArrayList<Comentario> comentarios = comentarioRepositorio.obtenerTodosPorIdCampaña(id);
                ArrayList<Tratamiento> tratamientos = tratamientoRepositorio.obtenerTodosPorIdCampaña(id);
                return CampañaMapper.toCampaña(rs, usuario, null, comentarios, tratamientos);
            }
        } catch (SQLException | ClassNotFoundException ex){
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ArrayList<Campaña> obtenerTodas() {
        ArrayList<Campaña> campañas = new ArrayList<>();
        try{
            ResultSet rs;
            try (Connection c = conectarBaseDeDatos(); Statement st = c.createStatement()) {
                rs = st.executeQuery("SELECT id FROM campañas");
            }
            do{
                campañas.add(buscarPorId(rs.getInt("id")));
            }while(rs.next());
            rs.close();
        } catch (SQLException | ClassNotFoundException ex){
            Logger.getLogger(ComentarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return campañas;
    }
    
    public boolean finalizarPorId(int id){
        Campaña campaña = buscarPorId(id);
        if(campaña!=null){
            campaña.setFechaFin(LocalDateTime.now());
            return true;
        }
        return false;
    }

    public int obtenerIdEstadoPorNombre(String estado) {
        try (Connection c = conectarBaseDeDatos(); Statement st = c.createStatement()) {
                ResultSet rs = st.executeQuery("SELECT id FROM estados_campañas WHERE nombre = '"+estado+"'");
                if(rs.next()){
                    return rs.getInt("id");
                }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CampañaRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}