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
 * Servlet for event detail page
 */
@WebServlet(name = "EventoDetalleServlet", urlPatterns = {"/eventos/*"})
public class EventoDetalleServlet extends HttpServlet {
    
    private static final Logger logger = LoggerFactory.getLogger(EventoDetalleServlet.class);
    private EventoService eventoService;
    private InscripcionService inscripcionService;
    private VoluntarioService voluntarioService;

    @Override
    public void init() throws ServletException {
        this.eventoService = new EventoService();
        this.inscripcionService = new InscripcionService();
        this.voluntarioService = new VoluntarioService();
        logger.info("EventoDetalleServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        // If no path info or just "/", redirect to public events list
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendRedirect(request.getContextPath() + "/eventos-publicos");
            return;
        }
        
        try {
            // Extract event ID from path
            String[] pathParts = pathInfo.split("/");
            Long eventoId = Long.parseLong(pathParts[1]);
            
            // Get event
            Optional<Evento> eventoOpt = eventoService.buscarPorId(eventoId);
            if (eventoOpt.isEmpty()) {
                request.setAttribute("error", "Evento no encontrado");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                return;
            }
            
            Evento evento = eventoOpt.get();
            request.setAttribute("evento", evento);
            
            // Check if user is logged in as volunteer
            HttpSession session = request.getSession(false);
            if (session != null) {
                UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
                if (usuario != null && usuario.getRol().name().equals("VOLUNTARIO")) {
                    // Get volunteer data
                    Optional<Voluntario> voluntarioOpt = voluntarioService.buscarPorUsuarioId(usuario.getId());
                    if (voluntarioOpt.isPresent()) {
                        Voluntario voluntario = voluntarioOpt.get();
                        request.setAttribute("voluntario", voluntario);
                        
                        // Check if already registered
                        boolean yaInscrito = inscripcionService.estaInscrito(voluntario.getId(), eventoId);
                        request.setAttribute("yaInscrito", yaInscrito);
                    }
                }
            }
            
            request.getRequestDispatcher("/WEB-INF/views/evento-detalle.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            logger.error("Invalid event ID format", e);
            request.setAttribute("error", "ID de evento inválido");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("Error loading event detail", e);
            request.setAttribute("error", "Error al cargar el evento");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
