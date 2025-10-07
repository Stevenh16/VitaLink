/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.mapper;

import modelo.Donacion;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import modelo.Campaña;
import modelo.TipoDonacion;
import modelo.Usuario;

/**
 *
 * @author Steven
 */
public class DonacionMapper {

    public DonacionMapper() {
    }
    
    public static Donacion toDonacion(ResultSet row){
        try{
            return new Donacion(
                    row.getInt("id"),
                    TipoDonacion.valueOf(row.getString("tipo")),
                    row.getString("monto"),
                    LocalDateTime.parse(row.getString("fecha")),
                    new Campaña(row.getInt("idCampaña")),
                    new Usuario(row.getLong("idDonante"))
            );
        } catch (SQLException ex){
            Logger.getLogger(DonacionMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static ArrayList<Donacion> toListDonacion(ResultSet rs){
        ArrayList<Donacion> donaciones = new ArrayList<>();
        try {
            do {
                donaciones.add(toDonacion(rs));
            } while(rs.next());
        } catch (SQLException ex) {
            Logger.getLogger(ComentarioMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return donaciones;
    }
}
