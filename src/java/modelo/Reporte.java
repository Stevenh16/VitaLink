/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDateTime;

/**
 *
 * @author Steven
 */
public class Reporte {
    private final int id;
    private String contenido;
    private LocalDateTime fecha;
    private Campaña campaña;
    private Usuario usuario;

    public Reporte(int id, String contenido, LocalDateTime fecha, Campaña campaña, Usuario usuario) {
        this.id = id;
        this.contenido = contenido;
        this.fecha = fecha;
        this.campaña = campaña;
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Reporte{" + "id=" + id + ", contenido=" + contenido + ", fecha=" + fecha + ", campa\u00f1a=" + campaña + ", usuario=" + usuario + '}';
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the contenido
     */
    public String getContenido() {
        return contenido;
    }

    /**
     * @param contenido the contenido to set
     */
    public void setContenido(String contenido) {
        this.contenido = contenido;
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
     * @return the campaña
     */
    public Campaña getCampaña() {
        return campaña;
    }

    /**
     * @param campaña the campaña to set
     */
    public void setCampaña(Campaña campaña) {
        this.campaña = campaña;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
}
