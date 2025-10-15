package com.dwi.ruwana.servlet;

import com.dwi.ruwana.model.Usuario;
import com.dwi.ruwana.model.Voluntario;
import com.dwi.ruwana.model.enums.RolUsuario;
import com.dwi.ruwana.service.CatalogoService;
import com.dwi.ruwana.service.VoluntarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Servlet for volunteer registration
 */
@WebServlet(name = "RegistroVoluntarioServlet", urlPatterns = {"/registro/voluntario"})
public class RegistroVoluntarioServlet extends HttpServlet {
    
    private static final Logger logger = LoggerFactory.getLogger(RegistroVoluntarioServlet.class);
    private VoluntarioService voluntarioService;
    private CatalogoService catalogoService;

    @Override
    public void init() throws ServletException {
        this.voluntarioService = new VoluntarioService();
        this.catalogoService = new CatalogoService();
        logger.info("RegistroVoluntarioServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Load catalogs for form dropdowns
        request.setAttribute("generos", catalogoService.listarGenerosActivos());
        request.setAttribute("estadosCiviles", catalogoService.listarEstadosCivilesActivos());
        request.setAttribute("tiposDocumento", catalogoService.listarTiposDocumentoActivos());
        request.setAttribute("gradosInstruccion", catalogoService.listarGradosInstruccionActivos());
        request.setAttribute("centrosEstudios", catalogoService.listarCentrosEstudiosActivos());
        
        request.getRequestDispatcher("/WEB-INF/views/registro-voluntario.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // Create Usuario
            Usuario usuario = new Usuario();
            usuario.setEmail(request.getParameter("email"));
            usuario.setNombres(request.getParameter("nombres"));
            usuario.setApellidoPaterno(request.getParameter("apellidoPaterno"));
            usuario.setApellidoMaterno(request.getParameter("apellidoMaterno"));
            usuario.setRol(RolUsuario.VOLUNTARIO);
            usuario.setEstadoActivo(true);
            usuario.setEstadoVerificado(false);
            
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            
            // Validate passwords match
            if (!password.equals(confirmPassword)) {
                request.setAttribute("error", "Las contraseñas no coinciden");
                loadCatalogs(request);
                request.getRequestDispatcher("/WEB-INF/views/registro-voluntario.jsp").forward(request, response);
                return;
            }
            
            // Create Voluntario
            Voluntario voluntario = new Voluntario();
            
            // Personal data
            String fechaNacStr = request.getParameter("fechaNacimiento");
            if (fechaNacStr != null && !fechaNacStr.isEmpty()) {
                voluntario.setFechaNacimiento(LocalDate.parse(fechaNacStr));
            }
            
            String generoId = request.getParameter("generoId");
            if (generoId != null && !generoId.isEmpty()) {
                catalogoService.buscarGeneroPorId(Integer.parseInt(generoId))
                    .ifPresent(voluntario::setGenero);
            }
            
            String estadoCivilId = request.getParameter("estadoCivilId");
            if (estadoCivilId != null && !estadoCivilId.isEmpty()) {
                catalogoService.buscarEstadoCivilPorId(Integer.parseInt(estadoCivilId))
                    .ifPresent(voluntario::setEstadoCivil);
            }
            
            voluntario.setNacionalidad(request.getParameter("nacionalidad"));
            
            String tipoDocId = request.getParameter("tipoDocumentoId");
            if (tipoDocId != null && !tipoDocId.isEmpty()) {
                catalogoService.buscarTipoDocumentoPorId(Integer.parseInt(tipoDocId))
                    .ifPresent(voluntario::setTipoDocumento);
            }
            
            voluntario.setNumeroDocumento(request.getParameter("numeroDocumento"));
            
            // Educational data
            String gradoId = request.getParameter("gradoInstruccionId");
            if (gradoId != null && !gradoId.isEmpty()) {
                catalogoService.buscarGradoInstruccionPorId(Integer.parseInt(gradoId))
                    .ifPresent(voluntario::setGradoInstruccion);
            }
            
            String centroId = request.getParameter("centroEstudiosId");
            if (centroId != null && !centroId.isEmpty()) {
                catalogoService.buscarCentroEstudiosPorId(Integer.parseInt(centroId))
                    .ifPresent(voluntario::setCentroEstudios);
            }
            
            voluntario.setNombreCentroEstudios(request.getParameter("nombreCentroEstudios"));
            voluntario.setCorreoInstitucional(request.getParameter("correoInstitucional"));
            voluntario.setCarrera(request.getParameter("carrera"));
            
            // Register volunteer
            Voluntario registrado = voluntarioService.registrar(usuario, password, voluntario);
            
            logger.info("Volunteer registered successfully: {}", usuario.getEmail());
            
            // Redirect to login with success message
            response.sendRedirect(request.getContextPath() + "/login?registro=success");
            
        } catch (IllegalArgumentException e) {
            logger.warn("Registration validation error: {}", e.getMessage());
            request.setAttribute("error", e.getMessage());
            loadCatalogs(request);
            request.getRequestDispatcher("/WEB-INF/views/registro-voluntario.jsp").forward(request, response);
            
        } catch (Exception e) {
            logger.error("Error registering volunteer", e);
            request.setAttribute("error", "Error al registrar voluntario. Intenta nuevamente.");
            loadCatalogs(request);
            request.getRequestDispatcher("/WEB-INF/views/registro-voluntario.jsp").forward(request, response);
        }
    }
    
    private void loadCatalogs(HttpServletRequest request) {
        request.setAttribute("generos", catalogoService.listarGenerosActivos());
        request.setAttribute("estadosCiviles", catalogoService.listarEstadosCivilesActivos());
        request.setAttribute("tiposDocumento", catalogoService.listarTiposDocumentoActivos());
        request.setAttribute("gradosInstruccion", catalogoService.listarGradosInstruccionActivos());
        request.setAttribute("centrosEstudios", catalogoService.listarCentrosEstudiosActivos());
    }
}
