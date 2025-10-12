/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mapper;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import modelo.Campaña;
import java.util.logging.Logger;
import java.util.logging.Level;
import modelo.Comentario;
import modelo.Donacion;
import modelo.Tratamiento;
import modelo.Usuario;

/**
 *
 * @author Steven
 */
public class CampañaMapper {

    public CampañaMapper() {
    }
    
    public static Campaña toCampaña(ResultSet row, Usuario usuario, ArrayList<Donacion> donaciones, ArrayList<Comentario> comentarios, ArrayList<Tratamiento> tratamientos){
        try{
            Campaña campaña = new Campaña(
                    row.getInt("id"),
                    row.getString("titulo"),
                    row.getString("descripcion"),
                    row.getString("estado"),
                    LocalDateTime.parse(row.getString("fechaInicio")),
                    usuario
            );
            if(donaciones!=null){
                campaña.setDonaciones(donaciones);
            }
            campaña.setComentarios(comentarios);
            campaña.setTratamientos(tratamientos);
            return campaña;
        } catch (SQLException ex){
            Logger.getLogger(CampañaMapper.class.getName()).log(Level.SEVERE,null,ex);
        }
        return null;
    }
    
}
