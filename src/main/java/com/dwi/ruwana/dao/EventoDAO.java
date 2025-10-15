package com.dwi.ruwana.dao;

import com.dwi.ruwana.model.Evento;
import com.dwi.ruwana.model.enums.EstadoEvento;

import java.time.LocalDate;
import java.util.List;

/**
 * DAO interface for Evento entity
 */
public interface EventoDAO extends GenericDAO<Evento, Long> {
    
    /**
     * Find events by organization
     */
    List<Evento> findByOrganizacionId(Long organizacionId);
    
    /**
     * Find events by status
     */
    List<Evento> findByEstado(EstadoEvento estado);
    
    /**
     * Find approved and available events
     */
    List<Evento> findEventosDisponibles();
    
    /**
     * Find events with available capacity (unlimited or has spots)
     */
    List<Evento> findEventosConCupos();
    
    /**
     * Find upcoming events
     */
    List<Evento> findEventosProximos();
    
    /**
     * Find events by date range
     */
    List<Evento> findByFechaRange(LocalDate desde, LocalDate hasta);
    
    /**
     * Find events by location
     */
    List<Evento> findByLugar(String lugar);
    
    /**
     * Search events by name (partial match)
     */
    List<Evento> searchByNombre(String nombre);
    
    /**
     * Find pending approval events
     */
    List<Evento> findPendientesAprobacion();
    
    /**
     * Find events ending soon (within days)
     */
    List<Evento> findEventosPorFinalizar(int dias);
    
    /**
     * Find event by ID with organization loaded
     */
    java.util.Optional<Evento> findByIdWithOrganizacion(Long id);
}
