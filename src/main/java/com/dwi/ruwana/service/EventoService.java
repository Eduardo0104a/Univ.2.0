package com.dwi.ruwana.service;

import com.dwi.ruwana.dao.EventoDAO;
import com.dwi.ruwana.dao.impl.EventoDAOImpl;
import com.dwi.ruwana.model.Evento;
import com.dwi.ruwana.model.Usuario;
import com.dwi.ruwana.model.enums.EstadoEvento;
import com.dwi.ruwana.util.JPAUtil;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for Evento entity
 */
public class EventoService {
    
    private static final Logger logger = LoggerFactory.getLogger(EventoService.class);
    private final EventoDAO eventoDAO;

    public EventoService() {
        this.eventoDAO = new EventoDAOImpl();
    }

    /**
     * Create a new event
     */
    public Evento crear(Evento evento) {
        return JPAUtil.executeInTransactionWithResult(em -> {
            eventoDAO.setEntityManager(em);
            
            // Validate dates
            if (evento.getFechaInicio().isAfter(evento.getFechaFin())) {
                throw new IllegalArgumentException("Start date must be before end date");
            }
            
            // Set initial state
            if (evento.getEstado() == null) {
                evento.setEstado(EstadoEvento.PENDIENTE_APROBACION);
            }
            
            // If has capacity limit, set available spots
            if (evento.getCuposMaximos() != null && evento.getCuposMaximos() > 0) {
                if (evento.getCuposDisponibles() == null) {
                    evento.setCuposDisponibles(evento.getCuposMaximos());
                }
            }
            
            Evento saved = eventoDAO.save(evento);
            logger.info("Event created: {} - {}", saved.getId(), saved.getNombre());
            return saved;
        });
    }

