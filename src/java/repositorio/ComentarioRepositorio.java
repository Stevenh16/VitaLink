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
public class ComentarioRepositorio {

    public ComentarioRepositorio() {
    }
    
    public Connection conectarBaseDeDatos() throws ClassNotFoundException, SQLException{
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/VitaLink","postgres","1605");
    }
    
    public ResultSet obtenerTodosPorIdCampaña(int id){
        try{
            try (Connection c = conectarBaseDeDatos(); Statement st = c.createStatement()) {
                return st.executeQuery("SELECT id, fecha, contenido, foto FROM comentarios WHERE id_campaña = "+id);
            }
        } catch (SQLException | ClassNotFoundException ex){
            Logger.getLogger(ComentarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
