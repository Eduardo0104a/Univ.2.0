package com.dwi.ruwana.service;

import com.dwi.ruwana.dao.OrganizacionDAO;
import com.dwi.ruwana.dao.UsuarioDAO;
import com.dwi.ruwana.dao.impl.OrganizacionDAOImpl;
import com.dwi.ruwana.dao.impl.UsuarioDAOImpl;
import com.dwi.ruwana.model.Organizacion;
import com.dwi.ruwana.model.Usuario;
import com.dwi.ruwana.model.enums.EstadoAprobacion;
import com.dwi.ruwana.model.enums.RolUsuario;
import com.dwi.ruwana.util.JPAUtil;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for Organizacion entity
 */
public class OrganizacionService {
    
    private static final Logger logger = LoggerFactory.getLogger(OrganizacionService.class);
    private final OrganizacionDAO organizacionDAO;
    private final UsuarioDAO usuarioDAO;
    private final UsuarioService usuarioService;

    public OrganizacionService() {
        this.organizacionDAO = new OrganizacionDAOImpl();
        this.usuarioDAO = new UsuarioDAOImpl();
        this.usuarioService = new UsuarioService();
    }

    /**
     * Register a new organization with user account
     */
    public Organizacion registrar(Usuario usuario, String password, Organizacion organizacion) {
        return JPAUtil.executeInTransactionWithResult(em -> {
            organizacionDAO.setEntityManager(em);
            usuarioDAO.setEntityManager(em);
            
            // Validate email doesn't exist
            if (usuarioDAO.existsByEmail(usuario.getEmail())) {
                throw new IllegalArgumentException("Email already exists");
            }
            
            // Validate document doesn't exist
            if (organizacion.getNumeroDocumento() != null) {
                if (organizacionDAO.findByNumeroDocumento(organizacion.getNumeroDocumento()).isPresent()) {
                    throw new IllegalArgumentException("Document number already exists");
                }
            }
            
            // Set user role
            usuario.setRol(RolUsuario.ORGANIZACION);
            
            // Register user using UsuarioService (handles password hashing)
            Usuario savedUser = usuarioService.registrar(usuario, password);
            
            // Associate organization with user
            organizacion.setUsuario(savedUser);
            organizacion.setEstadoAprobacion(EstadoAprobacion.PENDIENTE);
            
            Organizacion saved = organizacionDAO.save(organizacion);
            logger.info("Organization registered: {} - {}", saved.getId(), saved.getNombreOrganizacion());
            return saved;
        });
    }

    /**
     * Find organization by ID
     */
    public Optional<Organizacion> buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            organizacionDAO.setEntityManager(em);
            return organizacionDAO.findById(id);
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Find organization by user ID
     */
    public Optional<Organizacion> buscarPorUsuarioId(Long usuarioId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            organizacionDAO.setEntityManager(em);
            return organizacionDAO.findByUsuarioId(usuarioId);
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Find organization by document number
     */
    public Optional<Organizacion> buscarPorDocumento(String numeroDocumento) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            organizacionDAO.setEntityManager(em);
            return organizacionDAO.findByNumeroDocumento(numeroDocumento);
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Find all approved organizations
     */
    public List<Organizacion> listarAprobadas() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            organizacionDAO.setEntityManager(em);
            return organizacionDAO.findAprobadas();
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Find pending approval organizations
     */
    public List<Organizacion> listarPendientes() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            organizacionDAO.setEntityManager(em);
            return organizacionDAO.findPendientes();
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Find organizations by country
     */
    public List<Organizacion> listarPorPais(String pais) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            organizacionDAO.setEntityManager(em);
            return organizacionDAO.findByPais(pais);
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Search organizations by name
     */
    public List<Organizacion> buscarPorNombre(String nombre) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            organizacionDAO.setEntityManager(em);
            return organizacionDAO.searchByNombre(nombre);
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Update organization
     */
    public Organizacion actualizar(Organizacion organizacion) {
        return JPAUtil.executeInTransactionWithResult(em -> {
            organizacionDAO.setEntityManager(em);
            
            // Validate document uniqueness if changed
            if (organizacion.getNumeroDocumento() != null) {
                Optional<Organizacion> existing = organizacionDAO.findByNumeroDocumento(
                        organizacion.getNumeroDocumento());
                if (existing.isPresent() && !existing.get().getId().equals(organizacion.getId())) {
                    throw new IllegalArgumentException("Document number already exists");
                }
            }
            
            Organizacion updated = organizacionDAO.update(organizacion);
            logger.info("Organization updated: {}", updated.getId());
            return updated;
        });
    }

    /**
     * Approve organization (admin action)
     */
    public void aprobar(Long organizacionId, Usuario admin) {
        JPAUtil.executeInTransaction(em -> {
            organizacionDAO.setEntityManager(em);
            
            if (!admin.isAdmin()) {
                throw new IllegalArgumentException("Only administrators can approve organizations");
            }
            
            Organizacion organizacion = organizacionDAO.findById(organizacionId)
                    .orElseThrow(() -> new IllegalArgumentException("Organization not found"));
            
            if (organizacion.isAprobada()) {
                throw new IllegalStateException("Organization is already approved");
            }
            
            organizacion.aprobar(admin);
            organizacionDAO.update(organizacion);
            
            logger.info("Organization approved: {} by admin {}", organizacionId, admin.getEmail());
        });
    }

    /**
     * Reject organization (admin action)
     */
    public void rechazar(Long organizacionId, Usuario admin, String motivo) {
        JPAUtil.executeInTransaction(em -> {
            organizacionDAO.setEntityManager(em);
            
            if (!admin.isAdmin()) {
                throw new IllegalArgumentException("Only administrators can reject organizations");
            }
            
            Organizacion organizacion = organizacionDAO.findById(organizacionId)
                    .orElseThrow(() -> new IllegalArgumentException("Organization not found"));
            
            if (motivo == null || motivo.trim().isEmpty()) {
                throw new IllegalArgumentException("Rejection reason is required");
            }
            
            organizacion.rechazar(admin, motivo);
            organizacionDAO.update(organizacion);
            
            logger.info("Organization rejected: {} by admin {}", organizacionId, admin.getEmail());
        });
    }

    /**
     * Check if organization is approved
     */
    public boolean estaAprobada(Long organizacionId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            organizacionDAO.setEntityManager(em);
            Organizacion organizacion = organizacionDAO.findById(organizacionId)
                    .orElseThrow(() -> new IllegalArgumentException("Organization not found"));
            return organizacion.isAprobada();
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Delete organization
     */
    public void eliminar(Long organizacionId) {
        JPAUtil.executeInTransaction(em -> {
            organizacionDAO.setEntityManager(em);
            
            Organizacion organizacion = organizacionDAO.findById(organizacionId)
                    .orElseThrow(() -> new IllegalArgumentException("Organization not found"));
            
            // Check if has active events (optional business rule)
            // Could add validation here
            
            organizacionDAO.delete(organizacion);
            logger.info("Organization deleted: {}", organizacionId);
        });
    }
}
