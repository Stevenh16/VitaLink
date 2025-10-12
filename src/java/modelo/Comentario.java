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
public class Comentario {
    private final int id;
    private LocalDateTime fecha;
    private String contenido;
    private String foto;

    public Comentario(int id, LocalDateTime fecha, String contenido, String foto) {
        this.id = id;
        this.fecha = fecha;
        this.contenido = contenido;
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Comentario{" + "id=" + id + ", fecha=" + fecha + ", contenido=" + contenido + ", foto=" + foto + '}';
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
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
     * @return the foto
     */
    public String getFoto() {
        return foto;
    }

    /**
     * @param foto the foto to set
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }
    
    
}
