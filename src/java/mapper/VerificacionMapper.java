/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mapper;

import modelo.Usuario;
import modelo.Verificacion;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.sql.*;
import java.time.LocalDateTime;
import modelo.Campaña;
/**
 *
 * @author Steven
 */
public class VerificacionMapper {

    public VerificacionMapper() {
    }
    
    public static Verificacion toVerificacion(ResultSet row, Campaña campaña, Usuario usuario){
        try {
            return new Verificacion(
                    row.getInt("id"),
                    LocalDateTime.parse(row.getString("fecha")),
                    campaña,
                    usuario
            );
                    } catch (SQLException ex) {
            Logger.getLogger(VerificacionMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
