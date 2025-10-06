/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Steven
 */
public class Campaña {
    private final int id;
    private String titulo;
    private String descripcion;
    private LocalDateTime fecha;
    private Usuario donatario;
    private Verificacion verificacion;
    private ArrayList<Donacion> donaciones;
    private ArrayList<Comentario> comentarios;
    private ArrayList<Tratamiento> tratamientos;

    public Campaña(int id, String titulo, String descripcion, LocalDateTime fecha, Usuario donatario) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.donatario = donatario;
        donaciones = new ArrayList<>();
        comentarios = new ArrayList<>();
        tratamientos = new ArrayList<>();
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the fecha
     */
    public LocalDateTime getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the donatario
     */
    public Usuario getDonatario() {
        return donatario;
    }

    /**
     * @param donatario the donatario to set
     */
    public void setDonatario(Usuario donatario) {
        this.donatario = donatario;
    }

    /**
     * @return the verificacion
     */
    public Verificacion getVerificacion() {
        return verificacion;
    }

    /**
     * @param verificacion the verificacion to set
     */
    public void setVerificacion(Verificacion verificacion) {
        this.verificacion = verificacion;
    }

    /**
     * @return the donaciones
     */
    public ArrayList<Donacion> getDonaciones() {
        return donaciones;
    }

    /**
     * @param donaciones the donaciones to set
     */
    public void setDonaciones(ArrayList<Donacion> donaciones) {
        this.donaciones = donaciones;
    }

    /**
     * @return the comentarios
     */
    public ArrayList<Comentario> getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios the comentarios to set
     */
    public void setComentarios(ArrayList<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    /**
     * @return the tratamientos
     */
    public ArrayList<Tratamiento> getTratamientos() {
        return tratamientos;
    }

    /**
     * @param tratamientos the tratamientos to set
     */
    public void setTratamientos(ArrayList<Tratamiento> tratamientos) {
        this.tratamientos = tratamientos;
    }
    
    
}
