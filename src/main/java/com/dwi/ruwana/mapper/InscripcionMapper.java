package com.dwi.ruwana.mapper;

import com.dwi.ruwana.dto.InscripcionDTO;
import com.dwi.ruwana.model.Inscripcion;

/**
 * Mapper for Inscripcion entity and InscripcionDTO
 */
public class InscripcionMapper {
    
    /**
     * Convert Inscripcion entity to InscripcionDTO
     */
    public static InscripcionDTO toDTO(Inscripcion inscripcion) {
        if (inscripcion == null) {
            return null;
        }
        
        InscripcionDTO dto = new InscripcionDTO();
        dto.setId(inscripcion.getId());
        dto.setEstado(inscripcion.getEstado());
        dto.setFechaInscripcion(inscripcion.getFechaInscripcion());
        dto.setFechaConfirmacion(inscripcion.getFechaConfirmacion());
        dto.setCertificadoGenerado(inscripcion.getCertificadoGenerado());
        
        // Volunteer info
        if (inscripcion.getVoluntario() != null) {
            dto.setVoluntarioId(inscripcion.getVoluntario().getId());
            if (inscripcion.getVoluntario().getUsuario() != null) {
                dto.setNombreVoluntario(inscripcion.getVoluntario().getUsuario().getNombreCompleto());
                dto.setEmailVoluntario(inscripcion.getVoluntario().getUsuario().getEmail());
            }
        }
        
        // Event info
        if (inscripcion.getEvento() != null) {
            dto.setEventoId(inscripcion.getEvento().getId());
            dto.setNombreEvento(inscripcion.getEvento().getNombre());
            dto.setFechaInicioEvento(inscripcion.getEvento().getFechaInicio().toString());
            dto.setFechaFinEvento(inscripcion.getEvento().getFechaFin().toString());
            dto.setLugarEvento(inscripcion.getEvento().getLugar());
        }
        
        return dto;
    }
    
    /**
     * Convert InscripcionDTO to Inscripcion entity (basic data only)
     */
    public static Inscripcion toEntity(InscripcionDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setId(dto.getId());
        inscripcion.setEstado(dto.getEstado());
        inscripcion.setFechaInscripcion(dto.getFechaInscripcion());
        inscripcion.setFechaConfirmacion(dto.getFechaConfirmacion());
        inscripcion.setCertificadoGenerado(dto.getCertificadoGenerado());
        
        return inscripcion;
    }
}
