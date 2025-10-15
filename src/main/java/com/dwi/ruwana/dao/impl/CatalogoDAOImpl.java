package com.dwi.ruwana.dao.impl;

import com.dwi.ruwana.dao.CatalogoDAO;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

/**
 * Generic DAO implementation for catalog entities
 * @param <T> Catalog entity type
 */
public abstract class CatalogoDAOImpl<T> extends GenericDAOImpl<T, Integer> implements CatalogoDAO<T> {

    public CatalogoDAOImpl(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    public Optional<T> findByCodigo(String codigo) {
        try {
            String jpql = String.format("SELECT c FROM %s c WHERE c.codigo = :codigo", 
                    entityClass.getSimpleName());
            TypedQuery<T> query = getEntityManager().createQuery(jpql, entityClass);
            query.setParameter("codigo", codigo);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (Exception e) {
            logger.error("Error finding catalog entry by code: {}", codigo, e);
            throw new RuntimeException("Error finding catalog entry by code", e);
        }
    }

    @Override
    public List<T> findActivos() {
        try {
            String jpql = String.format("SELECT c FROM %s c WHERE c.activo = true ORDER BY c.orden", 
                    entityClass.getSimpleName());
            TypedQuery<T> query = getEntityManager().createQuery(jpql, entityClass);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding active catalog entries", e);
            throw new RuntimeException("Error finding active catalog entries", e);
        }
    }

    @Override
    public List<T> findAllOrdered() {
        try {
            String jpql = String.format("SELECT c FROM %s c ORDER BY c.orden", 
                    entityClass.getSimpleName());
            TypedQuery<T> query = getEntityManager().createQuery(jpql, entityClass);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding all catalog entries ordered", e);
            throw new RuntimeException("Error finding all catalog entries ordered", e);
        }
    }
}
