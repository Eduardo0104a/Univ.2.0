package com.dwi.ruwana.service;

import com.dwi.ruwana.dao.UsuarioDAO;
import com.dwi.ruwana.dao.VoluntarioDAO;
import com.dwi.ruwana.dao.impl.UsuarioDAOImpl;
import com.dwi.ruwana.dao.impl.VoluntarioDAOImpl;
import com.dwi.ruwana.model.Usuario;
import com.dwi.ruwana.model.Voluntario;
import com.dwi.ruwana.model.enums.RolUsuario;
import com.dwi.ruwana.util.JPAUtil;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for Voluntario entity
 */
public class VoluntarioService {
    
    private static final Logger logger = LoggerFactory.getLogger(VoluntarioService.class);
    private final VoluntarioDAO voluntarioDAO;
    private final UsuarioDAO usuarioDAO;
    private final UsuarioService usuarioService;

    public VoluntarioService() {
        this.voluntarioDAO = new VoluntarioDAOImpl();
        this.usuarioDAO = new UsuarioDAOImpl();
        this.usuarioService = new UsuarioService();
    }

    /**
     * Register a new volunteer with user account
     */
    public Voluntario registrar(Usuario usuario, String password, Voluntario voluntario) {
        return JPAUtil.executeInTransactionWithResult(em -> {
            voluntarioDAO.setEntityManager(em);
            usuarioDAO.setEntityManager(em);
            
            // Validate email doesn't exist
            if (usuarioDAO.existsByEmail(usuario.getEmail())) {
                throw new IllegalArgumentException("Email already exists");
            }
            
            // Validate document doesn't exist
            if (voluntario.getNumeroDocumento() != null) {
                if (voluntarioDAO.findByNumeroDocumento(voluntario.getNumeroDocumento()).isPresent()) {
                    throw new IllegalArgumentException("Document number already exists");
                }
            }
            
            // Set user role
            usuario.setRol(RolUsuario.VOLUNTARIO);
            
            // Register user using UsuarioService (handles password hashing)
            Usuario savedUser = usuarioService.registrar(usuario, password);
            
            // Associate volunteer with user
            voluntario.setUsuario(savedUser);
            
            Voluntario saved = voluntarioDAO.save(voluntario);
            logger.info("Volunteer registered: {} - {}", saved.getId(), savedUser.getEmail());
            return saved;
        });
    }

