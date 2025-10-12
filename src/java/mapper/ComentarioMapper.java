/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mapper;

import modelo.Comentario;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;
/**
 *
 * @author Steven
 */
public class ComentarioMapper {

    public ComentarioMapper() {
    }
    
    public static Comentario toComentario(ResultSet row){
        try{
            return new Comentario(
                    row.getInt("id"),
                    LocalDateTime.parse(row.getString("fecha")),
                    row.getString("contenido"),
                    row.getString("foto")
            );
        } catch (SQLException ex) {
            Logger.getLogger(ComentarioMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static ArrayList<Comentario> toListComentario(ResultSet rs){
        ArrayList<Comentario> comentarios = new ArrayList<>();
        try {
            do {
                comentarios.add(toComentario(rs));
            } while(rs.next());
        } catch(SQLException ex) {
            Logger.getLogger(ComentarioMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return comentarios;
    }
}
