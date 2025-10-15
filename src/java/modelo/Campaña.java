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
    private String estado;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Usuario donatario;
    private ArrayList<Donacion> donaciones;
    private ArrayList<Comentario> comentarios;
    private ArrayList<Tratamiento> tratamientos;

    public Campaña(int id, String titulo, String descripcion, String estado, LocalDateTime fechaInicio, Usuario donatario) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.donatario = donatario;
        donaciones = new ArrayList<>();
        comentarios = new ArrayList<>();
        tratamientos = new ArrayList<>();
    }

    public Campaña(int id, String titulo, String descripcion, String estado, LocalDateTime fechaInicio, LocalDateTime fechaFin, Usuario donatario) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.donatario = donatario;
        donaciones = new ArrayList<>();
        comentarios = new ArrayList<>();
        tratamientos = new ArrayList<>();
    }
    
    @Override
    public String toString() {
        return "Campa\u00f1a{" + "id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", estado=" + estado + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", donatario=" + donatario + ", donaciones=" + donaciones + ", comentarios=" + comentarios + ", tratamientos=" + tratamientos + '}';
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
