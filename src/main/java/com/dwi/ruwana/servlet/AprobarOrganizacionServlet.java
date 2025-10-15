package com.dwi.ruwana.servlet;

import com.dwi.ruwana.model.Organizacion;
import com.dwi.ruwana.model.enums.EstadoAprobacion;
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
 * Servlet for approving/rejecting organizations
 */
@WebServlet(name = "AprobarOrganizacionServlet", urlPatterns = {"/app/admin/organizacion/aprobar"})
public class AprobarOrganizacionServlet extends HttpServlet {
    
    private static final Logger logger = LoggerFactory.getLogger(AprobarOrganizacionServlet.class);
    private OrganizacionService organizacionService;

    @Override
    public void init() throws ServletException {
        this.organizacionService = new OrganizacionService();
        logger.info("AprobarOrganizacionServlet initialized");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        try {
            Long organizacionId = Long.parseLong(request.getParameter("organizacionId"));
            String accion = request.getParameter("accion");
            String motivo = request.getParameter("motivo");
            
            Optional<Organizacion> organizacionOpt = organizacionService.buscarPorId(organizacionId);
            if (organizacionOpt.isEmpty()) {
                session.setAttribute("error", "Organización no encontrada");
                response.sendRedirect(request.getContextPath() + "/app/admin/organizaciones");
                return;
            }
            
            Organizacion organizacion = organizacionOpt.get();
            
            if ("aprobar".equals(accion)) {
                organizacion.setEstadoAprobacion(EstadoAprobacion.APROBADO);
                organizacion.setMotivoRechazo(null);
                organizacionService.actualizar(organizacion);
                session.setAttribute("success", "Organización aprobada exitosamente");
            } else if ("rechazar".equals(accion)) {
                if (motivo == null || motivo.trim().isEmpty()) {
                    session.setAttribute("error", "Debes proporcionar un motivo de rechazo");
                    response.sendRedirect(request.getContextPath() + "/app/admin/organizaciones");
                    return;
                }
                organizacion.setEstadoAprobacion(EstadoAprobacion.RECHAZADO);
                organizacion.setMotivoRechazo(motivo);
                organizacionService.actualizar(organizacion);
                session.setAttribute("success", "Organización rechazada");
            }
            
            response.sendRedirect(request.getContextPath() + "/app/admin/organizaciones");
            
        } catch (Exception e) {
            logger.error("Error processing organization approval", e);
            session.setAttribute("error", "Error al procesar la solicitud");
            response.sendRedirect(request.getContextPath() + "/app/admin/organizaciones");
        }
    }
}
