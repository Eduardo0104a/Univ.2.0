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
 * Dashboard for Organization users
 */
@WebServlet(name = "OrganizacionDashboardServlet", urlPatterns = {"/app/organizacion/dashboard"})
public class OrganizacionDashboardServlet extends HttpServlet {
    
    private static final Logger logger = LoggerFactory.getLogger(OrganizacionDashboardServlet.class);
    private OrganizacionService organizacionService;
    private EventoService eventoService;

    @Override
    public void init() throws ServletException {
        this.organizacionService = new OrganizacionService();
        this.eventoService = new EventoService();
        logger.info("OrganizacionDashboardServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
        
        try {
            // Get organization data
            Optional<Organizacion> orgOpt = organizacionService.buscarPorUsuarioId(usuario.getId());
            
            if (orgOpt.isEmpty()) {
                logger.error("Organization not found for user: {}", usuario.getId());
                request.setAttribute("error", "Organización no encontrada");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                return;
            }
            
            Organizacion organizacion = orgOpt.get();
            request.setAttribute("organizacion", organizacion);
            
            // Get organization events
            request.setAttribute("eventos", eventoService.listarPorOrganizacion(organizacion.getId()));
            
            // Get pending events count
            request.setAttribute("eventosPendientes", 
                eventoService.listarPendientesPorOrganizacion(organizacion.getId()).size());
            
            request.getRequestDispatcher("/WEB-INF/views/organizacion/dashboard.jsp").forward(request, response);
            
        } catch (Exception e) {
            logger.error("Error loading organization dashboard", e);
            request.setAttribute("error", "Error al cargar el dashboard");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
