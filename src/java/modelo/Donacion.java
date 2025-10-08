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
public class Donacion {
    private final int id;
    private String tipo;
    private String cantidad;
    private LocalDateTime fecha;
    private Campaña campaña;
    private Usuario donante;

    public Donacion(int id, String tipo, String cantidad, LocalDateTime fecha, Campaña campaña, Usuario donante) {
        this.id = id;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.campaña = campaña;
        this.donante = donante;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the cantidad
     */
    public String getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
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

    public Usuario getDonante() {
        return donante;
    }

    public void setDonante(Usuario donante) {
        this.donante = donante;
    }
    
    
}
