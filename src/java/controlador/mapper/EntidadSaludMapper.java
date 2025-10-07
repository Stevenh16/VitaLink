/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.mapper;

import modelo.EntidadSalud;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.sql.*;
import java.util.ArrayList;
import modelo.Usuario;

/**
 *
 * @author Steven
 */
public class EntidadSaludMapper {

    public EntidadSaludMapper() {
    }
    
    public static EntidadSalud toEntidadSalud(ResultSet row){
        try {
            return new EntidadSalud(
                    row.getInt("id"),
                    row.getString("nombre"),
                    row.getString("pais"),
                    row.getString("ciudad"),
                    new Usuario(row.getLong("idGerente"))
            );
        } catch (SQLException ex) {
            Logger.getLogger(EntidadSaludMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static ArrayList<EntidadSalud> toListEntidadSalud(ResultSet rs){
        
    }
}
