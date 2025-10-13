/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios.implementaciones;

import mapper.CampañaMapper;
import modelo.*;
import repositorio.CampañaRepositorio;
import repositorio.UsuarioRepositorio;
import servicios.CampañaServicio;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Steven
 */
public class CampañaServicioImplementacion implements CampañaServicio {
    private final CampañaRepositorio campañaRepositorio;
    private final UsuarioServicioImplementacion usuarioServicio;
    private final DonacionServicioImplementacion donacionServicio;
    private final ComentarioServicioImplementacion comentarioServicio;
    private final TratamientoServicioImplementacion tratamientoServicio;

    public CampañaServicioImplementacion() {
        campañaRepositorio = new CampañaRepositorio();
        usuarioServicio = new UsuarioServicioImplementacion();
        donacionServicio = new DonacionServicioImplementacion();
        comentarioServicio = new ComentarioServicioImplementacion();
        tratamientoServicio = new TratamientoServicioImplementacion();
    }

    @Override
    public boolean crearCampaña(Campaña campaña){
        int estado = campañaRepositorio.obtenerIdEstadoPorNombre(campaña.getEstado());
        return campañaRepositorio.crear(campaña, estado);
    }

    @Override
    public Campaña obtenerCampañaId(int id) {
        return armarCampañaRespuesta(
                campañaRepositorio.buscarPorId(id),
                id, true);
    }

    @Override
    public Campaña buscarPorIdSinDonaciones(int id){
        return armarCampañaRespuesta(
                campañaRepositorio.buscarPorIdSinDonaciones(id),
                id, false);
    }

    private Campaña armarCampañaRespuesta(ResultSet rs, int id, boolean tieneDonacion){
        try {
            Usuario usuario = (Usuario) usuarioServicio.obtenerUsuarioId(rs.getInt("id_donatario"));
            ArrayList<Comentario> comentarios = comentarioServicio.obtenerTodosPorIdCampaña(id);
            ArrayList<Tratamiento> tratamientos = tratamientoServicio.obtenerTodosPorIdCampania(id);
            ArrayList<Donacion> donacion = tieneDonacion ? donacionServicio.obtenerTodosPorIdCampaña(id) : null;
            return CampañaMapper.toCampaña(
                    rs,
                    usuario, donacion, comentarios, tratamientos);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean editarCampaña(int id, Campaña campaña) {
        return false;
    }

    @Override
    public boolean existeCampañaCorreo(String correo) {
        return false;
    }

    @Override
    public ArrayList<Campaña> obtenerTodas() {
        ArrayList<Campaña> campañas = new ArrayList<>();
        try(ResultSet campañasRS = campañaRepositorio.obtenerTodas()) {
            while (campañasRS.next()) {
                campañas.add(obtenerCampañaId(campañasRS.getInt("id")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return campañas;
    }

    @Override
    public boolean finalizarCampaña(int id){
        Campaña campaña = obtenerCampañaId(id);
        if(campaña!=null){
            campaña.setFechaFin(LocalDateTime.now());
            return editarCampaña(id, campaña);
        }
        return false;
    }

    @Override
    public ArrayList<Donacion> obtenerDonaciones() {
        return null;
    }

    @Override
    public ArrayList<Comentario> obtenerComentarios() {
        return null;
    }

    @Override
    public ArrayList<Tratamiento> obtenerTratamientos() {
        return null;
    }

}
