package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 *
 * @author Steven
 */
@WebServlet(name = "ApiGatewayServlet", urlPatterns = {"/ApiGatewayServlet"})
public class ApiGatewayServlet extends HttpServlet {
    private static final String MS_DONACION = "http://localhost:8080/donacion_servicio/DonacionServlet";
    private static final String MS_CAMPANIA = "http://localhost:8080/campanias_servicio/campanias";
    private static final String MS_REPORTE = "http://localhost:8080/ReporteService/ReporteServlet";
    private static final String MS_TRATAMIENTO = "http://localhost:8080/TratamientoService/TratamientoServlet";
    private static final String MS_ENTIDAD = "http://localhost:8080/EntidadSaludService/EntidadDeSaludServlet";
    private static final String MS_USUARIO = "http://localhost:8080/UsuarioService/UsuarioServlet";
    private static final String MS_AUTH = "http://localhost:8080/AuthService/auth";
    private static final String MS_NOTIFICACION = "http://localhost:8080/notificacion/";
    private static final String MS_COMENTARIO = "http://localhost:8080/ComentarioService/ComentarioServlet";
    private static final String MS_VERIFICACION = "http://localhost:8080/VerificacionService/VerificacionServlet";
    private Map<String, Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>>> rutasGet;
    private Map<String, Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>>> rutasPost;
    private final Gson gson = new Gson();
    private static final int MAX_DEPTH = 1;

