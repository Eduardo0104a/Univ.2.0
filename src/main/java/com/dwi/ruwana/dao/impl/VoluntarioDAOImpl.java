package com.dwi.ruwana.dao.impl;

import com.dwi.ruwana.dao.VoluntarioDAO;
import com.dwi.ruwana.model.Voluntario;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

/**
 * DAO implementation for Voluntario entity
 */
public class VoluntarioDAOImpl extends GenericDAOImpl<Voluntario, Long> implements VoluntarioDAO {

    public VoluntarioDAOImpl() {
        super(Voluntario.class);
    }

    @Override
    public Optional<Voluntario> findByUsuarioId(Long usuarioId) {
        try {
            TypedQuery<Voluntario> query = getEntityManager()
                    .createQuery("SELECT v FROM Voluntario v WHERE v.usuario.id = :usuarioId", Voluntario.class);
            query.setParameter("usuarioId", usuarioId);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (Exception e) {
            logger.error("Error finding volunteer by user ID: {}", usuarioId, e);
            throw new RuntimeException("Error finding volunteer by user ID", e);
        }
    }

    @Override
    public Optional<Voluntario> findByNumeroDocumento(String numeroDocumento) {
        try {
            TypedQuery<Voluntario> query = getEntityManager()
                    .createQuery("SELECT v FROM Voluntario v WHERE v.numeroDocumento = :numero", Voluntario.class);
            query.setParameter("numero", numeroDocumento);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (Exception e) {
            logger.error("Error finding volunteer by document number: {}", numeroDocumento, e);
            throw new RuntimeException("Error finding volunteer by document number", e);
        }
    }

    @Override
    public List<Voluntario> findByGradoInstruccionId(Integer gradoInstruccionId) {
        try {
            TypedQuery<Voluntario> query = getEntityManager()
                    .createQuery("SELECT v FROM Voluntario v WHERE v.gradoInstruccion.id = :gradoId", 
                            Voluntario.class);
            query.setParameter("gradoId", gradoInstruccionId);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding volunteers by education level: {}", gradoInstruccionId, e);
            throw new RuntimeException("Error finding volunteers by education level", e);
        }
    }

    @Override
    public List<Voluntario> findByCentroEstudiosId(Integer centroEstudiosId) {
        try {
            TypedQuery<Voluntario> query = getEntityManager()
                    .createQuery("SELECT v FROM Voluntario v WHERE v.centroEstudios.id = :centroId", 
                            Voluntario.class);
            query.setParameter("centroId", centroEstudiosId);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding volunteers by study center: {}", centroEstudiosId, e);
            throw new RuntimeException("Error finding volunteers by study center", e);
        }
    }

    @Override
    public List<Voluntario> findWithCorreoInstitucional() {
        try {
            TypedQuery<Voluntario> query = getEntityManager()
                    .createQuery("SELECT v FROM Voluntario v WHERE v.correoInstitucional IS NOT NULL", 
                            Voluntario.class);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding volunteers with institutional email", e);
            throw new RuntimeException("Error finding volunteers with institutional email", e);
        }
    }

    @Override
    public long countByGeneroId(Integer generoId) {
        try {
            TypedQuery<Long> query = getEntityManager()
                    .createQuery("SELECT COUNT(v) FROM Voluntario v WHERE v.genero.id = :generoId", Long.class);
            query.setParameter("generoId", generoId);
            return query.getSingleResult();
        } catch (Exception e) {
            logger.error("Error counting volunteers by gender: {}", generoId, e);
            throw new RuntimeException("Error counting volunteers by gender", e);
        }
    }
}
