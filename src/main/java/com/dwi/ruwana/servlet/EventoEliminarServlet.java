package com.dwi.ruwana.servlet;

import com.dwi.ruwana.dto.UsuarioDTO;
import com.dwi.ruwana.model.Evento;
import com.dwi.ruwana.model.Organizacion;
import com.dwi.ruwana.service.EventoService;
import com.dwi.ruwana.service.OrganizacionService;
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
 * Servlet for event deletion
 */
@WebServlet(name = "EventoEliminarServlet", urlPatterns = {"/app/organizacion/evento/eliminar"})
public class EventoEliminarServlet extends HttpServlet {
    
    private static final Logger logger = LoggerFactory.getLogger(EventoEliminarServlet.class);
    private EventoService eventoService;
    private OrganizacionService organizacionService;

    @Override
    public void init() throws ServletException {
        this.eventoService = new EventoService();
        this.organizacionService = new OrganizacionService();
        logger.info("EventoEliminarServlet initialized");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
        
        try {
            Long eventoId = Long.parseLong(request.getParameter("eventoId"));
            
            // Get organization
            Optional<Organizacion> organizacionOpt = organizacionService.buscarPorUsuarioId(usuario.getId());
            if (organizacionOpt.isEmpty()) {
                session.setAttribute("error", "Datos de organización no encontrados");
                response.sendRedirect(request.getContextPath() + "/app/organizacion/eventos");
                return;
            }
            
            Organizacion organizacion = organizacionOpt.get();
            
            // Get event
            Optional<Evento> eventoOpt = eventoService.buscarPorId(eventoId);
            if (eventoOpt.isEmpty()) {
                session.setAttribute("error", "Evento no encontrado");
                response.sendRedirect(request.getContextPath() + "/app/organizacion/eventos");
                return;
            }
            
            Evento evento = eventoOpt.get();
            
            // Verify ownership
            if (!evento.getOrganizacion().getId().equals(organizacion.getId())) {
                session.setAttribute("error", "No tienes permiso para eliminar este evento");
                response.sendRedirect(request.getContextPath() + "/app/organizacion/eventos");
                return;
            }
            
            // Delete event
            eventoService.eliminar(eventoId);
            
            session.setAttribute("success", "Evento eliminado exitosamente");
            response.sendRedirect(request.getContextPath() + "/app/organizacion/eventos");
            
        } catch (IllegalStateException e) {
            logger.error("Cannot delete event", e);
            session.setAttribute("error", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/app/organizacion/eventos");
        } catch (Exception e) {
            logger.error("Error deleting event", e);
            session.setAttribute("error", "Error al eliminar el evento");
            response.sendRedirect(request.getContextPath() + "/app/organizacion/eventos");
        }
    }
}
