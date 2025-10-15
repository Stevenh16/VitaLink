/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Campaña;
import modelo.Donacion;
import modelo.Usuario;

/**
 *
 * @author Steven
 */
public class CampañaRepositorio {
    
    
    public Connection conectarBaseDeDatos() throws ClassNotFoundException, SQLException{
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/VitaLink","postgres","1605");
    }
    
    public boolean crear(Campaña campaña, int idEstado){
        try{
            int rowsAffected;
            try (Connection c = conectarBaseDeDatos()) {
                try (PreparedStatement st = c.prepareStatement("INSERT INTO campañas (id,id_estado,id_donatario,titulo,descripcion,fecha_inicio) VALUES(?,?,?,?,?,?)")) {
                    st.setInt(1, campaña.getId());
                    st.setInt(2, idEstado);
                    st.setInt(3, campaña.getDonatario().getId());
                    st.setString(4, campaña.getTitulo());
                    st.setString(5, campaña.getDescripcion());
                    st.setDate(6, Date.valueOf(campaña.getFechaInicio().toLocalDate()));
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
            try (Connection c = conectarBaseDeDatos()) {
                PreparedStatement ps = c.prepareStatement(
                    "SELECT "
                    + "c.id, "
                    + "c.id_donatario, "
                    + "c.titulo, "
                    + "c.descripcion, "
                    + "c.fecha_inicio, "
                    + "c.fecha_fin, "
                    + "e.nombre AS estado "
                    + "FROM campañas AS c "
                    + "JOIN estado_campaña AS e ON e.id = c.id_estado "
                    + "WHERE c.id = ?"
                );
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return new Campaña(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("descripcion"),
                        rs.getString("estado"),
                        rs.getDate("fecha_inicio").toLocalDate().atStartOfDay(),
                        rs.getDate("fecha_fin") != null ? rs.getDate("fecha_fin").toLocalDate().atStartOfDay() : null,
                        new Usuario(rs.getInt("id_donatario"))
                    );
                }
            }
        } catch (SQLException | ClassNotFoundException ex){
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Campaña buscarPorIdSinDonaciones(int id){
        try{
            try (Connection c = conectarBaseDeDatos()) {
                PreparedStatement ps = c.prepareStatement(
                    "SELECT "
                    + "c.id, "
                    + "c.id_donatario, "
                    + "c.titulo, "
                    + "c.descripcion, "
                    + "c.fecha_inicio, "
                    + "c.fecha_fin, "
                    + "e.nombre AS estado "
                    + "FROM campañas AS c "
                    + "JOIN estado_campaña AS e ON e.id = c.id_estado "
                    + "WHERE c.id = ?"
                );
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return new Campaña(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("descripcion"),
                        rs.getString("estado"),
                        rs.getDate("fecha_inicio").toLocalDate().atStartOfDay(),
                        rs.getDate("fecha_fin") != null ? rs.getDate("fecha_fin").toLocalDate().atStartOfDay() : null,
                        new Usuario(rs.getInt("id_donatario"))
                    );
                }
            }
        } catch (SQLException | ClassNotFoundException ex){
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ArrayList<Integer> obtenerTodas() {
        ArrayList<Integer> ids = new ArrayList<>();
        try{
            ResultSet rs;
            try (Connection c = conectarBaseDeDatos(); Statement st = c.createStatement()) {
                rs = st.executeQuery("SELECT id FROM campañas");
                while (rs.next()) {
                    ids.add(rs.getInt("id"));
                }
            }
        } catch (SQLException | ClassNotFoundException ex){
            Logger.getLogger(ComentarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ids;
    }
    
    public boolean editarCampaña(Campaña campaña, int id){
        String sql = "UPDATE campañas SET fecha_fin = ?, id_estado = 2 WHERE id = ?";
        try (Connection c = conectarBaseDeDatos(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(campaña.getFechaFin().toLocalDate()));
            ps.setInt(2, id);

            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ComentarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int obtenerIdEstadoPorNombre(String estado) {
        try (Connection c = conectarBaseDeDatos(); Statement st = c.createStatement()) {
                ResultSet rs = st.executeQuery("SELECT id FROM estado_campaña WHERE nombre = '"+estado+"'");
                if(rs.next()){
                    return rs.getInt("id");
                }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CampañaRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}