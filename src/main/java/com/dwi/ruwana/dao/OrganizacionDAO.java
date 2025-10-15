package com.dwi.ruwana.dao;

import com.dwi.ruwana.model.Organizacion;
import com.dwi.ruwana.model.enums.EstadoAprobacion;

import java.util.List;
import java.util.Optional;

/**
 * DAO interface for Organizacion entity
 */
public interface OrganizacionDAO extends GenericDAO<Organizacion, Long> {
    
    /**
     * Find organization by user ID
     */
    Optional<Organizacion> findByUsuarioId(Long usuarioId);
    
    /**
     * Find organization by document number
     */
    Optional<Organizacion> findByNumeroDocumento(String numeroDocumento);
    
    /**
     * Find organizations by approval status
     */
    List<Organizacion> findByEstadoAprobacion(EstadoAprobacion estado);
    
    /**
     * Find approved organizations
     */
    List<Organizacion> findAprobadas();
    
    /**
     * Find pending approval organizations
     */
    List<Organizacion> findPendientes();
    
    /**
     * Find organizations by country
     */
    List<Organizacion> findByPais(String pais);
    
    /**
     * Find legally constituted organizations
     */
    List<Organizacion> findConstituidasLegalmente();
    
    /**
     * Search organizations by name
     */
    List<Organizacion> searchByNombre(String nombre);
}
