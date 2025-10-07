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
public class Verificacion {
    private final int id;
    private LocalDateTime fecha;
    private Campaña campaña;

    public Verificacion(int id, LocalDateTime fecha, Campaña campaña) {
        this.id = id;
        this.fecha = fecha;
        this.campaña = campaña;
    }

    public Campaña getCampaña() {
        return campaña;
    }

    public void setCampaña(Campaña campaña) {
        this.campaña = campaña;
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
    
    
}
