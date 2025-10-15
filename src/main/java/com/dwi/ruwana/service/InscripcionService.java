package com.dwi.ruwana.service;

import com.dwi.ruwana.dao.EventoDAO;
import com.dwi.ruwana.dao.InscripcionDAO;
import com.dwi.ruwana.dao.VoluntarioDAO;
import com.dwi.ruwana.dao.impl.EventoDAOImpl;
import com.dwi.ruwana.dao.impl.InscripcionDAOImpl;
import com.dwi.ruwana.dao.impl.VoluntarioDAOImpl;
import com.dwi.ruwana.model.Evento;
import com.dwi.ruwana.model.Inscripcion;
import com.dwi.ruwana.model.Voluntario;
import com.dwi.ruwana.model.enums.EstadoEvento;
import com.dwi.ruwana.model.enums.EstadoInscripcion;
import com.dwi.ruwana.util.JPAUtil;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for Inscripcion entity
 * Handles volunteer registration to events with capacity management
 */
public class InscripcionService {
    
    private static final Logger logger = LoggerFactory.getLogger(InscripcionService.class);
    private final InscripcionDAO inscripcionDAO;
    private final EventoDAO eventoDAO;
    private final VoluntarioDAO voluntarioDAO;

    public InscripcionService() {
        this.inscripcionDAO = new InscripcionDAOImpl();
        this.eventoDAO = new EventoDAOImpl();
        this.voluntarioDAO = new VoluntarioDAOImpl();
    }

    /**
     * Register volunteer to an event
     */
    public Inscripcion inscribir(Long voluntarioId, Long eventoId) {
        return JPAUtil.executeInTransactionWithResult(em -> {
            inscripcionDAO.setEntityManager(em);
            eventoDAO.setEntityManager(em);
            voluntarioDAO.setEntityManager(em);
            
            // Find volunteer
            Voluntario voluntario = voluntarioDAO.findById(voluntarioId)
                    .orElseThrow(() -> new IllegalArgumentException("Volunteer not found"));
            
            // Find event
            Evento evento = eventoDAO.findById(eventoId)
                    .orElseThrow(() -> new IllegalArgumentException("Event not found"));
            
            // Validate event is approved
            if (!evento.isAprobado()) {
                throw new IllegalStateException("Event is not approved for registration");
            }
            
            // Validate event hasn't started
            if (evento.getFechaInicio().isBefore(java.time.LocalDate.now())) {
                throw new IllegalStateException("Event has already started");
            }
            
            // Check if already registered
            if (inscripcionDAO.existsByVoluntarioAndEvento(voluntarioId, eventoId)) {
                throw new IllegalStateException("Volunteer is already registered for this event");
            }
            
            // Check capacity
            if (!evento.tieneCuposDisponibles()) {
                throw new IllegalStateException("Event has no available spots");
            }
            
            // Decrement capacity if event has limit
            if (evento.tieneLimiteCupos()) {
                if (!evento.decrementarCupo()) {
                    throw new IllegalStateException("Failed to reserve spot");
                }
                eventoDAO.update(evento);
            }
            
            // Create registration
            Inscripcion inscripcion = new Inscripcion(voluntario, evento);
            inscripcion.setEstado(EstadoInscripcion.INSCRITO);
            
            Inscripcion saved = inscripcionDAO.save(inscripcion);
            logger.info("Volunteer {} registered for event {}", voluntarioId, eventoId);
            return saved;
        });
    }

    /**
     * Cancel registration
     */
    public void cancelar(Long inscripcionId) {
        JPAUtil.executeInTransaction(em -> {
            inscripcionDAO.setEntityManager(em);
            eventoDAO.setEntityManager(em);
            
            Inscripcion inscripcion = inscripcionDAO.findById(inscripcionId)
                    .orElseThrow(() -> new IllegalArgumentException("Registration not found"));
            
            // Check if can cancel
            if (inscripcion.getEstado() == EstadoInscripcion.CANCELADO) {
                throw new IllegalStateException("Registration is already cancelled");
            }
            
            if (inscripcion.getEstado() == EstadoInscripcion.ASISTIO) {
                throw new IllegalStateException("Cannot cancel registration with confirmed attendance");
            }
            
            // Cancel registration
            inscripcion.cancelar();
            inscripcionDAO.update(inscripcion);
            
            // Free spot if event has capacity limit
            Evento evento = inscripcion.getEvento();
            if (evento.tieneLimiteCupos()) {
                evento.incrementarCupo();
                eventoDAO.update(evento);
            }
            
            logger.info("Registration cancelled: {}", inscripcionId);
        });
    }

