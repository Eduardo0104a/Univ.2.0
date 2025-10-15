package com.dwi.ruwana.servlet;

import com.dwi.ruwana.model.Evento;
import com.dwi.ruwana.model.enums.EstadoEvento;
import com.dwi.ruwana.service.EventoService;
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
 * Servlet for approving/rejecting events
 */
@WebServlet(name = "AprobarEventoServlet", urlPatterns = {"/app/admin/evento/aprobar"})
public class AprobarEventoServlet extends HttpServlet {
    
    private static final Logger logger = LoggerFactory.getLogger(AprobarEventoServlet.class);
    private EventoService eventoService;

    @Override
    public void init() throws ServletException {
        this.eventoService = new EventoService();
        logger.info("AprobarEventoServlet initialized");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        try {
            Long eventoId = Long.parseLong(request.getParameter("eventoId"));
            String accion = request.getParameter("accion");
            
            Optional<Evento> eventoOpt = eventoService.buscarPorId(eventoId);
            if (eventoOpt.isEmpty()) {
                session.setAttribute("error", "Evento no encontrado");
                response.sendRedirect(request.getContextPath() + "/app/admin/dashboard");
                return;
            }
            
            Evento evento = eventoOpt.get();
            
            if ("aprobar".equals(accion)) {
                evento.setEstado(EstadoEvento.APROBADO);
                eventoService.actualizar(evento);
                session.setAttribute("success", "Evento aprobado exitosamente. Ahora es visible públicamente.");
            } else if ("rechazar".equals(accion)) {
                evento.setEstado(EstadoEvento.RECHAZADO);
                eventoService.actualizar(evento);
                session.setAttribute("success", "Evento rechazado");
            }
            
            response.sendRedirect(request.getContextPath() + "/app/admin/dashboard");
            
        } catch (Exception e) {
            logger.error("Error processing event approval", e);
            session.setAttribute("error", "Error al procesar la solicitud");
            response.sendRedirect(request.getContextPath() + "/app/admin/dashboard");
        }
    }
}
