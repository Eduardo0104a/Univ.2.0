package com.dwi.ruwana.dao;

import java.util.List;
import java.util.Optional;

/**
 * Generic DAO interface for catalog entities
 * @param <T> Catalog entity type
 */
public interface CatalogoDAO<T> extends GenericDAO<T, Integer> {
    
    /**
     * Find catalog entry by code
     */
    Optional<T> findByCodigo(String codigo);
    
    /**
     * Find all active catalog entries
     */
    List<T> findActivos();
    
    /**
     * Find catalog entries ordered by orden field
     */
    List<T> findAllOrdered();
}
