package com.dwi.ruwana.dao;

import com.dwi.ruwana.model.Voluntario;

import java.util.List;
import java.util.Optional;

/**
 * DAO interface for Voluntario entity
 */
public interface VoluntarioDAO extends GenericDAO<Voluntario, Long> {
    
    /**
     * Find volunteer by user ID
     */
    Optional<Voluntario> findByUsuarioId(Long usuarioId);
    
    /**
     * Find volunteer by document number
     */
    Optional<Voluntario> findByNumeroDocumento(String numeroDocumento);
    
    /**
     * Find volunteers by education level
     */
    List<Voluntario> findByGradoInstruccionId(Integer gradoInstruccionId);
    
    /**
     * Find volunteers by study center type
     */
    List<Voluntario> findByCentroEstudiosId(Integer centroEstudiosId);
    
    /**
     * Find volunteers with institutional email
     */
    List<Voluntario> findWithCorreoInstitucional();
    
    /**
     * Count volunteers by gender
     */
    long countByGeneroId(Integer generoId);
}
