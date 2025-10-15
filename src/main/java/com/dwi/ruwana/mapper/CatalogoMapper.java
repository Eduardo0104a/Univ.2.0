package com.dwi.ruwana.mapper;

import com.dwi.ruwana.dto.CatalogoDTO;
import com.dwi.ruwana.model.catalogo.*;

/**
 * Generic mapper for catalog entities
 */
public class CatalogoMapper {
    
    /**
     * Convert CatGenero to CatalogoDTO
     */
    public static CatalogoDTO toDTO(CatGenero genero) {
        if (genero == null) return null;
        
        CatalogoDTO dto = new CatalogoDTO();
        dto.setId(genero.getId());
        dto.setCodigo(genero.getCodigo());
        dto.setNombre(genero.getNombre());
        dto.setDescripcion(genero.getDescripcion());
        dto.setOrden(genero.getOrden());
        dto.setActivo(genero.getActivo());
        return dto;
    }
    
    /**
     * Convert CatEstadoCivil to CatalogoDTO
     */
    public static CatalogoDTO toDTO(CatEstadoCivil estadoCivil) {
        if (estadoCivil == null) return null;
        
        CatalogoDTO dto = new CatalogoDTO();
        dto.setId(estadoCivil.getId());
        dto.setCodigo(estadoCivil.getCodigo());
        dto.setNombre(estadoCivil.getNombre());
        dto.setDescripcion(estadoCivil.getDescripcion());
        dto.setOrden(estadoCivil.getOrden());
        dto.setActivo(estadoCivil.getActivo());
        return dto;
    }
    
    /**
     * Convert CatTipoDocumento to CatalogoDTO
     */
    public static CatalogoDTO toDTO(CatTipoDocumento tipoDocumento) {
        if (tipoDocumento == null) return null;
        
        CatalogoDTO dto = new CatalogoDTO();
        dto.setId(tipoDocumento.getId());
        dto.setCodigo(tipoDocumento.getCodigo());
        dto.setNombre(tipoDocumento.getNombre());
        dto.setDescripcion(tipoDocumento.getDescripcion());
        dto.setOrden(tipoDocumento.getOrden());
        dto.setActivo(tipoDocumento.getActivo());
        return dto;
    }
    
    /**
     * Convert CatGradoInstruccion to CatalogoDTO
     */
    public static CatalogoDTO toDTO(CatGradoInstruccion gradoInstruccion) {
        if (gradoInstruccion == null) return null;
        
        CatalogoDTO dto = new CatalogoDTO();
        dto.setId(gradoInstruccion.getId());
        dto.setCodigo(gradoInstruccion.getCodigo());
        dto.setNombre(gradoInstruccion.getNombre());
        dto.setDescripcion(gradoInstruccion.getDescripcion());
        dto.setOrden(gradoInstruccion.getOrden());
        dto.setActivo(gradoInstruccion.getActivo());
        return dto;
    }
    
    /**
     * Convert CatCentroEstudios to CatalogoDTO
     */
    public static CatalogoDTO toDTO(CatCentroEstudios centroEstudios) {
        if (centroEstudios == null) return null;
        
        CatalogoDTO dto = new CatalogoDTO();
        dto.setId(centroEstudios.getId());
        dto.setCodigo(centroEstudios.getCodigo());
        dto.setNombre(centroEstudios.getNombre());
        dto.setDescripcion(centroEstudios.getDescripcion());
        dto.setOrden(centroEstudios.getOrden());
        dto.setActivo(centroEstudios.getActivo());
        return dto;
    }
}
