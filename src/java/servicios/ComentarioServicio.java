package servicios;

import modelo.Comentario;

import java.util.ArrayList;

public interface ComentarioServicio {
    ArrayList<Comentario> obtenerTodosPorIdCampaña(int id);
}
