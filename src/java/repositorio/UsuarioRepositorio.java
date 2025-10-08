/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;

import controlador.mapper.UsuarioMapper;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Usuario;

/**
 *
 * @author Steven
 */
public class UsuarioRepositorio {

    public UsuarioRepositorio() {
    }
    
    
    public Connection conectarBaseDeDatos() throws ClassNotFoundException, SQLException{
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/VitaLink","postgres","1605");
    }
    
    public boolean crearUsuario(Usuario usuario){
        try{
            int rowsAffected;
            int idRol = obtenerIdRolPorNombre(usuario.getRol());
            try (Connection c = conectarBaseDeDatos()) {
                PreparedStatement st = c.prepareStatement("INSERT INTO usuarios (id,id_rol,nombre,correo,contrasenia) VALUES(?,?,?,?,?)");
                st.setInt(1,idRol);
                st.setString(2, usuario.getNombre());
                st.setString(3, usuario.getCorreo());
                st.setString(4, usuario.getContrasenia());
                rowsAffected = st.executeUpdate();
                System.out.println(rowsAffected + " row(s) inserted.");
            }
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public Usuario obtenerUsuarioId(int id){
        try{
            ResultSet rs;
            try (Connection c = conectarBaseDeDatos()) {
                Statement st = c.createStatement();
                rs = st.executeQuery("SELECT u.nombre, u.apellido, u.correo, u.contrasenia, r.nombre AS rol FROM usuarios AS u JOIN roles AS r ON r.id = u.id_rol WHERE u.id ="+id);
            }
            return UsuarioMapper.toUsuario(rs);
        } catch (SQLException | ClassNotFoundException ex){
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean editarUsuario(int id, Usuario usuario){
        Usuario usuarioV = obtenerUsuarioId(id);
        if(usuarioV!=null){
            try{
                int rowsAffected;
                int idRol = obtenerIdRolPorNombre(usuario.getRol());
                try (Connection c = conectarBaseDeDatos()) {
                    PreparedStatement st = c.prepareStatement("INSERT INTO usuarios (id_rol,nombre,correo,contrasenia) VALUES(?,?,?,?) WHERE id = "+usuario.getId());
                    st.setInt(1,usuario.getId());
                    st.setInt(2,idRol);
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
    
    public int obtenerIdRolPorNombre(String nombre){
        try{
            ResultSet rs;
            try (Connection c = conectarBaseDeDatos()) {
                Statement st = c.createStatement();
                rs = st.executeQuery("SELECT id FROM roles WHERE nombre = '"+nombre+"'");
            }
            return rs.getInt("id");
        } catch (SQLException | ClassNotFoundException ex){
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public ArrayList<String> obtenerTodosLosRoles() {
        ArrayList<String> roles = new ArrayList<>();
        try{
            ResultSet rs;
            try (Connection c = conectarBaseDeDatos()) {
                Statement st = c.createStatement();
                rs = st.executeQuery("SELECT nombre FROM roles");
            }
            do{
                roles.add(rs.getString("nombre"));
            }while(rs.next());
        } catch (SQLException | ClassNotFoundException ex){
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return roles;
    }
}
