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
    
    public Usuario toUsuario(ResultSet row){
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
    
    public ArrayList<Usuario> toListUsuarios(ResultSet rs){
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try{
            while(rs.next()){
                usuarios.add(toUsuario(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuarios;
    }
}