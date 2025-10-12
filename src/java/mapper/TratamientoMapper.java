/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mapper;

import modelo.Tratamiento;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.sql.*;
import modelo.EntidadSalud;
/**
 *
 * @author Steven
 */
public class TratamientoMapper {

    public TratamientoMapper() {
    }
    
    public static Tratamiento toTratamiento(ResultSet row, EntidadSalud entidad){
        try {
            return new Tratamiento(
                    row.getInt("id"),
                    row.getString("nombre"),
                    row.getString("descripcion"),
                    entidad
            );
        } catch (SQLException ex) {
            Logger.getLogger(TratamientoMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
