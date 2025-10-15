package com.dwi.ruwana.servlet;

import com.dwi.ruwana.dto.UsuarioDTO;
import com.dwi.ruwana.model.Evento;
import com.dwi.ruwana.model.Organizacion;
import com.dwi.ruwana.model.enums.EstadoEvento;
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
import java.time.LocalDate;
import java.util.Optional;

/**
 * Servlet for event creation and editing
 */
@WebServlet(name = "EventoFormServlet", urlPatterns = {
    "/app/organizacion/evento/nuevo",
    "/app/organizacion/evento/editar/*"
})
public class EventoFormServlet extends HttpServlet {
    
    private static final Logger logger = LoggerFactory.getLogger(EventoFormServlet.class);
    private EventoService eventoService;
    private OrganizacionService organizacionService;

    @Override
    public void init() throws ServletException {
        this.eventoService = new EventoService();
        this.organizacionService = new OrganizacionService();
        logger.info("EventoFormServlet initialized");
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
            request.setAttribute("organizacion", organizacion);
            
            // Check if editing
            String pathInfo = request.getPathInfo();
            if (pathInfo != null && pathInfo.startsWith("/editar/")) {
                String[] parts = pathInfo.split("/");
                if (parts.length >= 3) {
                    Long eventoId = Long.parseLong(parts[2]);
                    
                    Optional<Evento> eventoOpt = eventoService.buscarPorId(eventoId);
                    if (eventoOpt.isEmpty()) {
                        request.setAttribute("error", "Evento no encontrado");
                        request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                        return;
                    }
                    
                    Evento evento = eventoOpt.get();
                    
                    // Verify ownership
                    if (!evento.getOrganizacion().getId().equals(organizacion.getId())) {
                        request.setAttribute("error", "No tienes permiso para editar este evento");
                        request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                        return;
                    }
                    
                    request.setAttribute("evento", evento);
                }
            }
            
            request.getRequestDispatcher("/WEB-INF/views/organizacion/evento-form.jsp").forward(request, response);
            
        } catch (Exception e) {
            logger.error("Error loading event form", e);
            request.setAttribute("error", "Error al cargar el formulario");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
            
            // Get form data
            String eventoIdStr = request.getParameter("eventoId");
            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");
            String informacionPrograma = request.getParameter("informacionPrograma");
            String fechaInicioStr = request.getParameter("fechaInicio");
            String fechaFinStr = request.getParameter("fechaFin");
            String horario = request.getParameter("horario");
            String lugar = request.getParameter("lugar");
            String direccion = request.getParameter("direccion");
            String telefonoContacto = request.getParameter("telefonoContacto");
            String cuposMaximosStr = request.getParameter("cuposMaximos");
            
            // Validate
            if (nombre == null || nombre.trim().isEmpty() ||
                fechaInicioStr == null || fechaFinStr == null ||
                lugar == null || lugar.trim().isEmpty()) {
                session.setAttribute("error", "Por favor completa todos los campos obligatorios");
                response.sendRedirect(request.getContextPath() + request.getServletPath());
                return;
            }
            
            Evento evento;
            boolean isNew = false;
            
            if (eventoIdStr != null && !eventoIdStr.isEmpty()) {
                // Edit existing event
                Long eventoId = Long.parseLong(eventoIdStr);
                Optional<Evento> eventoOpt = eventoService.buscarPorId(eventoId);
                
                if (eventoOpt.isEmpty()) {
                    session.setAttribute("error", "Evento no encontrado");
                    response.sendRedirect(request.getContextPath() + "/app/organizacion/eventos");
                    return;
                }
                
                evento = eventoOpt.get();
                
                // Verify ownership
                if (!evento.getOrganizacion().getId().equals(organizacion.getId())) {
                    session.setAttribute("error", "No tienes permiso para editar este evento");
                    response.sendRedirect(request.getContextPath() + "/app/organizacion/eventos");
                    return;
                }
            } else {
                // Create new event
                evento = new Evento();
                evento.setOrganizacion(organizacion);
                evento.setEstado(EstadoEvento.PENDIENTE_APROBACION);
                isNew = true;
            }
            
            // Set event data
            evento.setNombre(nombre);
            evento.setDescripcion(descripcion);
            evento.setInformacionPrograma(informacionPrograma);
            evento.setFechaInicio(LocalDate.parse(fechaInicioStr));
            evento.setFechaFin(LocalDate.parse(fechaFinStr));
            evento.setHorario(horario);
            evento.setLugar(lugar);
            evento.setDireccion(direccion);
            evento.setTelefonoContacto(telefonoContacto);
            
            // Handle cupos
            if (cuposMaximosStr != null && !cuposMaximosStr.trim().isEmpty()) {
                int cuposMaximos = Integer.parseInt(cuposMaximosStr);
                if (cuposMaximos > 0) {
                    evento.setCuposMaximos(cuposMaximos);
                    if (isNew) {
                        evento.setCuposDisponibles(cuposMaximos);
                    }
                }
            }
            
            // Save
            if (isNew) {
                eventoService.crear(evento);
                session.setAttribute("success", "Evento creado exitosamente. Está pendiente de aprobación.");
            } else {
                eventoService.actualizar(evento);
                session.setAttribute("success", "Evento actualizado exitosamente.");
            }
            
            response.sendRedirect(request.getContextPath() + "/app/organizacion/eventos");
            
        } catch (Exception e) {
            logger.error("Error saving event", e);
            session.setAttribute("error", "Error al guardar el evento: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/app/organizacion/eventos");
        }
    }
}
