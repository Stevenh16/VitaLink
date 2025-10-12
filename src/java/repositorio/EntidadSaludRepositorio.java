/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;

import controlador.mapper.EntidadSaludMapper;
import java.sql.*;
import modelo.EntidadSalud;
import modelo.Usuario;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Steven
 */
public class EntidadSaludRepositorio {
    private final UsuarioRepositorio usuarioRepositorio;

    public EntidadSaludRepositorio() {
        this.usuarioRepositorio = new UsuarioRepositorio();
    }
    
    public Connection conectarBaseDeDatos() throws ClassNotFoundException, SQLException{
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/VitaLink","postgres","1605");
    }
    
    public EntidadSalud obtenerPorId(int id){
        try{
            ResultSet rs;
            try (Connection c = conectarBaseDeDatos()) {
                PreparedStatement ps = c.prepareStatement(
                        "SELECT "
                        + "e.id, "
                        + "e.nombre, "
                        + "c.nombre AS ciudad, "
                        + "p.nombre AS pais, "
                        + "t.nombre AS tipo, "
                        + "e.id_gerente "
                        + "FROM entidades_salud AS e "
                        + "JOIN tipos_entidades_salud AS t ON e.id_tipo = t.id "
                        + "JOIN ciudades AS c ON e.id_ciudad = c.id "
                        + "JOIN paises AS p ON c.id_pais = p.id "
                        + "WHERE e.id = ? "
                );
                ps.setInt(1, id);
                rs = ps.executeQuery();
                
                Usuario usuario = usuarioRepositorio.obtenerUsuarioId(rs.getInt("id_usuario"));
                return EntidadSaludMapper.toEntidadSalud(rs, usuario);
            }
        } catch (SQLException | ClassNotFoundException ex){
            Logger.getLogger(EntidadSaludRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
