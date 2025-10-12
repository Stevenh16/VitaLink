/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Steven
 */
public class Tratamiento {
    private final int id;
    private String nombre;
    private String descripcion;
    private EntidadSalud entidadSalud;

    public Tratamiento(int id, String nombre, String descripcion, EntidadSalud entidadSalud) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.entidadSalud = entidadSalud;
    }

    @Override
    public String toString() {
        return "Tratamiento{" + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", entidadSalud=" + entidadSalud + '}';
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public EntidadSalud getEntidadSalud() {
        return entidadSalud;
    }

    public void setEntidadSalud(EntidadSalud entidadSalud) {
        this.entidadSalud = entidadSalud;
    }
}
