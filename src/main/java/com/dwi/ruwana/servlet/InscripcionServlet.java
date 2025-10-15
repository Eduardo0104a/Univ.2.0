package com.dwi.ruwana.servlet;

import com.dwi.ruwana.dto.UsuarioDTO;
import com.dwi.ruwana.model.Evento;
import com.dwi.ruwana.model.Voluntario;
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
import java.util.Optional;

/**
 * Servlet for event registration
 */
@WebServlet(name = "InscripcionServlet", urlPatterns = {"/inscripcion"})
public class InscripcionServlet extends HttpServlet {
    
    private static final Logger logger = LoggerFactory.getLogger(InscripcionServlet.class);
    private InscripcionService inscripcionService;
    private EventoService eventoService;
    private VoluntarioService voluntarioService;

    @Override
    public void init() throws ServletException {
        this.inscripcionService = new InscripcionService();
        this.eventoService = new EventoService();
        this.voluntarioService = new VoluntarioService();
        logger.info("InscripcionServlet initialized");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login?redirect=" + 
                request.getContextPath() + "/eventos");
            return;
        }
        
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
        
        // Only volunteers can register
        if (!usuario.getRol().name().equals("VOLUNTARIO")) {
            request.setAttribute("error", "Solo los voluntarios pueden inscribirse en eventos");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return;
        }
        
        try {
            Long eventoId = Long.parseLong(request.getParameter("eventoId"));
            
            // Get volunteer
            Optional<Voluntario> voluntarioOpt = voluntarioService.buscarPorUsuarioId(usuario.getId());
            if (voluntarioOpt.isEmpty()) {
                request.setAttribute("error", "Datos de voluntario no encontrados");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                return;
            }
            
            Voluntario voluntario = voluntarioOpt.get();
            
            // Get event
            Optional<Evento> eventoOpt = eventoService.buscarPorId(eventoId);
            if (eventoOpt.isEmpty()) {
                request.setAttribute("error", "Evento no encontrado");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                return;
            }
            
            Evento evento = eventoOpt.get();
            
            // Check if already registered
            if (inscripcionService.estaInscrito(voluntario.getId(), eventoId)) {
                session.setAttribute("error", "Ya estás inscrito en este evento");
                response.sendRedirect(request.getContextPath() + "/eventos/" + eventoId);
                return;
            }
            
            // Check if event has available spots
            if (!evento.tieneCuposDisponibles()) {
                session.setAttribute("error", "Este evento ya no tiene cupos disponibles");
                response.sendRedirect(request.getContextPath() + "/eventos/" + eventoId);
                return;
            }
            
            // Register
            inscripcionService.inscribir(voluntario.getId(), eventoId);
            
            session.setAttribute("success", "¡Te has inscrito exitosamente en el evento!");
            response.sendRedirect(request.getContextPath() + "/app/voluntario/dashboard");
            
        } catch (NumberFormatException e) {
            logger.error("Invalid event ID", e);
            request.setAttribute("error", "ID de evento inválido");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        } catch (IllegalStateException e) {
            logger.error("Registration validation error", e);
            session.setAttribute("error", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/eventos");
        } catch (Exception e) {
            logger.error("Error registering for event", e);
            request.setAttribute("error", "Error al procesar la inscripción");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
