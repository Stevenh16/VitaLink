package controlador;

import modelo.EntidadSalud;
import servicios.EntidadSaludServicio;
import servicios.implementaciones.EntidadSaludServicioImplementacion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/EntidadSaludServlet")
public class EntidadSaludServlet extends HttpServlet {

    private final EntidadSaludServicio entidadSaludServicio = new EntidadSaludServicioImplementacion();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "ver";

        switch (action) {
            case "ver" -> verEntidadSalud(request, response);
            default -> response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // Por si luego agregas crear/editar
    }

    private void verEntidadSalud(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            EntidadSalud entidad = entidadSaludServicio.obtenerPorId(id);

            if (entidad != null) {
                request.setAttribute("entidadSalud", entidad);
                request.getRequestDispatcher("/entidadSalud/ver.jsp").forward(request, response);
            } else {
                response.getWriter().println("<h3>No se encontró la entidad de salud con ID " + id + ".</h3>");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El parámetro 'id' es inválido o falta.");
        }
    }
}