    /**
     * Find volunteer by ID
     */
    public Optional<Voluntario> buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            voluntarioDAO.setEntityManager(em);
            return voluntarioDAO.findById(id);
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Find volunteer by user ID
     */
    public Optional<Voluntario> buscarPorUsuarioId(Long usuarioId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            voluntarioDAO.setEntityManager(em);
            return voluntarioDAO.findByUsuarioId(usuarioId);
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Find volunteer by document number
     */
    public Optional<Voluntario> buscarPorDocumento(String numeroDocumento) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            voluntarioDAO.setEntityManager(em);
            return voluntarioDAO.findByNumeroDocumento(numeroDocumento);
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Find all volunteers
     */
    public List<Voluntario> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            voluntarioDAO.setEntityManager(em);
            return voluntarioDAO.findAll();
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Find volunteers by education level
     */
    public List<Voluntario> listarPorGradoInstruccion(Integer gradoInstruccionId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            voluntarioDAO.setEntityManager(em);
            return voluntarioDAO.findByGradoInstruccionId(gradoInstruccionId);
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Find volunteers by study center type
     */
    public List<Voluntario> listarPorCentroEstudios(Integer centroEstudiosId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            voluntarioDAO.setEntityManager(em);
            return voluntarioDAO.findByCentroEstudiosId(centroEstudiosId);
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Find volunteers with institutional email
     */
    public List<Voluntario> listarConCorreoInstitucional() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            voluntarioDAO.setEntityManager(em);
            return voluntarioDAO.findWithCorreoInstitucional();
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Update volunteer
     */
    public Voluntario actualizar(Voluntario voluntario) {
        return JPAUtil.executeInTransactionWithResult(em -> {
            voluntarioDAO.setEntityManager(em);
            
            // Validate document uniqueness if changed
            if (voluntario.getNumeroDocumento() != null) {
                Optional<Voluntario> existing = voluntarioDAO.findByNumeroDocumento(
                        voluntario.getNumeroDocumento());
                if (existing.isPresent() && !existing.get().getId().equals(voluntario.getId())) {
                    throw new IllegalArgumentException("Document number already exists");
                }
            }
            
            Voluntario updated = voluntarioDAO.update(voluntario);
            logger.info("Volunteer updated: {}", updated.getId());
            return updated;
        });
    }

    /**
     * Update volunteer profile (personal data)
     */
    public Voluntario actualizarPerfil(Long voluntarioId, Voluntario datosActualizados) {
        return JPAUtil.executeInTransactionWithResult(em -> {
            voluntarioDAO.setEntityManager(em);
            
            Voluntario voluntario = voluntarioDAO.findById(voluntarioId)
                    .orElseThrow(() -> new IllegalArgumentException("Volunteer not found"));
            
            // Update personal data
            if (datosActualizados.getFechaNacimiento() != null) {
                voluntario.setFechaNacimiento(datosActualizados.getFechaNacimiento());
            }
            if (datosActualizados.getEstadoCivil() != null) {
                voluntario.setEstadoCivil(datosActualizados.getEstadoCivil());
            }
            if (datosActualizados.getGenero() != null) {
                voluntario.setGenero(datosActualizados.getGenero());
            }
            if (datosActualizados.getNacionalidad() != null) {
                voluntario.setNacionalidad(datosActualizados.getNacionalidad());
            }
            
            // Update educational data
            if (datosActualizados.getGradoInstruccion() != null) {
                voluntario.setGradoInstruccion(datosActualizados.getGradoInstruccion());
            }
            if (datosActualizados.getCentroEstudios() != null) {
                voluntario.setCentroEstudios(datosActualizados.getCentroEstudios());
            }
            if (datosActualizados.getNombreCentroEstudios() != null) {
                voluntario.setNombreCentroEstudios(datosActualizados.getNombreCentroEstudios());
            }
            if (datosActualizados.getCorreoInstitucional() != null) {
                voluntario.setCorreoInstitucional(datosActualizados.getCorreoInstitucional());
            }
            if (datosActualizados.getCarrera() != null) {
                voluntario.setCarrera(datosActualizados.getCarrera());
            }
            
            Voluntario updated = voluntarioDAO.update(voluntario);
            logger.info("Volunteer profile updated: {}", updated.getId());
            return updated;
        });
    }

    /**
     * Count volunteers by gender
     */
    public long contarPorGenero(Integer generoId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            voluntarioDAO.setEntityManager(em);
            return voluntarioDAO.countByGeneroId(generoId);
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Get volunteer statistics
     */
    public VoluntarioStats obtenerEstadisticas(Long voluntarioId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            voluntarioDAO.setEntityManager(em);
            
            Voluntario voluntario = voluntarioDAO.findById(voluntarioId)
                    .orElseThrow(() -> new IllegalArgumentException("Volunteer not found"));
            
            int totalInscripciones = voluntario.getInscripciones().size();
            long asistencias = voluntario.getInscripciones().stream()
                    .filter(i -> i.asistio())
                    .count();
            
            return new VoluntarioStats(voluntarioId, totalInscripciones, (int) asistencias);
        } finally {
            JPAUtil.close(em);
        }
    }

    /**
     * Delete volunteer
     */
    public void eliminar(Long voluntarioId) {
        JPAUtil.executeInTransaction(em -> {
            voluntarioDAO.setEntityManager(em);
            
            Voluntario voluntario = voluntarioDAO.findById(voluntarioId)
                    .orElseThrow(() -> new IllegalArgumentException("Volunteer not found"));
            
            // Check if has active registrations (optional business rule)
            long activeRegistrations = voluntario.getInscripciones().stream()
                    .filter(i -> i.isActiva())
                    .count();
            
            if (activeRegistrations > 0) {
                throw new IllegalStateException("Cannot delete volunteer with active registrations");
            }
            
            voluntarioDAO.delete(voluntario);
            logger.info("Volunteer deleted: {}", voluntarioId);
        });
    }

    /**
     * Inner class for volunteer statistics
     */
    public static class VoluntarioStats {
        private final Long voluntarioId;
        private final int totalInscripciones;
        private final int totalAsistencias;

        public VoluntarioStats(Long voluntarioId, int totalInscripciones, int totalAsistencias) {
            this.voluntarioId = voluntarioId;
            this.totalInscripciones = totalInscripciones;
            this.totalAsistencias = totalAsistencias;
        }

        public Long getVoluntarioId() {
            return voluntarioId;
        }

        public int getTotalInscripciones() {
            return totalInscripciones;
        }

        public int getTotalAsistencias() {
            return totalAsistencias;
        }

        public double getTasaAsistencia() {
            return totalInscripciones > 0 ? (double) totalAsistencias / totalInscripciones * 100 : 0;
        }
    }
}
