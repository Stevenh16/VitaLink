/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;

import controlador.mapper.ComentarioMapper;
import java.util.ArrayList;
import modelo.Comentario;
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
    
    public ArrayList<Comentario> obtenerTodosPorIdCampaña(int id){
        ArrayList<Comentario> comentarios = new ArrayList<>();
        try{
            ResultSet rs;
            try (Connection c = conectarBaseDeDatos(); Statement st = c.createStatement()) {
                rs = st.executeQuery("SELECT id, fecha, contenido, foto FROM comentarios WHERE id_campaña = "+id);
            }
            do{
                comentarios = ComentarioMapper.toListComentario(rs);
            }while(rs.next());
            rs.close();
        } catch (SQLException | ClassNotFoundException ex){
            Logger.getLogger(ComentarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return comentarios;
    }
}
