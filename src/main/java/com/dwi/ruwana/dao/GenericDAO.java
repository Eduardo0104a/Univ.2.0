package com.dwi.ruwana.dao;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * Generic DAO interface with common CRUD operations
 * @param <T> Entity type
 * @param <ID> Primary key type
 */
public interface GenericDAO<T, ID> {
    
    /**
     * Set EntityManager for this DAO
     */
    void setEntityManager(EntityManager entityManager);
    
    /**
     * Save or update an entity
     */
    T save(T entity);
    
    /**
     * Find entity by ID
     */
    Optional<T> findById(ID id);
    
    /**
     * Find all entities
     */
    List<T> findAll();
    
    /**
     * Update an existing entity
     */
    T update(T entity);
    
    /**
     * Delete an entity
     */
    void delete(T entity);
    
    /**
     * Delete entity by ID
     */
    void deleteById(ID id);
    
    /**
     * Check if entity exists by ID
     */
    boolean existsById(ID id);
    
    /**
     * Count all entities
     */
    long count();
}
