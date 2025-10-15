package com.dwi.ruwana.service;

import com.dwi.ruwana.dao.UsuarioDAO;
import com.dwi.ruwana.dao.impl.UsuarioDAOImpl;
import com.dwi.ruwana.model.Usuario;
import com.dwi.ruwana.model.enums.RolUsuario;
import com.dwi.ruwana.util.JPAUtil;
import jakarta.persistence.EntityManager;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for Usuario entity
 * Handles business logic and transaction management
 */
public class UsuarioService {
    
    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);
    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    /**
     * Register a new user
     */
    public Usuario registrar(Usuario usuario, String passwordPlain) {
        return JPAUtil.executeInTransactionWithResult(em -> {
            usuarioDAO.setEntityManager(em);
            
            // Validate email doesn't exist
            if (usuarioDAO.existsByEmail(usuario.getEmail())) {
                throw new IllegalArgumentException("Email already exists");
            }
            
            // Hash password
            String hashedPassword = BCrypt.hashpw(passwordPlain, BCrypt.gensalt(10));
            usuario.setPassword(hashedPassword);
            
            // Save user
            Usuario saved = usuarioDAO.save(usuario);
            logger.info("User registered: {}", saved.getEmail());
            return saved;
        });
    }

    /**
     * Authenticate user with email and password
     */
    public Optional<Usuario> autenticar(String email, String passwordPlain) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            usuarioDAO.setEntityManager(em);
            
            Optional<Usuario> usuarioOpt = usuarioDAO.findByEmail(email);
            
            if (usuarioOpt.isEmpty()) {
                logger.warn("Authentication failed: User not found - {}", email);
                return Optional.empty();
            }
            
            Usuario usuario = usuarioOpt.get();
            
            // Check if account is active
            if (!usuario.getEstadoActivo()) {
                logger.warn("Authentication failed: Inactive account - {}", email);
                return Optional.empty();
            }
            
            // Verify password
            if (!BCrypt.checkpw(passwordPlain, usuario.getPassword())) {
                logger.warn("Authentication failed: Invalid password - {}", email);
                return Optional.empty();
            }
            
            // Update last login
            em.getTransaction().begin();
            usuario.setUltimoLogin(LocalDateTime.now());
            em.getTransaction().commit();
            
            logger.info("User authenticated successfully: {}", email);
            return Optional.of(usuario);
            
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.error("Error authenticating user: {}", email, e);
            throw new RuntimeException("Authentication error", e);
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Find user by ID
     */
    public Optional<Usuario> buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            usuarioDAO.setEntityManager(em);
            return usuarioDAO.findById(id);
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Find user by email
     */
    public Optional<Usuario> buscarPorEmail(String email) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            usuarioDAO.setEntityManager(em);
            return usuarioDAO.findByEmail(email);
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Find users by role
     */
    public List<Usuario> buscarPorRol(RolUsuario rol) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            usuarioDAO.setEntityManager(em);
            return usuarioDAO.findByRol(rol);
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Update user
     */
    public Usuario actualizar(Usuario usuario) {
        return JPAUtil.executeInTransactionWithResult(em -> {
            usuarioDAO.setEntityManager(em);
            Usuario updated = usuarioDAO.update(usuario);
            logger.info("User updated: {}", updated.getEmail());
            return updated;
        });
    }

    /**
     * Change password
     */
    public void cambiarPassword(Long usuarioId, String oldPassword, String newPassword) {
        JPAUtil.executeInTransaction(em -> {
            usuarioDAO.setEntityManager(em);
            
            Usuario usuario = usuarioDAO.findById(usuarioId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            
            // Verify old password
            if (!BCrypt.checkpw(oldPassword, usuario.getPassword())) {
                throw new IllegalArgumentException("Invalid old password");
            }
            
            // Hash and set new password
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(10));
            usuario.setPassword(hashedPassword);
            
            usuarioDAO.update(usuario);
            logger.info("Password changed for user: {}", usuario.getEmail());
        });
    }

    /**
     * Verify user email
     */
    public void verificarEmail(Long usuarioId) {
        JPAUtil.executeInTransaction(em -> {
            usuarioDAO.setEntityManager(em);
            
            Usuario usuario = usuarioDAO.findById(usuarioId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            
            usuario.setEstadoVerificado(true);
            usuario.setFechaVerificacion(LocalDateTime.now());
            
            usuarioDAO.update(usuario);
            logger.info("Email verified for user: {}", usuario.getEmail());
        });
    }

    /**
     * Activate/Deactivate user
     */
    public void cambiarEstadoActivo(Long usuarioId, boolean activo) {
        JPAUtil.executeInTransaction(em -> {
            usuarioDAO.setEntityManager(em);
            
            Usuario usuario = usuarioDAO.findById(usuarioId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            
            usuario.setEstadoActivo(activo);
            usuarioDAO.update(usuario);
            
            logger.info("User {} {}", usuario.getEmail(), activo ? "activated" : "deactivated");
        });
    }

    /**
     * Delete user
     */
    public void eliminar(Long usuarioId) {
        JPAUtil.executeInTransaction(em -> {
            usuarioDAO.setEntityManager(em);
            usuarioDAO.deleteById(usuarioId);
            logger.info("User deleted: {}", usuarioId);
        });
    }

    /**
     * Check if email exists
     */
    public boolean emailExiste(String email) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            usuarioDAO.setEntityManager(em);
            return usuarioDAO.existsByEmail(email);
        } finally {
            JPAUtil.close(em);
        }
    }
}