    @Override
    public void init() {
        rutasGet = new HashMap<>();
        rutasPost = new HashMap<>();
        rutasGet.put("Donacion", Map.of(
                "listar", this::listarDonaciones,
                "ver", this::verDonacion,
                "listarPorCampaña", this::listarPorCampaña
        ));
        rutasGet.put("Campaña", Map.of(
                "listar", this::listarCampañas,
                "ver", this::verCampaña
        ));
        rutasGet.put("Reporte", Map.of(
                "listar", this::listarReportes,
                "listarPorCampaña", this::listarReportesPorCampaña,
                "buscarPorId", this::buscarReportePorId
        ));
        rutasGet.put("Tratamiento", Map.of(
                "listar", this::listarTratamientos,
                "buscar", this::buscarTratamientoPorId
        ));
        rutasGet.put("EntidadSalud", Map.of(
                "listar", this::listarEntidades,
                "buscar", this::buscarEntidadDeSalud
        ));
        rutasGet.put("Usuario", Map.of(
                "listar", this::listarUsuarios,
                "buscar", this::buscarUsuario,
                "listarRoles", this::verListaRoles,
                "buscarUsuarioPorRol", this::buscarUsuarioPorRol,
                "verificarUsuarioPorNombreYrol", this::verificarUsuarioPorNombreYrol
        ));
        rutasGet.put("Auth", Map.of());
        rutasGet.put("Notificacion", Map.of());
        rutasGet.put("Comentario", Map.of(
            "listarTodos", this::listarTodosComentarios,
            "buscarPorId", this::buscarComentarioPorId,
            "buscarPorCampaña", this::buscarComentariosPorCampaña
        ));
        rutasGet.put("Verificacion", Map.of(
            "listarTodos", this::listarTodasVerificaciones,
            "buscarPorId", this::buscarPorId,
            "buscarPorCampaña", this::buscarPorCampaña
        ));
        rutasPost.put("Donacion", Map.of(
                "crear", this::crearDonacion
        ));
        rutasPost.put("Campaña", Map.of(
                "crear", this::crearCampaña,
                "actualizar", this::finalizarCampaña
        ));
        rutasPost.put("Reporte", Map.of(
                "crear", this::crearReporte,
                "actualizar", this::actualizarReporte,
                "eliminar", this::eliminarReporte
        ));
        rutasPost.put("Tratamiento", Map.of(
                "crear", this::crearTratamiento,
                "actualizar", this::actualizarTratamiento,
                "eliminar", this::eliminarTratamiento
        ));
        rutasPost.put("EntidadSalud", Map.of(
                "crear", this::crearEntidadDeSalud,
                "actualizar", this::actualizarEntidadDeSalud,
                "eliminar", this::eliminarEntidadDeSalud
        ));
        rutasPost.put("Usuario", Map.of(
                "crear", this::crearUsuario,
                "actualizar", this::actualizarUsuario,
                "eliminar", this::eliminarUsuario
        ));
        rutasPost.put("Auth", Map.of(
                "register", this::register,
                "login", this::login,
                "updatedPassword", this::updatedPassword
        ));
        rutasPost.put("Notificacion", Map.of());
        rutasPost.put("Comentario", Map.of(
            "guardar", this::guardarComentario,
            "actualizar", this::actualizarComentario,
            "eliminar", this::eliminarComentario
        ));
        rutasPost.put("Verificacion", Map.of(
            "guardar", this::guardarVerificacion,
            "actualizar", this::actualizarVerificacion,
            "eliminar", this::eliminarVerificacion
        ));
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servicio = request.getParameter("servicio");
        String metodo = request.getParameter("metodo");
        ejecutarRuta(rutasGet, servicio, metodo, request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servicio = request.getParameter("servicio");
        String metodo = request.getParameter("metodo");
        ejecutarRuta(rutasPost, servicio, metodo, request, response);
    }
  
    private void crearReporte(HttpServletRequest request, HttpServletResponse response) {
        String url = MS_REPORTE + "?action=crear";
        Map<String, String> params = new HashMap<>();
        params.put("titulo",request.getParameter("titulo"));
        params.put("descripcion",request.getParameter("descripcion"));
        params.put("fechaCreacion",request.getParameter("fechaCreacion"));
        params.put("campaña",request.getParameter("campaña"));
        params.put("usuario",request.getParameter("usuario"));
        params.put("tipoReporte",request.getParameter("tipoReporte"));
        params.put("estado",request.getParameter("estado"));
        params.put("comentario",request.getParameter("comentario"));
        proxyPost(url, params, response);
    }

    private void actualizarReporte(HttpServletRequest request, HttpServletResponse response) {
        String url = MS_REPORTE + "?action=actualizar";
        Map<String, String> params = new HashMap<>();
        params.put("id",request.getParameter("id"));
        params.put("titulo",request.getParameter("titulo"));
        params.put("descripcion",request.getParameter("descripcion"));
        params.put("fechaCreacion",request.getParameter("fechaCreacion"));
        params.put("campaña",request.getParameter("campaña"));
        params.put("usuario",request.getParameter("usuario"));
        params.put("tipoReporte",request.getParameter("tipoReporte"));
        params.put("estado",request.getParameter("estado"));
        params.put("comentario",request.getParameter("comentario"));
        proxyPost(url, params, response);
    }

    private void eliminarReporte(HttpServletRequest request, HttpServletResponse response) {
        String url = MS_REPORTE + "?action=eliminar";
        Map<String, String> params = new HashMap<>();
        params.put("id",request.getParameter("id"));
        proxyPost(url,params,response);
    }

    private void buscarReportePorId(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String url = MS_REPORTE + "?action=buscar&id="+id;
        proxyGet(url, response);
    }

    private void listarTratamientos(HttpServletRequest request, HttpServletResponse response) {
        String url = MS_TRATAMIENTO + "?action=listar";
        proxyGet(url, response);
    }

    private void buscarTratamientoPorId(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String url = MS_TRATAMIENTO + "?action=buscar&id=" + id;
        proxyGet(url, response);
    }

    private void listarEntidades(HttpServletRequest request, HttpServletResponse response) {
        String url = MS_ENTIDAD + "?action=listar";
        proxyGet(url, response);
    }

    private void buscarEntidadDeSalud(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String url = MS_ENTIDAD + "?action=buscar&id=" + id;
        proxyGet(url, response);
    }

    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) {
        String url = MS_USUARIO + "?action=listar";
        proxyGet(url, response);
    }

