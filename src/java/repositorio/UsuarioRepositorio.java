/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;

import controlador.mapper.UsuarioMapper;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Usuario;

/**
 *
 * @author Steven
 */
public class UsuarioRepositorio {
    UsuarioMapper usuarioMapper;

    public UsuarioRepositorio() {
        usuarioMapper = new UsuarioMapper();
    }
    
    public Connection conectarBaseDeDatos() throws ClassNotFoundException, SQLException{
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/VitaLink","postgres","1605");
    }
    
    public boolean crearUsuario(Usuario usuario){
        try{
            int rowsAffected;
            try (Connection c = conectarBaseDeDatos()) {
                PreparedStatement st = c.prepareStatement("INSERT INTO usuarios (id,id_rol,nombre,correo,contrasenia) VALUES(?,?,?,?,?)");
                st.setLong(1,usuario.getId());
                st.setLong(2,usuario.getIdRol());
                st.setString(3, usuario.getNombre());
                st.setString(4, usuario.getCorreo());
                st.setString(5, usuario.getContrasenia());
                rowsAffected = st.executeUpdate();
                System.out.println(rowsAffected + " row(s) inserted.");
            }
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
   
    public boolean existeUsuarioCorreo(String correo){
        try{
            ResultSet rs;
            try (Connection c = conectarBaseDeDatos()) {
                Statement st = c.createStatement();
                rs = st.executeQuery("SELECT id FROM usuarios WHERE correo = '"+correo+"'");
            }
            return rs.next();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean loginValido(String correo, String contrasenia){
        try{
            ResultSet rs;
            try (Connection c = conectarBaseDeDatos()) {
                Statement st = c.createStatement();
                rs = st.executeQuery("SELECT id FROM usuarios WHERE correo = '"+correo+"' AND contrasenia = '"+contrasenia+"'");
            }
            return rs.next();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
