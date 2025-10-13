/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Donacion;

/**
 *
 * @author Steven
 */
public class DonacionRepositorio {

    public DonacionRepositorio() {
    }

    private Connection conectarBaseDeDatos() throws ClassNotFoundException, SQLException{
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/VitaLink","postgres","1605");
    }

    public int obtenerIdtipoPorNombre(String tipo) {
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

    public boolean crear(Donacion donacion, int idTipo){
        try{
            int rowsAffected;
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
    
    public ResultSet obtenerPorId(int id){
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
            return ps.executeQuery();
        } catch (SQLException | ClassNotFoundException ex){
            Logger.getLogger(DonacionRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ResultSet obtenerPorIdSinCampaña(int id){
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
            return ps.executeQuery();
        } catch (SQLException | ClassNotFoundException ex){
            Logger.getLogger(DonacionRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ResultSet obtenerTodos(){
        try (Connection c = conectarBaseDeDatos(); Statement st = c.createStatement()) {
            return st.executeQuery("SELECT id FROM donaciones");
        } catch (SQLException | ClassNotFoundException ex){
            Logger.getLogger(DonacionRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
