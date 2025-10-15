package com.dwi.ruwana.servlet;

import com.dwi.ruwana.service.EventoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Servlet for public event listing
 */
@WebServlet(name = "EventosPublicosServlet", urlPatterns = {"/eventos", "/eventos-publicos"})
public class EventosPublicosServlet extends HttpServlet {
    
    private static final Logger logger = LoggerFactory.getLogger(EventosPublicosServlet.class);
    private EventoService eventoService;

    @Override
    public void init() throws ServletException {
        this.eventoService = new EventoService();
        logger.info("EventosPublicosServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            String searchQuery = request.getParameter("buscar");
            String lugar = request.getParameter("lugar");
            
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                // Search by name or description
                request.setAttribute("eventos", eventoService.buscarPorNombre(searchQuery));
                request.setAttribute("searchQuery", searchQuery);
            } else if (lugar != null && !lugar.trim().isEmpty()) {
                // Search by location
                request.setAttribute("eventos", eventoService.buscarPorLugar(lugar));
                request.setAttribute("lugar", lugar);
            } else {
                // List all available events
                request.setAttribute("eventos", eventoService.listarDisponibles());
            }
            
            request.getRequestDispatcher("/WEB-INF/views/eventos-publicos.jsp").forward(request, response);
            
        } catch (Exception e) {
            logger.error("Error loading public events", e);
            request.setAttribute("error", "Error al cargar los eventos");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
