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
public class Notificacion {
    private final int id;
    private String mensaje;
    private LocalDateTime fecha;
    private String titulo;
    private final Campaña campaña;

    public Notificacion(int id, String mensaje, LocalDateTime fecha, String titulo, Campaña campaña) {
        this.id = id;
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.titulo = titulo;
        this.campaña = campaña;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Campaña getCampaña() {
        return campaña;
    }

    public int getId() {
        return id;
    }
  
}
