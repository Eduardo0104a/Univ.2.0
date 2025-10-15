package com.dwi.ruwana.dao.impl;

import com.dwi.ruwana.dao.InscripcionDAO;
import com.dwi.ruwana.model.Inscripcion;
import com.dwi.ruwana.model.enums.EstadoInscripcion;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

/**
 * DAO implementation for Inscripcion entity
 */
public class InscripcionDAOImpl extends GenericDAOImpl<Inscripcion, Long> implements InscripcionDAO {

    public InscripcionDAOImpl() {
        super(Inscripcion.class);
    }

    @Override
    public List<Inscripcion> findByVoluntarioId(Long voluntarioId) {
        try {
            TypedQuery<Inscripcion> query = getEntityManager()
                    .createQuery("SELECT i FROM Inscripcion i WHERE i.voluntario.id = :volId " +
                               "ORDER BY i.fechaInscripcion DESC", Inscripcion.class);
            query.setParameter("volId", voluntarioId);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding registrations by volunteer: {}", voluntarioId, e);
            throw new RuntimeException("Error finding registrations by volunteer", e);
        }
    }

    @Override
    public List<Inscripcion> findByEventoId(Long eventoId) {
        try {
            TypedQuery<Inscripcion> query = getEntityManager()
                    .createQuery("SELECT i FROM Inscripcion i WHERE i.evento.id = :eventoId " +
                               "ORDER BY i.fechaInscripcion", Inscripcion.class);
            query.setParameter("eventoId", eventoId);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding registrations by event: {}", eventoId, e);
            throw new RuntimeException("Error finding registrations by event", e);
        }
    }

    @Override
    public Optional<Inscripcion> findByVoluntarioAndEvento(Long voluntarioId, Long eventoId) {
        try {
            TypedQuery<Inscripcion> query = getEntityManager()
                    .createQuery("SELECT i FROM Inscripcion i " +
                               "WHERE i.voluntario.id = :volId AND i.evento.id = :eventoId", 
                               Inscripcion.class);
            query.setParameter("volId", voluntarioId);
            query.setParameter("eventoId", eventoId);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (Exception e) {
            logger.error("Error finding registration by volunteer and event", e);
            throw new RuntimeException("Error finding registration by volunteer and event", e);
        }
    }

    @Override
    public List<Inscripcion> findByEstado(EstadoInscripcion estado) {
        try {
            TypedQuery<Inscripcion> query = getEntityManager()
                    .createQuery("SELECT i FROM Inscripcion i WHERE i.estado = :estado " +
                               "ORDER BY i.fechaInscripcion DESC", Inscripcion.class);
            query.setParameter("estado", estado);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding registrations by status: {}", estado, e);
            throw new RuntimeException("Error finding registrations by status", e);
        }
    }

    @Override
    public List<Inscripcion> findActiveByEventoId(Long eventoId) {
        try {
            TypedQuery<Inscripcion> query = getEntityManager()
                    .createQuery("SELECT i FROM Inscripcion i " +
                               "WHERE i.evento.id = :eventoId AND i.estado != :cancelado " +
                               "ORDER BY i.fechaInscripcion", Inscripcion.class);
            query.setParameter("eventoId", eventoId);
            query.setParameter("cancelado", EstadoInscripcion.CANCELADO);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding active registrations by event: {}", eventoId, e);
            throw new RuntimeException("Error finding active registrations by event", e);
        }
    }

    @Override
    public List<Inscripcion> findActiveByVoluntarioId(Long voluntarioId) {
        try {
            TypedQuery<Inscripcion> query = getEntityManager()
                    .createQuery("SELECT i FROM Inscripcion i " +
                               "WHERE i.voluntario.id = :volId AND i.estado != :cancelado " +
                               "ORDER BY i.fechaInscripcion DESC", Inscripcion.class);
            query.setParameter("volId", voluntarioId);
            query.setParameter("cancelado", EstadoInscripcion.CANCELADO);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding active registrations by volunteer: {}", voluntarioId, e);
            throw new RuntimeException("Error finding active registrations by volunteer", e);
        }
    }

    @Override
    public List<Inscripcion> findWithAsistencia() {
        try {
            TypedQuery<Inscripcion> query = getEntityManager()
                    .createQuery("SELECT i FROM Inscripcion i " +
                               "WHERE i.estado = :asistio OR i.estado = :noAsistio", 
                               Inscripcion.class);
            query.setParameter("asistio", EstadoInscripcion.ASISTIO);
            query.setParameter("noAsistio", EstadoInscripcion.NO_ASISTIO);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding registrations with attendance", e);
            throw new RuntimeException("Error finding registrations with attendance", e);
        }
    }

    @Override
    public List<Inscripcion> findWithoutCertificacion() {
        try {
            TypedQuery<Inscripcion> query = getEntityManager()
                    .createQuery("SELECT i FROM Inscripcion i " +
                               "WHERE i.estado = :asistio AND i.certificadoGenerado = false", 
                               Inscripcion.class);
            query.setParameter("asistio", EstadoInscripcion.ASISTIO);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding registrations without certification", e);
            throw new RuntimeException("Error finding registrations without certification", e);
        }
    }

    @Override
    public boolean existsByVoluntarioAndEvento(Long voluntarioId, Long eventoId) {
        try {
            TypedQuery<Long> query = getEntityManager()
                    .createQuery("SELECT COUNT(i) FROM Inscripcion i " +
                               "WHERE i.voluntario.id = :volId AND i.evento.id = :eventoId", 
                               Long.class);
            query.setParameter("volId", voluntarioId);
            query.setParameter("eventoId", eventoId);
            return query.getSingleResult() > 0;
        } catch (Exception e) {
            logger.error("Error checking if registration exists", e);
            throw new RuntimeException("Error checking if registration exists", e);
        }
    }

    @Override
    public long countActiveByEventoId(Long eventoId) {
        try {
            TypedQuery<Long> query = getEntityManager()
                    .createQuery("SELECT COUNT(i) FROM Inscripcion i " +
                               "WHERE i.evento.id = :eventoId AND i.estado != :cancelado", 
                               Long.class);
            query.setParameter("eventoId", eventoId);
            query.setParameter("cancelado", EstadoInscripcion.CANCELADO);
            return query.getSingleResult();
        } catch (Exception e) {
            logger.error("Error counting active registrations by event: {}", eventoId, e);
            throw new RuntimeException("Error counting active registrations by event", e);
        }
    }
}
