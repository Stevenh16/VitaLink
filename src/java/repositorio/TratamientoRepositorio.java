/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Steven
 */
public class TratamientoRepositorio {

    public TratamientoRepositorio() {
    }
    
    public Connection conectarBaseDeDatos() throws ClassNotFoundException, SQLException{
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/VitaLink","postgres","1605");
    }
    
    public ResultSet obtenerPorId(int id){
        try{
            try (Connection c = conectarBaseDeDatos()) {
                PreparedStatement ps = c.prepareStatement(
                        "SELECT "
                        + "id, "
                        + "id_entidad_salud, "
                        + "nombre, "
                        + "descripcion "
                        + "FROM tratamientos "
                        + "WHERE id = ?"
                );
                ps.setInt(1, id);
                return ps.executeQuery();
            }
        } catch (SQLException | ClassNotFoundException ex){
            Logger.getLogger(TratamientoRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ResultSet obtenerTodosPorIdCampania(int id){
        try{
            ResultSet rs;
            try (Connection c = conectarBaseDeDatos(); Statement st = c.createStatement()) {
                rs = st.executeQuery("SELECT id FROM tratamientos WHERE id_campaña = "+id);
                return rs;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (ClassNotFoundException ex){
            Logger.getLogger(ComentarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
