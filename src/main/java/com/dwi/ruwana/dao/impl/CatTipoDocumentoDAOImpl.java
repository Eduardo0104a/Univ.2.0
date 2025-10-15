package com.dwi.ruwana.dao.impl;

import com.dwi.ruwana.model.catalogo.CatTipoDocumento;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * DAO implementation for CatTipoDocumento entity
 */
public class CatTipoDocumentoDAOImpl extends CatalogoDAOImpl<CatTipoDocumento> {
    
    public CatTipoDocumentoDAOImpl() {
        super(CatTipoDocumento.class);
    }
    
    /**
     * Find document types by country
     */
    public List<CatTipoDocumento> findByPais(String pais) {
        try {
            TypedQuery<CatTipoDocumento> query = getEntityManager()
                    .createQuery("SELECT c FROM CatTipoDocumento c WHERE c.pais = :pais AND c.activo = true ORDER BY c.orden", 
                            CatTipoDocumento.class);
            query.setParameter("pais", pais);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding document types by country: {}", pais, e);
            throw new RuntimeException("Error finding document types by country", e);
        }
    }
}
