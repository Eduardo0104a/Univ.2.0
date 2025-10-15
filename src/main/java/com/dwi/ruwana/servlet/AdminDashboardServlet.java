package com.dwi.ruwana.servlet;

import com.dwi.ruwana.service.EventoService;
import com.dwi.ruwana.service.OrganizacionService;
import com.dwi.ruwana.service.UsuarioService;
import com.dwi.ruwana.service.VoluntarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Dashboard for Admin users
 */
@WebServlet(name = "AdminDashboardServlet", urlPatterns = {"/app/admin/dashboard"})
public class AdminDashboardServlet extends HttpServlet {
    
    private static final Logger logger = LoggerFactory.getLogger(AdminDashboardServlet.class);
    private OrganizacionService organizacionService;
    private EventoService eventoService;
    private VoluntarioService voluntarioService;
    private UsuarioService usuarioService;

    @Override
    public void init() throws ServletException {
        this.organizacionService = new OrganizacionService();
        this.eventoService = new EventoService();
        this.voluntarioService = new VoluntarioService();
        this.usuarioService = new UsuarioService();
        logger.info("AdminDashboardServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // Get pending organizations
            request.setAttribute("organizacionesPendientes", 
                organizacionService.listarPendientes());
            
            // Get pending events
            request.setAttribute("eventosPendientes", 
                eventoService.listarPendientesAprobacion());
            
            // Get statistics
            request.setAttribute("totalOrganizaciones", 
                organizacionService.listarAprobadas().size());
            
            request.setAttribute("totalVoluntarios", 
                voluntarioService.listarTodos().size());
            
            request.setAttribute("totalEventos", 
                eventoService.listarAprobados().size());
            
            request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response);
            
        } catch (Exception e) {
            logger.error("Error loading admin dashboard", e);
            request.setAttribute("error", "Error al cargar el dashboard");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
