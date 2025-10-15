package com.dwi.ruwana.servlet;

import com.dwi.ruwana.dto.UsuarioDTO;
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
 * Servlet for organization's event management
 */
@WebServlet(name = "EventosOrganizacionServlet", urlPatterns = {"/app/organizacion/eventos"})
public class EventosOrganizacionServlet extends HttpServlet {
    
    private static final Logger logger = LoggerFactory.getLogger(EventosOrganizacionServlet.class);
    private EventoService eventoService;
    private OrganizacionService organizacionService;

    @Override
    public void init() throws ServletException {
        this.eventoService = new EventoService();
        this.organizacionService = new OrganizacionService();
        logger.info("EventosOrganizacionServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
        
        try {
            // Get organization
            Optional<Organizacion> organizacionOpt = organizacionService.buscarPorUsuarioId(usuario.getId());
            if (organizacionOpt.isEmpty()) {
                request.setAttribute("error", "Datos de organización no encontrados");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                return;
            }
            
            Organizacion organizacion = organizacionOpt.get();
            
            // Get organization's events
            request.setAttribute("eventos", eventoService.listarPorOrganizacion(organizacion.getId()));
            request.setAttribute("eventosPendientes", eventoService.listarPendientesPorOrganizacion(organizacion.getId()));
            request.setAttribute("organizacion", organizacion);
            
            request.getRequestDispatcher("/WEB-INF/views/organizacion/eventos.jsp").forward(request, response);
            
        } catch (Exception e) {
            logger.error("Error loading organization events", e);
            request.setAttribute("error", "Error al cargar los eventos");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
