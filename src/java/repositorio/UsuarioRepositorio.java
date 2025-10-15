/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;

import java.sql.*;
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
    
    public boolean crearUsuario(Usuario usuario, int idRol){
        try{
            int rowsAffected;
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.INFO, "REPOSITORIO : '{' idRol: {0}'}'", new Object[]{idRol});

            try (Connection c = conectarBaseDeDatos()) {
                try (PreparedStatement st = c.prepareStatement("INSERT INTO usuarios (id,id_rol,nombre,correo,contrasenia) VALUES(?,?,?,?,?)")) {
                    st.setInt(1, usuario.getId());
                    st.setInt(2,idRol);
                    st.setString(3, usuario.getNombre());
                    st.setString(4, usuario.getCorreo());
                    st.setString(5, usuario.getContrasenia());
                    rowsAffected = st.executeUpdate();
                }
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
            try (Connection c = conectarBaseDeDatos(); Statement st = c.createStatement()) {
                rs = st.executeQuery("SELECT u.id, u.nombre, u.correo, u.contrasenia, r.nombre AS rol FROM usuarios AS u JOIN roles AS r ON r.id = u.id_rol WHERE u.id ="+id);
                if (rs.next()) {
                    return new Usuario(
                        rs.getInt("id"),
                        rs.getString("rol"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("contrasenia")
                    );
                }
            }
        } catch (SQLException | ClassNotFoundException ex){
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean editarUsuario(int id, Usuario usuario, int idRol){
        try{
            int rowsAffected;
            try (Connection c = conectarBaseDeDatos()) {
                PreparedStatement st = c.prepareStatement("INSERT INTO usuarios (id_rol,nombre,correo,contrasenia) VALUES(?,?,?,?) WHERE id = "+id);
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
   
    public boolean existeUsuarioCorreo(String correo){
        try{
            ResultSet rs;
            try (Connection c = conectarBaseDeDatos()) {
                Statement st = c.createStatement();
                st.close();
                rs = st.executeQuery("SELECT id FROM usuarios WHERE correo = '"+correo+"'");
            }
            return rs.next();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public Usuario loginValido(String correo, String contrasenia){
        ResultSet rs;
        try (Connection c = conectarBaseDeDatos()) {
            Statement st = c.createStatement();
            rs = st.executeQuery("SELECT u.id, r.nombre AS rol, u.nombre, u.correo, u.contrasenia  FROM usuarios AS u JOIN roles AS r ON u.id_rol = r.id WHERE correo = '" + correo + "' AND contrasenia = '" + contrasenia + "'");
            rs.next();
            return new Usuario(
                        rs.getInt("id"),
                        rs.getString("rol"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("contrasenia")
                    );
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ResultSet obtenerTodos(){
        ResultSet rs;
        try (Connection c = conectarBaseDeDatos()){
            Statement st = c.createStatement();
            st.close();
            rs = st.executeQuery("SELECT * FROM usuarios");
            return rs;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean eliminar(int id){
        boolean borro = false;
        try(Connection c = conectarBaseDeDatos()){
            ResultSet rs;
            Statement st = c.createStatement();
            rs = st.executeQuery("SELECT * FROM usuarios WHERE id = "+id);
            if(rs.next()){
                rs.deleteRow();
                rs.close();
                st.close();
                return !borro;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return borro;
    }
    
    public int obtenerIdRolPorNombre(String nombre) {
        int id = 0;
        String sql = "SELECT id FROM roles WHERE nombre = ?";

        try (Connection c = conectarBaseDeDatos();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    id = rs.getInt("id");
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }

        return id;
    }

    
    public ResultSet obtenerTodosLosRoles() {
        try{
            ResultSet rs;
            try (Connection c = conectarBaseDeDatos(); Statement st = c.createStatement()) {
                rs = st.executeQuery("SELECT nombre FROM roles");
            }
            return rs;
        } catch (SQLException | ClassNotFoundException ex){
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
