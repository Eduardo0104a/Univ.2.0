package com.dwi.ruwana.service;

import com.dwi.ruwana.dao.impl.*;
import com.dwi.ruwana.model.catalogo.*;
import com.dwi.ruwana.util.JPAUtil;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for catalog entities
 * Provides access to catalog data for dropdowns and forms
 */
public class CatalogoService {
    
    private static final Logger logger = LoggerFactory.getLogger(CatalogoService.class);
    private final CatGeneroDAOImpl generoDAO;
    private final CatEstadoCivilDAOImpl estadoCivilDAO;
    private final CatTipoDocumentoDAOImpl tipoDocumentoDAO;
    private final CatGradoInstruccionDAOImpl gradoInstruccionDAO;
    private final CatCentroEstudiosDAOImpl centroEstudiosDAO;

    public CatalogoService() {
        this.generoDAO = new CatGeneroDAOImpl();
        this.estadoCivilDAO = new CatEstadoCivilDAOImpl();
        this.tipoDocumentoDAO = new CatTipoDocumentoDAOImpl();
        this.gradoInstruccionDAO = new CatGradoInstruccionDAOImpl();
        this.centroEstudiosDAO = new CatCentroEstudiosDAOImpl();
    }

    // ========== GENERO ==========
    
    public List<CatGenero> listarGenerosActivos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            generoDAO.setEntityManager(em);
            return generoDAO.findActivos();
        } finally {
            JPAUtil.close(em);
        }
    }

    public Optional<CatGenero> buscarGeneroPorId(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            generoDAO.setEntityManager(em);
            return generoDAO.findById(id);
        } finally {
            JPAUtil.close(em);
        }
    }

    public Optional<CatGenero> buscarGeneroPorCodigo(String codigo) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            generoDAO.setEntityManager(em);
            return generoDAO.findByCodigo(codigo);
        } finally {
            JPAUtil.close(em);
        }
    }

    // ========== ESTADO CIVIL ==========
    
    public List<CatEstadoCivil> listarEstadosCivilesActivos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            estadoCivilDAO.setEntityManager(em);
            return estadoCivilDAO.findActivos();
        } finally {
            JPAUtil.close(em);
        }
    }

    public Optional<CatEstadoCivil> buscarEstadoCivilPorId(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            estadoCivilDAO.setEntityManager(em);
            return estadoCivilDAO.findById(id);
        } finally {
            JPAUtil.close(em);
        }
    }

    public Optional<CatEstadoCivil> buscarEstadoCivilPorCodigo(String codigo) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            estadoCivilDAO.setEntityManager(em);
            return estadoCivilDAO.findByCodigo(codigo);
        } finally {
            JPAUtil.close(em);
        }
    }

    // ========== TIPO DOCUMENTO ==========
    
    public List<CatTipoDocumento> listarTiposDocumentoActivos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            tipoDocumentoDAO.setEntityManager(em);
            return tipoDocumentoDAO.findActivos();
        } finally {
            JPAUtil.close(em);
        }
    }

    public List<CatTipoDocumento> listarTiposDocumentoPorPais(String pais) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            tipoDocumentoDAO.setEntityManager(em);
            return tipoDocumentoDAO.findByPais(pais);
        } finally {
            JPAUtil.close(em);
        }
    }

    public Optional<CatTipoDocumento> buscarTipoDocumentoPorId(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            tipoDocumentoDAO.setEntityManager(em);
            return tipoDocumentoDAO.findById(id);
        } finally {
            JPAUtil.close(em);
        }
    }

    public Optional<CatTipoDocumento> buscarTipoDocumentoPorCodigo(String codigo) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            tipoDocumentoDAO.setEntityManager(em);
            return tipoDocumentoDAO.findByCodigo(codigo);
        } finally {
            JPAUtil.close(em);
        }
    }

    // ========== GRADO INSTRUCCION ==========
    
    public List<CatGradoInstruccion> listarGradosInstruccionActivos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            gradoInstruccionDAO.setEntityManager(em);
            return gradoInstruccionDAO.findActivos();
        } finally {
            JPAUtil.close(em);
        }
    }

    public Optional<CatGradoInstruccion> buscarGradoInstruccionPorId(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            gradoInstruccionDAO.setEntityManager(em);
            return gradoInstruccionDAO.findById(id);
        } finally {
            JPAUtil.close(em);
        }
    }

    public Optional<CatGradoInstruccion> buscarGradoInstruccionPorCodigo(String codigo) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            gradoInstruccionDAO.setEntityManager(em);
            return gradoInstruccionDAO.findByCodigo(codigo);
        } finally {
            JPAUtil.close(em);
        }
    }

    // ========== CENTRO ESTUDIOS ==========
    
    public List<CatCentroEstudios> listarCentrosEstudiosActivos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            centroEstudiosDAO.setEntityManager(em);
            return centroEstudiosDAO.findActivos();
        } finally {
            JPAUtil.close(em);
        }
    }

    public Optional<CatCentroEstudios> buscarCentroEstudiosPorId(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            centroEstudiosDAO.setEntityManager(em);
            return centroEstudiosDAO.findById(id);
        } finally {
            JPAUtil.close(em);
        }
    }

    public Optional<CatCentroEstudios> buscarCentroEstudiosPorCodigo(String codigo) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            centroEstudiosDAO.setEntityManager(em);
            return centroEstudiosDAO.findByCodigo(codigo);
        } finally {
            JPAUtil.close(em);
        }
    }

    // ========== ADMIN METHODS ==========
    
    /**
     * Create a new catalog entry (generic)
     */
    public <T> T crearCatalogo(T catalogoEntry) {
        return JPAUtil.executeInTransactionWithResult(em -> {
            em.persist(catalogoEntry);
            logger.info("Catalog entry created: {}", catalogoEntry);
            return catalogoEntry;
        });
    }

    /**
     * Update a catalog entry (generic)
     */
    public <T> T actualizarCatalogo(T catalogoEntry) {
        return JPAUtil.executeInTransactionWithResult(em -> {
            T updated = em.merge(catalogoEntry);
            logger.info("Catalog entry updated: {}", updated);
            return updated;
        });
    }
}
