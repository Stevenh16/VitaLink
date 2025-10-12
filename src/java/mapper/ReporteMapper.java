/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mapper;

import modelo.Reporte;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.sql.*;
import java.time.LocalDateTime;
import modelo.Campaña;
import modelo.Usuario;
/**
 *
 * @author Steven
 */
public class ReporteMapper {

    public ReporteMapper() {
    }
    
    public static Reporte toReporte(ResultSet row, Campaña campaña, Usuario usuario){
        try {
            return new Reporte(
                    row.getInt("id"),
                    row.getString("contenido"),
                    LocalDateTime.parse(row.getString("fecha")),
                    campaña,
                    usuario
            );
        } catch (SQLException ex) {
            Logger.getLogger(ReporteMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    } 
}
