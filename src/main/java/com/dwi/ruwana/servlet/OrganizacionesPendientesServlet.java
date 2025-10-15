package com.dwi.ruwana.servlet;

import com.dwi.ruwana.service.OrganizacionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Servlet for managing pending organizations
 */
@WebServlet(name = "OrganizacionesPendientesServlet", urlPatterns = {"/app/admin/organizaciones"})
public class OrganizacionesPendientesServlet extends HttpServlet {
    
    private static final Logger logger = LoggerFactory.getLogger(OrganizacionesPendientesServlet.class);
    private OrganizacionService organizacionService;

    @Override
    public void init() throws ServletException {
        this.organizacionService = new OrganizacionService();
        logger.info("OrganizacionesPendientesServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // Get pending organizations
            request.setAttribute("organizacionesPendientes", organizacionService.listarPendientes());
            
            // Get all organizations
            request.setAttribute("todasOrganizaciones", organizacionService.listarTodas());
            
            request.getRequestDispatcher("/WEB-INF/views/admin/organizaciones.jsp").forward(request, response);
            
        } catch (Exception e) {
            logger.error("Error loading organizations", e);
            request.setAttribute("error", "Error al cargar las organizaciones");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