    /**
     * Confirm registration
     */
    public void confirmar(Long inscripcionId) {
        JPAUtil.executeInTransaction(em -> {
            inscripcionDAO.setEntityManager(em);
            
            Inscripcion inscripcion = inscripcionDAO.findById(inscripcionId)
                    .orElseThrow(() -> new IllegalArgumentException("Registration not found"));
            
            if (inscripcion.getEstado() != EstadoInscripcion.INSCRITO) {
                throw new IllegalStateException("Registration cannot be confirmed in current state");
            }
            
            inscripcion.confirmar();
            inscripcionDAO.update(inscripcion);
            
            logger.info("Registration confirmed: {}", inscripcionId);
        });
    }

    /**
     * Mark attendance
     */
    public void marcarAsistencia(Long inscripcionId) {
        JPAUtil.executeInTransaction(em -> {
            inscripcionDAO.setEntityManager(em);
            
            Inscripcion inscripcion = inscripcionDAO.findById(inscripcionId)
                    .orElseThrow(() -> new IllegalArgumentException("Registration not found"));
            
            if (inscripcion.getEstado() == EstadoInscripcion.CANCELADO) {
                throw new IllegalStateException("Cannot mark attendance for cancelled registration");
            }
            
            inscripcion.marcarAsistencia();
            inscripcionDAO.update(inscripcion);
            
            logger.info("Attendance marked for registration: {}", inscripcionId);
        });
    }

    /**
     * Mark non-attendance
     */
    public void marcarNoAsistencia(Long inscripcionId) {
        JPAUtil.executeInTransaction(em -> {
            inscripcionDAO.setEntityManager(em);
            
            Inscripcion inscripcion = inscripcionDAO.findById(inscripcionId)
                    .orElseThrow(() -> new IllegalArgumentException("Registration not found"));
            
            if (inscripcion.getEstado() == EstadoInscripcion.CANCELADO) {
                throw new IllegalStateException("Cannot mark non-attendance for cancelled registration");
            }
            
            inscripcion.marcarNoAsistencia();
            inscripcionDAO.update(inscripcion);
            
            logger.info("Non-attendance marked for registration: {}", inscripcionId);
        });
    }

    /**
     * Find registration by ID
     */
    public Optional<Inscripcion> buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            inscripcionDAO.setEntityManager(em);
            return inscripcionDAO.findById(id);
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Find registrations by volunteer
     */
    public List<Inscripcion> listarPorVoluntario(Long voluntarioId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            inscripcionDAO.setEntityManager(em);
            return inscripcionDAO.findByVoluntarioId(voluntarioId);
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Find active registrations by volunteer
     */
    public List<Inscripcion> listarActivasPorVoluntario(Long voluntarioId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            inscripcionDAO.setEntityManager(em);
            return inscripcionDAO.findActiveByVoluntarioId(voluntarioId);
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Find registrations by event
     */
    public List<Inscripcion> listarPorEvento(Long eventoId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            inscripcionDAO.setEntityManager(em);
            return inscripcionDAO.findByEventoId(eventoId);
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Find active registrations by event
     */
    public List<Inscripcion> listarActivasPorEvento(Long eventoId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            inscripcionDAO.setEntityManager(em);
            return inscripcionDAO.findActiveByEventoId(eventoId);
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Count active registrations for event
     */
    public long contarInscritosPorEvento(Long eventoId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            inscripcionDAO.setEntityManager(em);
            return inscripcionDAO.countActiveByEventoId(eventoId);
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Check if volunteer is registered for event
     */
    public boolean estaInscrito(Long voluntarioId, Long eventoId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            inscripcionDAO.setEntityManager(em);
            return inscripcionDAO.existsByVoluntarioAndEvento(voluntarioId, eventoId);
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Find registrations without certification
     */
    public List<Inscripcion> listarSinCertificacion() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            inscripcionDAO.setEntityManager(em);
            return inscripcionDAO.findWithoutCertificacion();
        } finally {
            JPAUtil.close(em);
        }
    }
}
