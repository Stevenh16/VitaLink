package controlador;

import modelo.Tratamiento;
import servicios.TratamientoServicio;
import servicios.implementaciones.TratamientoServicioImplementacion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "TratamientoServlet", urlPatterns = {"/tratamientos"})
public class TratamientoServlet extends HttpServlet {

    private TratamientoServicio tratamientoServicio;

    @Override
    public void init() throws ServletException {
        super.init();
        tratamientoServicio = new TratamientoServicioImplementacion();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        String idCampaniaParam = request.getParameter("idCampania");

        if (idParam != null) {
            // Obtener un tratamiento por su ID
            int id = Integer.parseInt(idParam);
            Tratamiento tratamiento = tratamientoServicio.obtenerPorId(id);

            if (tratamiento != null) {
                request.setAttribute("tratamiento", tratamiento);
                request.getRequestDispatcher("detalle-tratamiento.jsp").forward(request, response);
            } else {
                request.setAttribute("mensaje", "Tratamiento no encontrado");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }

        } else if (idCampaniaParam != null) {
            // Obtener todos los tratamientos asociados a una campaña
            int idCampania = Integer.parseInt(idCampaniaParam);
            ArrayList<Tratamiento> tratamientos = tratamientoServicio.obtenerTodosPorIdCampania(idCampania);

            request.setAttribute("tratamientos", tratamientos);
            request.getRequestDispatcher("lista-tratamientos.jsp").forward(request, response);

        } else {
            // Si no se proporcionan parámetros
            request.setAttribute("mensaje", "Debe proporcionar un id o un idCampania");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