    private void buscarUsuario(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String url = MS_USUARIO + "?action=buscar&id=" + id;
        proxyGet(url, response);
    }

    private void verListaRoles(HttpServletRequest request, HttpServletResponse response) {
        String url = MS_USUARIO + "?action=listarRoles";
        proxyGet(url, response);
    }

    private void buscarUsuarioPorRol(HttpServletRequest request, HttpServletResponse response) {
        String rol = request.getParameter("rol");
        String url = MS_USUARIO + "?action=buscarUsuarioPorRol&rol="+ rol;
        proxyGet(url, response);
    }

    private void verificarUsuarioPorNombreYrol(HttpServletRequest request, HttpServletResponse response) {
        String nombre = request.getParameter("nombre");
        String rol = request.getParameter("rol");
        String url = MS_USUARIO + "?action=verificarUsuarioPorNombreYrol&nombre=" + nombre + "&rol=" + rol;
        proxyGet(url, response);
    }

    private void crearTratamiento(HttpServletRequest request, HttpServletResponse response) {
        String url = MS_TRATAMIENTO + "?action=crear";
        Map<String, String> params = new HashMap<>();
        params.put("descripcion",request.getParameter("descripcion"));
        params.put("estado",request.getParameter("estado"));
        params.put("idEntidadDeSalud",request.getParameter("idEntidadDeSalud"));
        params.put("usuario",request.getParameter("usuario"));
        params.put("comentarios",request.getParameter("comentarios"));
        proxyPost(url, params, response);
    }

    private void actualizarTratamiento(HttpServletRequest request, HttpServletResponse response) {
        String url = MS_TRATAMIENTO + "?action=actualizar";
        Map<String, String> params = new HashMap<>();
        params.put("id",request.getParameter("id"));
        params.put("descripcion",request.getParameter("descripcion"));
        params.put("estado",request.getParameter("estado"));
        params.put("fechaInicio",request.getParameter("fechaInicio"));
        params.put("fechaFin",request.getParameter("fechaFin"));
        params.put("idEntidadDeSalud",request.getParameter("idEntidadDeSalud"));
        params.put("usuario",request.getParameter("usuario"));
        params.put("comentarios",request.getParameter("comentarios"));
        proxyPost(url, params, response);
    }

    private void eliminarTratamiento(HttpServletRequest request, HttpServletResponse response) {
        String url = MS_TRATAMIENTO + "?action=eliminar";
        Map<String, String> params = new HashMap<>();
        params.put("id",request.getParameter("id"));
        proxyPost(url, params, response);
    }

    private void crearEntidadDeSalud(HttpServletRequest request, HttpServletResponse response) {
        String url = MS_ENTIDAD + "?action=crear";
        Map<String, String> params = new HashMap<>();
        params.put("nombre",request.getParameter("nombre"));
        params.put("pais",request.getParameter("pais"));
        params.put("ciudad",request.getParameter("ciudad"));
        params.put("tipo",request.getParameter("tipo"));
        params.put("idUsuario",request.getParameter("idUsuario"));
        proxyPost(url, params, response);
    }

    private void actualizarEntidadDeSalud(HttpServletRequest request, HttpServletResponse response) {
        String url = MS_ENTIDAD + "?action=actualizar";
        Map<String, String> params = new HashMap<>();
        params.put("id",request.getParameter("id"));
        params.put("nombre",request.getParameter("nombre"));
        params.put("pais",request.getParameter("pais"));
        params.put("ciudad",request.getParameter("ciudad"));
        params.put("tipo",request.getParameter("tipo"));
        params.put("idUsuario",request.getParameter("idUsuario"));
        proxyPost(url, params, response);
    }

    private void eliminarEntidadDeSalud(HttpServletRequest request, HttpServletResponse response) {
        String url = MS_ENTIDAD + "?action=eliminar";
        Map<String, String> params = new HashMap<>();
        params.put("id",request.getParameter("id"));
        proxyPost(url, params, response);
    }

