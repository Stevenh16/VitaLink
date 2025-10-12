/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;

import controlador.mapper.DonacionMapper;
import java.sql.*;
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
public class DonacionRepositorio {
    private UsuarioRepositorio usuarioRepositorio;
    private CampañaRepositorio campañaRepositorio;

    private int obtenerIdtipoPorNombre(String tipo) {
        try (Connection c = conectarBaseDeDatos(); Statement st = c.createStatement()) {
                ResultSet rs = st.executeQuery("SELECT id FROM tipo_donaciones WHERE nombre = '"+tipo+"'");
                if(rs.next()){
                    return rs.getInt("id");
                }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DonacionRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    
    public Connection conectarBaseDeDatos() throws ClassNotFoundException, SQLException{
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/VitaLink","postgres","1605");
    }
    
    public boolean crear(Donacion donacion){
        try{
            int rowsAffected;
            int idTipo = obtenerIdtipoPorNombre(donacion.getTipo());
            try (Connection c = conectarBaseDeDatos()) {
                try (PreparedStatement st = c.prepareStatement("INSERT INTO donaciones (id,id_campaña,id_tipo,id_donante,cantidad,fecha) VALUES(?,?,?,?,?)")) {
                    st.setInt(1, donacion.getId());
                    st.setInt(2, donacion.getCampaña().getId());
                    st.setInt(3, idTipo);
                    st.setInt(4, donacion.getDonante().getId());
                    st.setString(5, donacion.getFecha().toString());
                    rowsAffected = st.executeUpdate();
                }
                System.out.println(rowsAffected + " row(s) inserted.");
            }
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DonacionRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public Donacion obtenerPorId(int id){
        try{
            ResultSet rs;
            try (Connection c = conectarBaseDeDatos()) {
                PreparedStatement ps = c.prepareStatement(
                        "SELECT "
                        + "d.id, "
                        + "d.id_campaña, "
                        + "d.id_donante, "
                        + "d.cantidad, "
                        + "d.fecha, "
                        + "t.nombre AS tipo "
                        + "FROM donaciones AS d "
                        + "JOIN tipo_donaciones AS t ON d.id_tipo = t.id "
                        + "WHERE d.id = ? "
                );
                ps.setInt(1, id);
                rs = ps.executeQuery();
                
                Usuario usuario = usuarioRepositorio.obtenerUsuarioId(rs.getInt("id_donante"));
                Campaña campaña = campañaRepositorio.buscarPorIdSinDonaciones(rs.getInt("id_campaña"));
                return DonacionMapper.toDonacion(rs, campaña, usuario);
            }
        } catch (SQLException | ClassNotFoundException ex){
            Logger.getLogger(DonacionRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Donacion obtenerPorIdSinCampaña(int id){
        try{
            ResultSet rs;
            try (Connection c = conectarBaseDeDatos()) {
                PreparedStatement ps = c.prepareStatement(
                        "SELECT "
                        + "d.id, "
                        + "d.id_donante, "
                        + "d.cantidad, "
                        + "d.fecha, "
                        + "t.nombre AS tipo "
                        + "FROM donaciones AS d "
                        + "JOIN tipo_donaciones AS t ON d.id_tipo = t.id "
                        + "WHERE d.id = ? "
                );
                ps.setInt(1, id);
                rs = ps.executeQuery();
                
                Usuario usuario = usuarioRepositorio.obtenerUsuarioId(rs.getInt("id_donante"));
                return DonacionMapper.toDonacion(rs, null, usuario);
            }
        } catch (SQLException | ClassNotFoundException ex){
            Logger.getLogger(DonacionRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ArrayList<Donacion> obtenerTodos(){
        ArrayList<Donacion> donaciones = new ArrayList<>();
        try{
            ResultSet rs;
            try (Connection c = conectarBaseDeDatos(); Statement st = c.createStatement()) {
                rs = st.executeQuery("SELECT id FROM donaciones");
            }
            do{
                donaciones.add(obtenerPorId(rs.getInt("id")));
            }while(rs.next());
            rs.close();
        } catch (SQLException | ClassNotFoundException ex){
            Logger.getLogger(DonacionRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return donaciones;
    }
    
    public ArrayList<Donacion> obtenerTodosPorIdCampaña(int id){
        ArrayList<Donacion> donaciones = new ArrayList<>();
        try{
            ResultSet rs;
            try (Connection c = conectarBaseDeDatos(); Statement st = c.createStatement()) {
                rs = st.executeQuery("SELECT id FROM donaciones WHERE id_campaña = "+id);
            }
            do{
                donaciones.add(obtenerPorIdSinCampaña(rs.getInt("id")));
            }while(rs.next());
            rs.close();
        } catch (SQLException | ClassNotFoundException ex){
            Logger.getLogger(DonacionRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return donaciones;
    }
}
