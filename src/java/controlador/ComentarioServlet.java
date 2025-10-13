package controlador;

import modelo.Comentario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import servicios.implementaciones.ComentarioServicioImplementacion;

@WebServlet("/ComentarioServlet")
public class ComentarioServlet extends HttpServlet {

    private final ComentarioServicioImplementacion comentarioServicio = new ComentarioServicioImplementacion();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "listar";

        switch (action) {
            case "listar" -> listarPorCampaña(request, response);
            default -> response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // si luego agregas crear/editar, puedes manejarlo aquí
    }

    private void listarPorCampaña(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idCampaña = Integer.parseInt(request.getParameter("idCampaña"));
            ArrayList<Comentario> comentarios = comentarioServicio.obtenerTodosPorIdCampaña(idCampaña);

            request.setAttribute("comentarios", comentarios);
            request.setAttribute("idCampaña", idCampaña);

            request.getRequestDispatcher("/comentarios/listar.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El parámetro idCampaña es inválido o falta.");
        }
    }
}
