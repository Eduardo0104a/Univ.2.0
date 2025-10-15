package com.dwi.ruwana.mapper;

import com.dwi.ruwana.dto.EventoDTO;
import com.dwi.ruwana.model.Evento;

/**
 * Mapper for Evento entity and EventoDTO
 */
public class EventoMapper {
    
    /**
     * Convert Evento entity to EventoDTO
     */
    public static EventoDTO toDTO(Evento evento) {
        if (evento == null) {
            return null;
        }
        
        EventoDTO dto = new EventoDTO();
        dto.setId(evento.getId());
        dto.setNombre(evento.getNombre());
        dto.setDescripcion(evento.getDescripcion());
        dto.setFechaInicio(evento.getFechaInicio());
        dto.setFechaFin(evento.getFechaFin());
        dto.setLugar(evento.getLugar());
        dto.setDireccion(evento.getDireccion());
        dto.setCuposMaximos(evento.getCuposMaximos());
        dto.setCuposDisponibles(evento.getCuposDisponibles());
        dto.setCuposIlimitados(!evento.tieneLimiteCupos());
        dto.setEstado(evento.getEstado());
        dto.setFechaCreacion(evento.getFechaCreacion());
        dto.setMotivoRechazo(evento.getMotivoRechazo());
        
        // Organization info
        if (evento.getOrganizacion() != null) {
            dto.setOrganizacionId(evento.getOrganizacion().getId());
            dto.setNombreOrganizacion(evento.getOrganizacion().getNombreOrganizacion());
        }
        
        // Approval info
        dto.setFechaAprobacion(evento.getFechaAprobacion());
        
        return dto;
    }
    
    /**
     * Convert EventoDTO to Evento entity (basic data only)
     */
    public static Evento toEntity(EventoDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Evento evento = new Evento();
        evento.setId(dto.getId());
        evento.setNombre(dto.getNombre());
        evento.setDescripcion(dto.getDescripcion());
        evento.setFechaInicio(dto.getFechaInicio());
        evento.setFechaFin(dto.getFechaFin());
        evento.setLugar(dto.getLugar());
        evento.setDireccion(dto.getDireccion());
        evento.setCuposMaximos(dto.getCuposMaximos());
        evento.setCuposDisponibles(dto.getCuposDisponibles());
        evento.setEstado(dto.getEstado());
        
        return evento;
    }
    
    /**
     * Update existing Evento entity with DTO data
     */
    public static void updateEntity(Evento evento, EventoDTO dto) {
        if (evento == null || dto == null) {
            return;
        }
        
        if (dto.getNombre() != null) evento.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) evento.setDescripcion(dto.getDescripcion());
        if (dto.getFechaInicio() != null) evento.setFechaInicio(dto.getFechaInicio());
        if (dto.getFechaFin() != null) evento.setFechaFin(dto.getFechaFin());
        if (dto.getLugar() != null) evento.setLugar(dto.getLugar());
        if (dto.getDireccion() != null) evento.setDireccion(dto.getDireccion());
        if (dto.getCuposMaximos() != null) evento.setCuposMaximos(dto.getCuposMaximos());
    }
}
