package com.dwi.ruwana.dao;

import com.dwi.ruwana.model.Inscripcion;
import com.dwi.ruwana.model.enums.EstadoInscripcion;

import java.util.List;
import java.util.Optional;

/**
 * DAO interface for Inscripcion entity
 */
public interface InscripcionDAO extends GenericDAO<Inscripcion, Long> {
    
    /**
     * Find registrations by volunteer
     */
    List<Inscripcion> findByVoluntarioId(Long voluntarioId);
    
    /**
     * Find registrations by event
     */
    List<Inscripcion> findByEventoId(Long eventoId);
    
    /**
     * Find registration by volunteer and event
     */
    Optional<Inscripcion> findByVoluntarioAndEvento(Long voluntarioId, Long eventoId);
    
    /**
     * Find registrations by status
     */
    List<Inscripcion> findByEstado(EstadoInscripcion estado);
    
    /**
     * Find active registrations for an event
     */
    List<Inscripcion> findActiveByEventoId(Long eventoId);
    
    /**
     * Find active registrations for a volunteer
     */
    List<Inscripcion> findActiveByVoluntarioId(Long voluntarioId);
    
    /**
     * Find registrations with attendance marked
     */
    List<Inscripcion> findWithAsistencia();
    
    /**
     * Find registrations without certification
     */
    List<Inscripcion> findWithoutCertificacion();
    
    /**
     * Check if volunteer is registered for event
     */
    boolean existsByVoluntarioAndEvento(Long voluntarioId, Long eventoId);
    
    /**
     * Count active registrations for event
     */
    long countActiveByEventoId(Long eventoId);
}
