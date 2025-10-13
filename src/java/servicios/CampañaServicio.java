/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import modelo.Campaña;

import java.util.ArrayList;

/**
 *
 * @author Steven
 */
public interface CampañaServicio {
    boolean crearCampaña(Campaña campaña);
    Campaña obtenerCampañaId(int id);
    boolean editarCampaña(int id, Campaña campaña);
    ArrayList<Campaña> obtenerTodas();
    boolean finalizarCampaña(int id);
    public Campaña buscarPorIdSinDonaciones(int id);
}
