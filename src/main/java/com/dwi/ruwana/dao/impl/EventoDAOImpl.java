package com.dwi.ruwana.dao.impl;

import com.dwi.ruwana.dao.EventoDAO;
import com.dwi.ruwana.model.Evento;
import com.dwi.ruwana.model.enums.EstadoEvento;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

/**
 * DAO implementation for Evento entity
 */
public class EventoDAOImpl extends GenericDAOImpl<Evento, Long> implements EventoDAO {

    public EventoDAOImpl() {
        super(Evento.class);
    }

    @Override
    public List<Evento> findByOrganizacionId(Long organizacionId) {
        try {
            String jpql = "SELECT DISTINCT e FROM Evento e " +
                         "JOIN FETCH e.organizacion " +
                         "WHERE e.organizacion.id = :orgId " +
                         "ORDER BY e.fechaInicio DESC";
            
            TypedQuery<Evento> query = getEntityManager().createQuery(jpql, Evento.class);
            query.setParameter("orgId", organizacionId);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding events by organization: {}", organizacionId, e);
            throw new RuntimeException("Error finding events by organization", e);
        }
    }

    @Override
    public List<Evento> findByEstado(EstadoEvento estado) {
        try {
            String jpql = "SELECT DISTINCT e FROM Evento e " +
                         "JOIN FETCH e.organizacion " +
                         "WHERE e.estado = :estado " +
                         "ORDER BY e.fechaInicio";
            
            TypedQuery<Evento> query = getEntityManager().createQuery(jpql, Evento.class);
            query.setParameter("estado", estado);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding events by status: {}", estado, e);
            throw new RuntimeException("Error finding events by status", e);
        }
    }

    @Override
    public List<Evento> findEventosDisponibles() {
        try {
            String jpql = "SELECT DISTINCT e FROM Evento e " +
                         "JOIN FETCH e.organizacion " +
                         "WHERE e.estado = :estado " +
                         "AND e.fechaInicio >= :hoy " +
                         "AND (e.cuposMaximos IS NULL OR e.cuposMaximos = 0 OR e.cuposDisponibles > 0) " +
                         "ORDER BY e.fechaInicio";
            
            TypedQuery<Evento> query = getEntityManager().createQuery(jpql, Evento.class);
            query.setParameter("estado", EstadoEvento.APROBADO);
            query.setParameter("hoy", LocalDate.now());
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding available events", e);
            throw new RuntimeException("Error finding available events", e);
        }
    }

    @Override
    public List<Evento> findEventosConCupos() {
        try {
            String jpql = "SELECT e FROM Evento e " +
                         "WHERE e.estado = :estado " +
                         "AND e.fechaInicio >= :hoy " +
                         "AND (e.cuposMaximos IS NULL OR e.cuposMaximos = 0 OR e.cuposDisponibles > 0) " +
                         "ORDER BY e.fechaInicio";
            
            TypedQuery<Evento> query = getEntityManager().createQuery(jpql, Evento.class);
            query.setParameter("estado", EstadoEvento.APROBADO);
            query.setParameter("hoy", LocalDate.now());
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding events with capacity", e);
            throw new RuntimeException("Error finding events with capacity", e);
        }
    }

    @Override
    public List<Evento> findEventosProximos() {
        try {
            String jpql = "SELECT e FROM Evento e " +
                         "WHERE e.estado = :estado " +
                         "AND e.fechaInicio >= :hoy " +
                         "ORDER BY e.fechaInicio";
            
            TypedQuery<Evento> query = getEntityManager().createQuery(jpql, Evento.class);
            query.setParameter("estado", EstadoEvento.APROBADO);
            query.setParameter("hoy", LocalDate.now());
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding upcoming events", e);
            throw new RuntimeException("Error finding upcoming events", e);
        }
    }

    @Override
    public List<Evento> findByFechaRange(LocalDate desde, LocalDate hasta) {
        try {
            String jpql = "SELECT e FROM Evento e " +
                         "WHERE e.fechaInicio BETWEEN :desde AND :hasta " +
                         "ORDER BY e.fechaInicio";
            
            TypedQuery<Evento> query = getEntityManager().createQuery(jpql, Evento.class);
            query.setParameter("desde", desde);
            query.setParameter("hasta", hasta);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding events by date range", e);
            throw new RuntimeException("Error finding events by date range", e);
        }
    }

    @Override
    public List<Evento> findByLugar(String lugar) {
        try {
            String jpql = "SELECT DISTINCT e FROM Evento e " +
                         "JOIN FETCH e.organizacion " +
                         "WHERE LOWER(e.lugar) LIKE LOWER(:lugar) " +
                         "ORDER BY e.fechaInicio";
            
            TypedQuery<Evento> query = getEntityManager().createQuery(jpql, Evento.class);
            query.setParameter("lugar", "%" + lugar + "%");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding events by location: {}", lugar, e);
            throw new RuntimeException("Error finding events by location", e);
        }
    }

    @Override
    public List<Evento> searchByNombre(String nombre) {
        try {
            String jpql = "SELECT DISTINCT e FROM Evento e " +
                         "JOIN FETCH e.organizacion " +
                         "WHERE LOWER(e.nombre) LIKE LOWER(:nombre) " +
                         "AND e.estado = :estado " +
                         "ORDER BY e.fechaInicio";
            
            TypedQuery<Evento> query = getEntityManager().createQuery(jpql, Evento.class);
            query.setParameter("nombre", "%" + nombre + "%");
            query.setParameter("estado", EstadoEvento.APROBADO);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error searching events by name: {}", nombre, e);
            throw new RuntimeException("Error searching events by name", e);
        }
    }

    @Override
    public List<Evento> findPendientesAprobacion() {
        try {
            String jpql = "SELECT DISTINCT e FROM Evento e " +
                         "JOIN FETCH e.organizacion " +
                         "WHERE e.estado = :estado " +
                         "ORDER BY e.fechaCreacion";
            
            TypedQuery<Evento> query = getEntityManager().createQuery(jpql, Evento.class);
            query.setParameter("estado", EstadoEvento.PENDIENTE_APROBACION);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding pending approval events", e);
            throw new RuntimeException("Error finding pending approval events", e);
        }
    }

    @Override
    public List<Evento> findEventosPorFinalizar(int dias) {
        try {
            LocalDate desde = LocalDate.now();
            LocalDate hasta = desde.plusDays(dias);
            
            String jpql = "SELECT e FROM Evento e " +
                         "WHERE e.estado = :estado " +
                         "AND e.fechaFin BETWEEN :desde AND :hasta " +
                         "ORDER BY e.fechaFin";
            
            TypedQuery<Evento> query = getEntityManager().createQuery(jpql, Evento.class);
            query.setParameter("estado", EstadoEvento.EN_CURSO);
            query.setParameter("desde", desde);
            query.setParameter("hasta", hasta);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding events ending soon", e);
            throw new RuntimeException("Error finding events ending soon", e);
        }
    }

    @Override
    public java.util.Optional<Evento> findByIdWithOrganizacion(Long id) {
        try {
            String jpql = "SELECT e FROM Evento e " +
                         "JOIN FETCH e.organizacion " +
                         "WHERE e.id = :id";
            
            TypedQuery<Evento> query = getEntityManager().createQuery(jpql, Evento.class);
            query.setParameter("id", id);
            
            List<Evento> results = query.getResultList();
            return results.isEmpty() ? java.util.Optional.empty() : java.util.Optional.of(results.get(0));
        } catch (Exception e) {
            logger.error("Error finding event by ID with organization: {}", id, e);
            throw new RuntimeException("Error finding event by ID with organization", e);
        }
    }
}
