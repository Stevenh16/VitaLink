/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;

import controlador.mapper.TratamientoMapper;
import java.sql.*;
import java.util.ArrayList;
import modelo.EntidadSalud;
import modelo.Tratamiento;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Steven
 */
public class TratamientoRepositorio {
    private final EntidadSaludRepositorio entidadSaludRepositorio;

    public TratamientoRepositorio() {
        this.entidadSaludRepositorio = new EntidadSaludRepositorio();
    }
    
    public Connection conectarBaseDeDatos() throws ClassNotFoundException, SQLException{
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/VitaLink","postgres","1605");
    }
    
    public Tratamiento obtenerPorId(int id){
        try{
            ResultSet rs;
            try (Connection c = conectarBaseDeDatos()) {
                PreparedStatement ps = c.prepareStatement(
                        "SELECT "
                        + "id, "
                        + "id_entidad_salud, "
                        + "nombre, "
                        + "descripcion "
                        + "FROM tratamientos "
                        + "WHERE id = ?"
                );
                ps.setInt(1, id);
                rs = ps.executeQuery();
                
                EntidadSalud entidad = entidadSaludRepositorio.obtenerPorId(rs.getInt("id_entidad_salud"));
                return TratamientoMapper.toTratamiento(rs, entidad);
            }
        } catch (SQLException | ClassNotFoundException ex){
            Logger.getLogger(TratamientoRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ArrayList<Tratamiento> obtenerTodosPorIdCampaña(int id){
        ArrayList<Tratamiento> tratamientos = new ArrayList<>();
        try{
            ResultSet rs;
            try (Connection c = conectarBaseDeDatos(); Statement st = c.createStatement()) {
                rs = st.executeQuery("SELECT id FROM tratamientos WHERE id_campaña = "+id);
            }
            do{
                tratamientos.add(obtenerPorId(rs.getInt(id)));
            }while(rs.next());
            rs.close();
        } catch (SQLException | ClassNotFoundException ex){
            Logger.getLogger(ComentarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tratamientos;
    }
}
