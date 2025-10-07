/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.mapper;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import modelo.Campaña;
import java.util.logging.Logger;
import java.util.logging.Level;
import modelo.Usuario;

/**
 *
 * @author Steven
 */
public class CampañaMapper {

    public CampañaMapper() {
    }
    
    public static Campaña toCampaña(ResultSet row){
        try{
            return new Campaña(
                    row.getInt("id"),
                    row.getString("titulo"),
                    row.getString("descripcion"),
                    LocalDateTime.parse(row.getString("fechaInicio")),
                    new Usuario(row.getLong("idDonatario"))
            );
        } catch (SQLException ex){
            Logger.getLogger(CampañaMapper.class.getName()).log(Level.SEVERE,null,ex);
        }
        return null;
    }
    
    public static ArrayList<Campaña> toListCampaña(ResultSet rs){
        ArrayList<Campaña> campañas = new ArrayList<>();
        try{
            do{
                campañas.add(toCampaña(rs));
            } while (rs.next());
        } catch (SQLException ex){
            Logger.getLogger(CampañaMapper.class.getName()).log(Level.SEVERE,null,ex);
        }
        return campañas;
    }
}