    private void crearUsuario(HttpServletRequest request, HttpServletResponse response) {
        String url = MS_USUARIO + "?action=crear";
        Map<String, String> params = new HashMap<>();
        params.put("nombre",request.getParameter("nombre"));
        params.put("correo",request.getParameter("correo"));
        params.put("contrasenia",request.getParameter("contrasenia"));
        params.put("rol",request.getParameter("rol"));
        proxyPost(url, params, response);
    }

    private void actualizarUsuario(HttpServletRequest request, HttpServletResponse response) {
        String url = MS_USUARIO + "?action=actualizar";
        Map<String, String> params = new HashMap<>();
        params.put("id",request.getParameter("id"));
        params.put("nombre",request.getParameter("nombre"));
        params.put("correo",request.getParameter("correo"));
        params.put("contrasenia",request.getParameter("contrasenia"));
        params.put("rol",request.getParameter("rol"));
        proxyPost(url, params, response);
    }

    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String url = MS_USUARIO + "?action=eliminar&id=" + id;
        proxyGet(url, response);
    }

    private void crearDonacion(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = new HashMap<>();
        params.put("tipo",request.getParameter("tipo"));
        params.put("cantidad",request.getParameter("cantidad"));
        params.put("idCampaña",request.getParameter("idCampaña"));
        params.put("idDonante",request.getParameter("idDonante"));
        String url = MS_DONACION + "?action=crear";
        proxyPost(url,params,response);
    }

    private void crearCampaña(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = new HashMap<>();
        params.put("titulo",request.getParameter("titulo"));
        params.put("descripcion",request.getParameter("descripcion"));
        params.put("idUsuario",request.getParameter("idUsuario"));
        String url = MS_CAMPANIA + "?action=crear";
        proxyPost(url,params,response);
    }

    private void finalizarCampaña(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = new HashMap<>();
        String id = request.getParameter("id");
        String url = MS_CAMPANIA + "?action=finalizar&id="+id;
        proxyPost(url,params,response);
    }

    private void listarDonaciones(HttpServletRequest request, HttpServletResponse response) {
        String url = MS_DONACION + "?action=listar";
        proxyGet(url, response);
    }

    private void verDonacion(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String url = MS_DONACION + "?action=ver&id=" + id;
        proxyGet(url, response);
    }

    private void listarPorCampaña(HttpServletRequest request, HttpServletResponse response) {
        String idCampania = request.getParameter("idCampaña");
        String url = MS_DONACION + "?action=listarPorCampaña&idCampaña=" + idCampania;
        proxyGet(url, response);
    }

    private void verCampaña(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String url = MS_CAMPANIA + "?action=ver&id=" + id;
        proxyGet(url, response);
    }

    private void listarCampañas(HttpServletRequest request, HttpServletResponse response) {
        String url = MS_CAMPANIA + "?action=listar";
        proxyGet(url, response);
    }

    private void listarReportes(HttpServletRequest request, HttpServletResponse response) {
        String url = MS_REPORTE + "?action=listar";
        proxyGet(url, response);
    }

    private void listarReportesPorCampaña(HttpServletRequest request, HttpServletResponse response) {
        String url = MS_REPORTE + "?action=listarPorCampaña&idCampaña=" + request.getParameter("idCampaña") ;
        proxyGet(url, response);
    }

    private void guardarVerificacion(HttpServletRequest t, HttpServletResponse u) {
        String url = MS_VERIFICACION + "?action=guardar";
        Map<String, String> params = new HashMap<>();
        params.put("campañaId",t.getParameter("campañaId"));
        params.put("usuarioId",t.getParameter("usuarioId"));
        params.put("comentario",t.getParameter("comentario"));
        proxyPost(url, params, u);
    }

    private void actualizarVerificacion(HttpServletRequest t, HttpServletResponse u) {
        String url = MS_VERIFICACION + "?action=actualizar";
        Map<String, String> params = new HashMap<>();
        params.put("id",t.getParameter("id"));
        params.put("comentario",t.getParameter("comentario"));
        proxyPost(url, params, u);
    }

    private void eliminarVerificacion(HttpServletRequest t, HttpServletResponse u) {
        String url = MS_VERIFICACION + "?action=eliminar";
        Map<String, String> params = new HashMap<>();
        params.put("id",t.getParameter("id"));
        proxyPost(url, params, u);
    }

    private void listarTodasVerificaciones(HttpServletRequest t, HttpServletResponse u) {
        String url = MS_VERIFICACION + "?action=listarTodos";
        proxyGet(url, u);
    }

    private void buscarPorId(HttpServletRequest t, HttpServletResponse u) {
        String id = t.getParameter("id");
        String url = MS_VERIFICACION + "?action=buscarPorId&id=" + id;
        proxyGet(url, u);
    }

    private void buscarPorCampaña(HttpServletRequest t, HttpServletResponse u) {
        String id = t.getParameter("camapañaId");
        String url = MS_VERIFICACION + "?action=buscarPorCampaña&campañaId=" + id;
        proxyGet(url, u);
    }
    
    private void ejecutarRuta(Map<String, Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>>> rutas, String servicio, String metodo, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (servicio == null || metodo == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetros faltantes");
            return;
        }
        Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>> metodos = rutas.get(servicio);
        if (metodos == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Servicio no válido");
            return;
        }
        BiConsumer<HttpServletRequest, HttpServletResponse> handler = metodos.get(metodo);
        if (handler == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Método no válido");
            return;
        }
        handler.accept(request, response);
    }
    
    private void proxyGet(String url, HttpServletResponse response) {
        try {
            // fetch raw body (string)
            String body = httpGetJson(url);
            if (body == null) {
                response.sendError(502, "Bad gateway - empty response from microservice");
                return;
            }

            // try to parse as JSON and enrich (basic)
            String enriched = tryEnrichJson(body, MAX_DEPTH);

            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(enriched);
        } catch (IOException e) {
            try {
                response.sendError(500, "Error en API Gateway GET: " + e.getMessage());
            } catch (IOException ex) {}
        }
    }

    private void proxyPost(String url, Map<String, String> params, HttpServletResponse response) {
        try {
            StringBuilder form = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (form.length() > 0) form.append("&");
                form.append(entry.getKey()).append("=").append(entry.getValue());
            }
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(form.toString()))
                    .build();
            HttpResponse<String> msResponse =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(msResponse.body());
        } catch (IOException | InterruptedException e) {
            try {
                response.sendError(500, "Error en API Gateway POST: " + e.getMessage());
            } catch (IOException ex) {}
        }
    }

    private void listarTodosComentarios(HttpServletRequest t, HttpServletResponse u) {
        String url = MS_COMENTARIO + "?action=listarTodos";
        proxyGet(url, u);
    }

    private void buscarComentarioPorId(HttpServletRequest t, HttpServletResponse u) {
        String id = t.getParameter("id");
        String url = MS_COMENTARIO + "?action=buscarPorId&id=" + id;
        proxyGet(url, u);
    }

    private void buscarComentariosPorCampaña(HttpServletRequest t, HttpServletResponse u) {
        String id = t.getParameter("campañaId");
        String url = MS_COMENTARIO + "?action=buscarPorCampaña&campañaId=" + id;
        proxyGet(url, u);
    }
    
    private void guardarComentario(HttpServletRequest t, HttpServletResponse u) {
        String url = MS_COMENTARIO + "?action=guardar";
        Map<String, String> params = new HashMap<>();
        params.put("contenido",t.getParameter("contenido"));
        params.put("campañaId",t.getParameter("campañaId"));
        params.put("usuarioId",t.getParameter("usuarioId"));
        proxyPost(url, params, u);
    }

    private void actualizarComentario(HttpServletRequest t, HttpServletResponse u) {
        String url = MS_COMENTARIO + "?action=actualizar";
        Map<String, String> params = new HashMap<>();
        params.put("id",t.getParameter("id"));
        params.put("contenido",t.getParameter("contenido"));
        params.put("campañaId",t.getParameter("campañaId"));
        params.put("usuarioId",t.getParameter("usuarioId"));
        proxyPost(url, params, u);
    }

    private void eliminarComentario(HttpServletRequest t, HttpServletResponse u) {
        String url = MS_COMENTARIO + "?action=eliminar";
        Map<String, String> params = new HashMap<>();
        params.put("id",t.getParameter("id"));
        proxyPost(url, params, u);
    }

    private void register(HttpServletRequest t, HttpServletResponse u) {
        String url = MS_AUTH + "?action=register";
        System.out.println(t.getParameter("rol"));
        Map<String, String> params = new HashMap<>();
        params.put("username",t.getParameter("username"));
        params.put("password",t.getParameter("password"));
        params.put("rol",t.getParameter("rol"));
        proxyPost(url, params, u);
    }

    private void login(HttpServletRequest t, HttpServletResponse u) {
        String url = MS_AUTH + "?action=login";
        Map<String, String> params = new HashMap<>();
        params.put("username",t.getParameter("username"));
        params.put("password",t.getParameter("password"));
        proxyPost(url, params, u);
    }

    private void updatedPassword(HttpServletRequest t, HttpServletResponse u) {
        String url = MS_AUTH + "?action=updatePassword";
        Map<String, String> params = new HashMap<>();
        params.put("username",t.getParameter("username"));
        params.put("password",t.getParameter("password"));
        params.put("oldPassword",t.getParameter("oldPassword"));
        params.put("newPassword",t.getParameter("newPassword"));
        proxyPost(url, params, u);
    }
    
    private String httpGetJson(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(false);
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                return br.lines().collect(Collectors.joining());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ------------------- ENRICHMENT (BASIC) --------------------
    private String tryEnrichJson(String body, int maxDepth) {
        try {
            JsonElement el = JsonParser.parseString(body);
            JsonElement enriched = enrichJsonElement(el, maxDepth);
            return gson.toJson(enriched);
        } catch (Exception ex) {
            // not JSON or parse error -> return original body
            return body;
        }
    }

    private JsonElement enrichJsonElement(JsonElement el, int depth) {
        if (depth <= 0 || el == null || el.isJsonNull()) return el;

        if (el.isJsonArray()) {
            JsonArray arr = el.getAsJsonArray();
            JsonArray out = new JsonArray();
            for (JsonElement item : arr) {
                out.add(enrichJsonElement(item, depth));
            }
            return out;
        }

        if (el.isJsonObject()) {
            JsonObject obj = el.getAsJsonObject();
            JsonObject out = new JsonObject();

            for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
                String key = entry.getKey();
                JsonElement value = entry.getValue();

                // First, recurse inside nested objects/arrays
                if (value.isJsonObject() || value.isJsonArray()) {
                    out.add(key, enrichJsonElement(value, depth - 1));
                    continue;
                }

                // If primitive, check if it looks like an ID field
                if (value.isJsonPrimitive()) {
                    String keyLower = key.toLowerCase();

                    Integer id = null;
                    if (value.getAsJsonPrimitive().isNumber()) {
                        try { id = value.getAsInt(); } catch (Exception ignore) {}
                    } else if (value.getAsJsonPrimitive().isString()) {
                        String s = value.getAsString().trim();
                        try { id = Integer.parseInt(s); } catch (Exception ignore) {}
                    }

                    // map key -> ms url template
                    String msUrl = msUrlForFieldName(keyLower, id);

                    if (msUrl != null && id != null) {
                        // call microservice and replace value with the fetched json (one level deeper)
                        try {
                            String fetched = httpGetJson(msUrl);
                            if (fetched != null) {
                                JsonElement fetchedEl = JsonParser.parseString(fetched);
                                // enrich fetchedEl one level less to avoid deep recursion
                                out.add(key, enrichJsonElement(fetchedEl, depth - 1));
                                continue;
                            } else {
                                // couldn't fetch, keep original primitive
                                out.add(key, value);
                                continue;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            out.add(key, value);
                            continue;
                        }
                    }
                }

                // default: copy original
                out.add(key, value);
            }
            return out;
        }

        return el;
    }

    /**
     * Devuelve la URL completa para obtener el objeto referido por el campo
     * (usa la convención ?action=buscar&id=... ). Retorna null si no hay ms
     * conocido para ese campo.
     */
    private String msUrlForFieldName(String keyLower, Integer id) {
        if (id == null) return null;

        // CAMPANIAS
        if (keyLower.equals("campaña") || keyLower.equals("campana") || keyLower.equals("campania") ||
            keyLower.equals("campañaid") || keyLower.equals("campanaid") || keyLower.equals("idcampaña") ||
            keyLower.equals("idcampana") || keyLower.equals("idcampania") || keyLower.equals("id_campaña") ||
            keyLower.equals("id_campana") || keyLower.equals("id_campania") || keyLower.equals("campana_id") ||
            keyLower.equals("idcampania") || keyLower.equals("id_campania")) {
            return MS_CAMPANIA + "?action=ver&id=" + id;
        }

        // USUARIOS
        if (keyLower.equals("usuario") || keyLower.equals("usuarioid") || keyLower.equals("idusuario") ||
            keyLower.equals("id_usuario") || keyLower.equals("usuario_id") || keyLower.equals("id_usuario_gerente") ||
            keyLower.equals("usuariom") || keyLower.equals("usuario_m") || keyLower.equals("usuariom")) {
            return MS_USUARIO + "?action=buscar&id=" + id;
        }

        // ENTIDADES
        if (keyLower.equals("entidad") || keyLower.equals("identidad") || keyLower.equals("identidaddesalud") ||
            keyLower.equals("identidaddesalud") || keyLower.equals("identidad_desalud") ||
            keyLower.equals("identidaddesalud") || keyLower.equals("identidad") ||
            keyLower.equals("identidad") || keyLower.equals("identidad") ||
            keyLower.equals("identidad") || keyLower.equals("identidad") ||
            keyLower.equals("identidad") || keyLower.equals("identidad") ||
            keyLower.equals("id_entidad") || keyLower.equals("identidad")) {
            return MS_ENTIDAD + "?action=buscar&id=" + id;
        }

        // TRATAMIENTOS (si hay campos que refieran a tratamientos)
        if (keyLower.equals("tratamiento") || keyLower.equals("id_tratamiento") || keyLower.equals("idtratamiento")) {
            return MS_TRATAMIENTO + "?action=buscar&id=" + id;
        }

        // REPORTES (si alguien referencia un reporte)
        if (keyLower.equals("reporte") || keyLower.equals("id_reporte") || keyLower.equals("idreporte")) {
            return MS_REPORTE + "?action=buscar&id=" + id;
        }

        // comentarios - usualmente no referimos a comentarios por id en otros objetos,
        // pero si lo haces, podríamos mapearlo:
        if (keyLower.equals("comentarioid") || keyLower.equals("id_comentario") || keyLower.equals("comentario")) {
            // no mapping by default
            return null;
        }
        
        if(keyLower.equals("donacionId") || keyLower.equals("donacion") || keyLower.equals("donacion_id") || keyLower.equals("idDonacion")){
            return MS_DONACION + "?action=ver&id=" + id;
        }
        
        if(keyLower.equals("verificacionId") || keyLower.equals("verificaicon") || keyLower.equals("verificacion_id") || keyLower.equals("idVerificacion")){
            return MS_VERIFICACION + "?action=buscarPorId&id=" + id;
        }

        // por defecto, no sabemos qué microservicio usar
        return null;
    }

}
