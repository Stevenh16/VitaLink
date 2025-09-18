/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Steven
 */
public class Usuario {
    private final Long id;
    private Rol rol;
    private String nombre;
    private String correo;
    private String contrasenia;

    public Usuario(Long id, Rol rol, String nombre, String correo, String contrasenia) {
        this.id = id;
        this.rol = rol;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasenia = contrasenia;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nombre=" + nombre + ", correo=" + correo + ", contrasenia=" + contrasenia + '}';
    }

    /**
     * @return the id
     */
    public Long getId() {
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
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the contrasenia
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * @param contrasenia the contrasenia to set
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * @return the rol
     */
    public Rol getRol() {
        return rol;
    }
    
    public Long getIdRol(){
        return switch (rol) {
            case ADMINISTRADOR -> 1L;
            case DONANTE -> 2L;
            case DONATARIO -> 3L;
            case GERENTESALUD -> 4L;
            default -> 5L;
        };
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
}
