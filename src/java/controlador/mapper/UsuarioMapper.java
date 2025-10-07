/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.mapper;

import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Usuario;
import java.sql.*;
import java.util.ArrayList;
import modelo.Rol;

/**
 *
 * @author Steven
 */
public class UsuarioMapper {

    public UsuarioMapper() {
    }
    
    public static Usuario toUsuario(ResultSet row){
        try{
            return new Usuario(
                    Long.valueOf(row.getString("id")),
                    Rol.valueOf(row.getString("rol")),
                    row.getString("nombre"),
                    row.getString("correo"),
                    row.getString("contrasenia")
            );
        } catch (SQLException ex){
            Logger.getLogger(UsuarioMapper.class.getName()).log(Level.SEVERE,null,ex);
        }
        return null;
    }
    
    public static ArrayList<Usuario> toListUsuarios(ResultSet rs){
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try{
            do{
                usuarios.add(toUsuario(rs));
            } while(rs.next());
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuarios;
    }
}