    /**
     * Find event by ID
     */
    public Optional<Evento> buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            eventoDAO.setEntityManager(em);
            return eventoDAO.findByIdWithOrganizacion(id);
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Find all available events
     */
    public List<Evento> listarDisponibles() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            eventoDAO.setEntityManager(em);
            return eventoDAO.findEventosDisponibles();
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Find upcoming events
     */
    public List<Evento> listarProximos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            eventoDAO.setEntityManager(em);
            return eventoDAO.findEventosProximos();
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Find events by organization
     */
    public List<Evento> listarPorOrganizacion(Long organizacionId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            eventoDAO.setEntityManager(em);
            return eventoDAO.findByOrganizacionId(organizacionId);
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Find events by status
     */
    public List<Evento> listarPorEstado(EstadoEvento estado) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            eventoDAO.setEntityManager(em);
            return eventoDAO.findByEstado(estado);
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Find pending approval events
     */
    public List<Evento> listarPendientesAprobacion() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            eventoDAO.setEntityManager(em);
            return eventoDAO.findPendientesAprobacion();
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Find approved events
     */
    public List<Evento> listarAprobados() {
        return listarPorEstado(EstadoEvento.APROBADO);
    }

    /**
     * Find pending events by organization
     */
    public List<Evento> listarPendientesPorOrganizacion(Long organizacionId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            eventoDAO.setEntityManager(em);
            return eventoDAO.findByOrganizacionId(organizacionId).stream()
                    .filter(e -> e.getEstado() == EstadoEvento.PENDIENTE_APROBACION)
                    .toList();
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Search events by name
     */
    public List<Evento> buscarPorNombre(String nombre) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            eventoDAO.setEntityManager(em);
            return eventoDAO.searchByNombre(nombre);
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Search events by location
     */
    public List<Evento> buscarPorLugar(String lugar) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            eventoDAO.setEntityManager(em);
            return eventoDAO.findByLugar(lugar);
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Search events by date range
     */
    public List<Evento> buscarPorFechas(LocalDate desde, LocalDate hasta) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            eventoDAO.setEntityManager(em);
            return eventoDAO.findByFechaRange(desde, hasta);
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Update event
     */
    public Evento actualizar(Evento evento) {
        return JPAUtil.executeInTransactionWithResult(em -> {
            eventoDAO.setEntityManager(em);
            
            // Validate dates
            if (evento.getFechaInicio().isAfter(evento.getFechaFin())) {
                throw new IllegalArgumentException("Start date must be before end date");
            }
            
            Evento updated = eventoDAO.update(evento);
            logger.info("Event updated: {}", updated.getId());
            return updated;
        });
    }

    /**
     * Approve event (admin action)
     */
    public void aprobar(Long eventoId, Usuario admin) {
        JPAUtil.executeInTransaction(em -> {
            eventoDAO.setEntityManager(em);
            
            Evento evento = eventoDAO.findById(eventoId)
                    .orElseThrow(() -> new IllegalArgumentException("Event not found"));
            
            if (!admin.isAdmin()) {
                throw new IllegalArgumentException("Only administrators can approve events");
            }
            
            evento.aprobar(admin);
            eventoDAO.update(evento);
            
            logger.info("Event approved: {} by admin {}", eventoId, admin.getEmail());
        });
    }

    /**
     * Reject event (admin action)
     */
    public void rechazar(Long eventoId, Usuario admin, String motivo) {
        JPAUtil.executeInTransaction(em -> {
            eventoDAO.setEntityManager(em);
            
            Evento evento = eventoDAO.findById(eventoId)
                    .orElseThrow(() -> new IllegalArgumentException("Event not found"));
            
            if (!admin.isAdmin()) {
                throw new IllegalArgumentException("Only administrators can reject events");
            }
            
            evento.rechazar(admin, motivo);
            eventoDAO.update(evento);
            
            logger.info("Event rejected: {} by admin {}", eventoId, admin.getEmail());
        });
    }

    /**
     * Cancel event
     */
    public void cancelar(Long eventoId) {
        JPAUtil.executeInTransaction(em -> {
            eventoDAO.setEntityManager(em);
            
            Evento evento = eventoDAO.findById(eventoId)
                    .orElseThrow(() -> new IllegalArgumentException("Event not found"));
            
            evento.setEstado(EstadoEvento.CANCELADO);
            eventoDAO.update(evento);
            
            logger.info("Event cancelled: {}", eventoId);
        });
    }

    /**
     * Mark event as in progress
     */
    public void iniciar(Long eventoId) {
        JPAUtil.executeInTransaction(em -> {
            eventoDAO.setEntityManager(em);
            
            Evento evento = eventoDAO.findById(eventoId)
                    .orElseThrow(() -> new IllegalArgumentException("Event not found"));
            
            if (!evento.isAprobado()) {
                throw new IllegalStateException("Only approved events can be started");
            }
            
            evento.setEstado(EstadoEvento.EN_CURSO);
            eventoDAO.update(evento);
            
            logger.info("Event started: {}", eventoId);
        });
    }

    /**
     * Mark event as finished
     */
    public void finalizar(Long eventoId) {
        JPAUtil.executeInTransaction(em -> {
            eventoDAO.setEntityManager(em);
            
            Evento evento = eventoDAO.findById(eventoId)
                    .orElseThrow(() -> new IllegalArgumentException("Event not found"));
            
            evento.setEstado(EstadoEvento.FINALIZADO);
            eventoDAO.update(evento);
            
            logger.info("Event finished: {}", eventoId);
        });
    }

    /**
     * Check if event has available capacity
     */
    public boolean tieneCuposDisponibles(Long eventoId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            eventoDAO.setEntityManager(em);
            Evento evento = eventoDAO.findById(eventoId)
                    .orElseThrow(() -> new IllegalArgumentException("Event not found"));
            return evento.tieneCuposDisponibles();
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Delete event
     */
    public void eliminar(Long eventoId) {
        JPAUtil.executeInTransaction(em -> {
            eventoDAO.setEntityManager(em);
            
            Evento evento = eventoDAO.findById(eventoId)
                    .orElseThrow(() -> new IllegalArgumentException("Event not found"));
            
            // Only allow deletion of draft or pending events
            if (evento.getEstado() != EstadoEvento.BORRADOR && 
                evento.getEstado() != EstadoEvento.PENDIENTE_APROBACION) {
                throw new IllegalStateException("Cannot delete event in current state");
            }
            
            eventoDAO.delete(evento);
            logger.info("Event deleted: {}", eventoId);
        });
    }
}
