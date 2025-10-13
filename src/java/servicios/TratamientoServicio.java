package servicios;

import modelo.Tratamiento;

import java.util.ArrayList;

public interface TratamientoServicio {
    Tratamiento obtenerPorId(int id);
    ArrayList<Tratamiento> obtenerTodosPorIdCampania(int id);
}
