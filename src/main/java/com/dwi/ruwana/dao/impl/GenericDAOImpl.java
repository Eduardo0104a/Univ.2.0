package com.dwi.ruwana.dao.impl;

import com.dwi.ruwana.dao.GenericDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * Generic DAO implementation using JPA
 * @param <T> Entity type
 * @param <ID> Primary key type
 */
public abstract class GenericDAOImpl<T, ID> implements GenericDAO<T, ID> {
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final Class<T> entityClass;
    protected EntityManager entityManager;

    public GenericDAOImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Set EntityManager (to be injected)
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    protected EntityManager getEntityManager() {
        if (entityManager == null) {
            throw new IllegalStateException("EntityManager has not been set");
        }
        return entityManager;
    }

    @Override
    public T save(T entity) {
        try {
            getEntityManager().persist(entity);
            logger.debug("Entity saved: {}", entity);
            return entity;
        } catch (Exception e) {
            logger.error("Error saving entity: {}", entity, e);
            throw new RuntimeException("Error saving entity", e);
        }
    }

    @Override
    public Optional<T> findById(ID id) {
        try {
            T entity = getEntityManager().find(entityClass, id);
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            logger.error("Error finding entity by id: {}", id, e);
            throw new RuntimeException("Error finding entity by id", e);
        }
    }

    @Override
    public List<T> findAll() {
        try {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(entityClass);
            Root<T> root = cq.from(entityClass);
            cq.select(root);
            return getEntityManager().createQuery(cq).getResultList();
        } catch (Exception e) {
            logger.error("Error finding all entities", e);
            throw new RuntimeException("Error finding all entities", e);
        }
    }

    @Override
    public T update(T entity) {
        try {
            T merged = getEntityManager().merge(entity);
            logger.debug("Entity updated: {}", entity);
            return merged;
        } catch (Exception e) {
            logger.error("Error updating entity: {}", entity, e);
            throw new RuntimeException("Error updating entity", e);
        }
    }

    @Override
    public void delete(T entity) {
        try {
            if (!getEntityManager().contains(entity)) {
                entity = getEntityManager().merge(entity);
            }
            getEntityManager().remove(entity);
            logger.debug("Entity deleted: {}", entity);
        } catch (Exception e) {
            logger.error("Error deleting entity: {}", entity, e);
            throw new RuntimeException("Error deleting entity", e);
        }
    }

    @Override
    public void deleteById(ID id) {
        findById(id).ifPresent(this::delete);
    }

    @Override
    public boolean existsById(ID id) {
        return findById(id).isPresent();
    }

    @Override
    public long count() {
        try {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<T> root = cq.from(entityClass);
            cq.select(cb.count(root));
            return getEntityManager().createQuery(cq).getSingleResult();
        } catch (Exception e) {
            logger.error("Error counting entities", e);
            throw new RuntimeException("Error counting entities", e);
        }
    }
}
