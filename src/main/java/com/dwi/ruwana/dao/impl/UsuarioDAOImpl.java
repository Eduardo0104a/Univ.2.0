package com.dwi.ruwana.dao.impl;

import com.dwi.ruwana.dao.UsuarioDAO;
import com.dwi.ruwana.model.Usuario;
import com.dwi.ruwana.model.enums.RolUsuario;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

/**
 * DAO implementation for Usuario entity
 */
public class UsuarioDAOImpl extends GenericDAOImpl<Usuario, Long> implements UsuarioDAO {

    public UsuarioDAOImpl() {
        super(Usuario.class);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        try {
            TypedQuery<Usuario> query = getEntityManager()
                    .createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class);
            query.setParameter("email", email);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (Exception e) {
            logger.error("Error finding user by email: {}", email, e);
            throw new RuntimeException("Error finding user by email", e);
        }
    }

    @Override
    public List<Usuario> findByRol(RolUsuario rol) {
        try {
            TypedQuery<Usuario> query = getEntityManager()
                    .createQuery("SELECT u FROM Usuario u WHERE u.rol = :rol", Usuario.class);
            query.setParameter("rol", rol);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding users by role: {}", rol, e);
            throw new RuntimeException("Error finding users by role", e);
        }
    }

    @Override
    public List<Usuario> findByEstadoActivo(Boolean activo) {
        try {
            TypedQuery<Usuario> query = getEntityManager()
                    .createQuery("SELECT u FROM Usuario u WHERE u.estadoActivo = :activo", Usuario.class);
            query.setParameter("activo", activo);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding users by active status: {}", activo, e);
            throw new RuntimeException("Error finding users by active status", e);
        }
    }

    @Override
    public List<Usuario> findByEstadoVerificado(Boolean verificado) {
        try {
            TypedQuery<Usuario> query = getEntityManager()
                    .createQuery("SELECT u FROM Usuario u WHERE u.estadoVerificado = :verificado", Usuario.class);
            query.setParameter("verificado", verificado);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding users by verified status: {}", verificado, e);
            throw new RuntimeException("Error finding users by verified status", e);
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        try {
            TypedQuery<Long> query = getEntityManager()
                    .createQuery("SELECT COUNT(u) FROM Usuario u WHERE u.email = :email", Long.class);
            query.setParameter("email", email);
            return query.getSingleResult() > 0;
        } catch (Exception e) {
            logger.error("Error checking if email exists: {}", email, e);
            throw new RuntimeException("Error checking if email exists", e);
        }
    }

    @Override
    public Optional<Usuario> authenticate(String email, String password) {
        try {
            TypedQuery<Usuario> query = getEntityManager()
                    .createQuery("SELECT u FROM Usuario u WHERE u.email = :email AND u.estadoActivo = true", 
                            Usuario.class);
            query.setParameter("email", email);
            Usuario usuario = query.getSingleResult();
            
            // Note: Password verification should be done using BCrypt in service layer
            // This just returns the user if email exists and is active
            return Optional.of(usuario);
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (Exception e) {
            logger.error("Error authenticating user: {}", email, e);
            throw new RuntimeException("Error authenticating user", e);
        }
    }
}
