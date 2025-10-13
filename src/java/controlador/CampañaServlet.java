package controlador;

import modelo.Campaña;
import modelo.Usuario;
import servicios.CampañaServicio;
import servicios.implementaciones.CampañaServicioImplementacion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

@WebServlet("/CampañaServlet")
public class CampañaServlet extends HttpServlet {

    private final CampañaServicio campañaServicio = new CampañaServicioImplementacion();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "listar";

        switch (action) {
            case "listar" -> listarCampañas(request, response);
            case "ver" -> verCampaña(request, response);
            case "finalizar" -> finalizarCampaña(request, response);
            default -> listarCampañas(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "crear" -> crearCampaña(request, response);
            default -> response.sendRedirect("CampañaServlet?action=listar");
        }
    }

    private void listarCampañas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Campaña> campañas = campañaServicio.obtenerTodas();
        request.setAttribute("campañas", campañas);
        request.getRequestDispatcher("/campañas/listar.jsp").forward(request, response);
    }

    private void verCampaña(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Campaña campaña = campañaServicio.obtenerCampañaId(id);
        if (campaña != null) {
            request.setAttribute("campaña", campaña);
            request.getRequestDispatcher("/campañas/ver.jsp").forward(request, response);
        } else {
            response.sendRedirect("CampañaServlet?action=listar");
        }
    }

    private void crearCampaña(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String titulo = request.getParameter("titulo");
        String descripcion = request.getParameter("descripcion");
        Usuario donatario = (Usuario) request.getSession().getAttribute("usuario");
        LocalDateTime fechaInicio = LocalDateTime.now();

        Campaña nuevaCampaña = new Campaña(0, titulo, descripcion, "Activa", fechaInicio, donatario);
        boolean creada = campañaServicio.crearCampaña(nuevaCampaña);

        if (creada) {
            request.setAttribute("mensaje", "Campaña creada exitosamente");
        } else {
            request.setAttribute("error", "No se pudo crear la campaña");
        }

        listarCampañas(request, response);
    }

    private void finalizarCampaña(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean finalizada = campañaServicio.finalizarCampaña(id);

        if (finalizada) {
            request.setAttribute("mensaje", "Campaña finalizada correctamente");
        } else {
            request.setAttribute("error", "No se pudo finalizar la campaña");
        }

        listarCampañas(request, response);
    }
}
