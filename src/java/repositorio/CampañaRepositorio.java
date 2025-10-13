/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Campaña;

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
    
    public boolean crear(Campaña campaña, int idEstado){
        try{
            int rowsAffected;
            try (Connection c = conectarBaseDeDatos()) {
                try (PreparedStatement st = c.prepareStatement("INSERT INTO campañas (id,id_estado,id_donatario,descripcion,fecha_inicio) VALUES(?,?,?,?,?)")) {
                    st.setInt(1, campaña.getId());
                    st.setInt(2, idEstado);
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
    
    public ResultSet buscarPorId(int id){
        try{
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
                return ps.executeQuery();
            }
        } catch (SQLException | ClassNotFoundException ex){
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ResultSet buscarPorIdSinDonaciones(int id){
        try{
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
                return ps.executeQuery();
            }
        } catch (SQLException | ClassNotFoundException ex){
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ResultSet obtenerTodas() {
        try{
            ResultSet rs;
            try (Connection c = conectarBaseDeDatos(); Statement st = c.createStatement()) {
                rs = st.executeQuery("SELECT id FROM campañas");
                return rs;
            }
        } catch (SQLException | ClassNotFoundException ex){
            Logger.getLogger(ComentarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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