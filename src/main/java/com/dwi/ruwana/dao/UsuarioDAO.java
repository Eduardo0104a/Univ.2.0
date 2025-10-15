package com.dwi.ruwana.dao;

import com.dwi.ruwana.model.Usuario;
import com.dwi.ruwana.model.enums.RolUsuario;

import java.util.List;
import java.util.Optional;

/**
 * DAO interface for Usuario entity
 */
public interface UsuarioDAO extends GenericDAO<Usuario, Long> {
    
    /**
     * Find user by email
     */
    Optional<Usuario> findByEmail(String email);
    
    /**
     * Find users by role
     */
    List<Usuario> findByRol(RolUsuario rol);
    
    /**
     * Find active users
     */
    List<Usuario> findByEstadoActivo(Boolean activo);
    
    /**
     * Find verified users
     */
    List<Usuario> findByEstadoVerificado(Boolean verificado);
    
    /**
     * Check if email exists
     */
    boolean existsByEmail(String email);
    
    /**
     * Authenticate user
     */
    Optional<Usuario> authenticate(String email, String password);
}
