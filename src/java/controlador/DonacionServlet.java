package controlador;

import modelo.Campaña;
import modelo.Donacion;
import modelo.Usuario;
import servicios.DonacionServicio;
import servicios.implementaciones.DonacionServicioImplementacion;
import servicios.implementaciones.CampañaServicioImplementacion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import servicios.implementaciones.UsuarioServicioImplementacion;

@WebServlet("/DonacionServlet")
public class DonacionServlet extends HttpServlet {
    int i = 0;
    private final DonacionServicio donacionServicio = new DonacionServicioImplementacion();
    private final CampañaServicioImplementacion campañaServicio = new CampañaServicioImplementacion();
    private final UsuarioServicioImplementacion usuarioServicio = new UsuarioServicioImplementacion();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "listar";

        switch (action) {
            case "listar" -> listarDonaciones(request, response);
            case "ver" -> verDonacion(request, response);
            case "listarPorCampaña" -> listarPorCampaña(request, response);
            default -> response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "crear" -> crearDonacion(request, response);
            default -> response.sendRedirect("DonacionServlet?action=listar");
        }
    }

    private void listarDonaciones(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Donacion> donaciones = donacionServicio.obtenerTodos();
        request.setAttribute("donaciones", donaciones);
        request.getRequestDispatcher("/donaciones/listar.jsp").forward(request, response);
    }

    private void listarPorCampaña(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idCampaña = Integer.parseInt(request.getParameter("idCampaña"));
            ArrayList<Donacion> donaciones = donacionServicio.obtenerTodosPorIdCampaña(idCampaña);

            request.setAttribute("donaciones", donaciones);
            request.setAttribute("idCampaña", idCampaña);
            request.getRequestDispatcher("/donaciones/listarPorCampaña.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El parámetro idCampaña es inválido o falta.");
        }
    }

    private void verDonacion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Donacion donacion = donacionServicio.obtenerPorId(id);

            if (donacion != null) {
                request.setAttribute("donacion", donacion);
                request.getRequestDispatcher("/donaciones/ver.jsp").forward(request, response);
            } else {
                response.sendRedirect("DonacionServlet?action=listar");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de donación inválido.");
        }
    }

    private void crearDonacion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tipo = request.getParameter("tipo");
        String cantidad = request.getParameter("cantidad");
        int idCampaña = Integer.parseInt(request.getParameter("idCampaña"));
        int idUsuario = Integer.parseInt(request.getParameter("idDonante"));
        

        getServletContext().log("ID CAMPAÑA: " + idCampaña);
        getServletContext().log("ID USUARIO: " + idUsuario);
        Campaña campaña = campañaServicio.obtenerCampañaId(idCampaña);
        Usuario donante = usuarioServicio.obtenerUsuarioId(idUsuario);

        Donacion donacion = new Donacion(
                ++i,
                tipo,
                cantidad,
                LocalDateTime.now(),
                campaña,
                donante
        );

        boolean creada = donacionServicio.crear(donacion);

        if (creada) {
            request.setAttribute("mensaje", "Donación registrada correctamente.");
        } else {
            request.setAttribute("error", "No se pudo registrar la donación.");
        }

        listarDonaciones(request, response);
    }
}
