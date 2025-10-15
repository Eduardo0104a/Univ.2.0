package com.dwi.ruwana.dao.impl;

import com.dwi.ruwana.dao.OrganizacionDAO;
import com.dwi.ruwana.model.Organizacion;
import com.dwi.ruwana.model.enums.EstadoAprobacion;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

/**
 * DAO implementation for Organizacion entity
 */
public class OrganizacionDAOImpl extends GenericDAOImpl<Organizacion, Long> implements OrganizacionDAO {

    public OrganizacionDAOImpl() {
        super(Organizacion.class);
    }

    @Override
    public Optional<Organizacion> findByUsuarioId(Long usuarioId) {
        try {
            TypedQuery<Organizacion> query = getEntityManager()
                    .createQuery("SELECT o FROM Organizacion o WHERE o.usuario.id = :usuarioId", 
                            Organizacion.class);
            query.setParameter("usuarioId", usuarioId);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (Exception e) {
            logger.error("Error finding organization by user ID: {}", usuarioId, e);
            throw new RuntimeException("Error finding organization by user ID", e);
        }
    }

    @Override
    public Optional<Organizacion> findByNumeroDocumento(String numeroDocumento) {
        try {
            TypedQuery<Organizacion> query = getEntityManager()
                    .createQuery("SELECT o FROM Organizacion o WHERE o.numeroDocumento = :numero", 
                            Organizacion.class);
            query.setParameter("numero", numeroDocumento);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (Exception e) {
            logger.error("Error finding organization by document number: {}", numeroDocumento, e);
            throw new RuntimeException("Error finding organization by document number", e);
        }
    }

    @Override
    public List<Organizacion> findByEstadoAprobacion(EstadoAprobacion estado) {
        try {
            String jpql = "SELECT DISTINCT o FROM Organizacion o " +
                         "JOIN FETCH o.usuario " +
                         "WHERE o.estadoAprobacion = :estado " +
                         "ORDER BY o.fechaRegistro DESC";
            TypedQuery<Organizacion> query = getEntityManager().createQuery(jpql, Organizacion.class);
            query.setParameter("estado", estado);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding organizations by approval status: {}", estado, e);
            throw new RuntimeException("Error finding organizations by approval status", e);
        }
    }

    @Override
    public List<Organizacion> findAprobadas() {
        return findByEstadoAprobacion(EstadoAprobacion.APROBADO);
    }

    @Override
    public List<Organizacion> findPendientes() {
        return findByEstadoAprobacion(EstadoAprobacion.PENDIENTE);
    }

    @Override
    public List<Organizacion> findAll() {
        try {
            String jpql = "SELECT DISTINCT o FROM Organizacion o " +
                         "JOIN FETCH o.usuario " +
                         "ORDER BY o.fechaRegistro DESC";
            TypedQuery<Organizacion> query = getEntityManager().createQuery(jpql, Organizacion.class);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding all organizations", e);
            throw new RuntimeException("Error finding all organizations", e);
        }
    }

    @Override
    public List<Organizacion> findByPais(String pais) {
        try {
            TypedQuery<Organizacion> query = getEntityManager()
                    .createQuery("SELECT o FROM Organizacion o WHERE o.pais = :pais " +
                               "ORDER BY o.nombreOrganizacion", Organizacion.class);
            query.setParameter("pais", pais);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding organizations by country: {}", pais, e);
            throw new RuntimeException("Error finding organizations by country", e);
        }
    }

    @Override
    public List<Organizacion> findConstituidasLegalmente() {
        try {
            TypedQuery<Organizacion> query = getEntityManager()
                    .createQuery("SELECT o FROM Organizacion o WHERE o.constituidaLegalmente = true " +
                               "ORDER BY o.nombreOrganizacion", Organizacion.class);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding legally constituted organizations", e);
            throw new RuntimeException("Error finding legally constituted organizations", e);
        }
    }

    @Override
    public List<Organizacion> searchByNombre(String nombre) {
        try {
            TypedQuery<Organizacion> query = getEntityManager()
                    .createQuery("SELECT o FROM Organizacion o " +
                               "WHERE LOWER(o.nombreOrganizacion) LIKE LOWER(:nombre) " +
                               "ORDER BY o.nombreOrganizacion", Organizacion.class);
            query.setParameter("nombre", "%" + nombre + "%");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error searching organizations by name: {}", nombre, e);
            throw new RuntimeException("Error searching organizations by name", e);
        }
    }
}
