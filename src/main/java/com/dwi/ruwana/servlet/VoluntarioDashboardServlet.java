package com.dwi.ruwana.servlet;

import com.dwi.ruwana.dto.UsuarioDTO;
import com.dwi.ruwana.service.EventoService;
import com.dwi.ruwana.service.InscripcionService;
import com.dwi.ruwana.service.VoluntarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Dashboard for Volunteer users
 */
@WebServlet(name = "VoluntarioDashboardServlet", urlPatterns = {"/app/voluntario/dashboard"})
public class VoluntarioDashboardServlet extends HttpServlet {
    
    private static final Logger logger = LoggerFactory.getLogger(VoluntarioDashboardServlet.class);
    private EventoService eventoService;
    private InscripcionService inscripcionService;
    private VoluntarioService voluntarioService;

    @Override
    public void init() throws ServletException {
        this.eventoService = new EventoService();
        this.inscripcionService = new InscripcionService();
        this.voluntarioService = new VoluntarioService();
        logger.info("VoluntarioDashboardServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
        
        try {
            // Get volunteer data
            voluntarioService.buscarPorUsuarioId(usuario.getId()).ifPresent(voluntario -> {
                request.setAttribute("voluntario", voluntario);
                
                // Get volunteer statistics
                VoluntarioService.VoluntarioStats stats = voluntarioService.obtenerEstadisticas(voluntario.getId());
                request.setAttribute("stats", stats);
            });
            
            // Get available events
            request.setAttribute("eventosDisponibles", eventoService.listarDisponibles());
            
            // Get volunteer's registrations
            request.getSession().getAttribute("voluntario");
            
            request.getRequestDispatcher("/WEB-INF/views/voluntario/dashboard.jsp").forward(request, response);
            
        } catch (Exception e) {
            logger.error("Error loading volunteer dashboard", e);
            request.setAttribute("error", "Error al cargar el dashboard");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
