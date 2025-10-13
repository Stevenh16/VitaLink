package servicios;

import modelo.Donacion;

import java.util.ArrayList;

public interface DonacionServicio {
    int obtenerIdTipoPorNombre(String tipo);
    boolean crear(Donacion donacion);
    Donacion obtenerPorId(int id);
    Donacion obtenerPorIdSinCampaña(int id);
    ArrayList<Donacion> obtenerTodos();
    ArrayList<Donacion> obtenerTodosPorIdCampaña(int id);
}
