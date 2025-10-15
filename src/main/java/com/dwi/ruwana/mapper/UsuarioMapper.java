package com.dwi.ruwana.mapper;

import com.dwi.ruwana.dto.UsuarioDTO;
import com.dwi.ruwana.model.Usuario;

/**
 * Mapper for Usuario entity and UsuarioDTO
 */
public class UsuarioMapper {
    
    /**
     * Convert Usuario entity to UsuarioDTO
     */
    public static UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setEmail(usuario.getEmail());
        dto.setNombres(usuario.getNombres());
        dto.setApellidoPaterno(usuario.getApellidoPaterno());
        dto.setApellidoMaterno(usuario.getApellidoMaterno());
        dto.setRol(usuario.getRol());
        dto.setEstadoActivo(usuario.getEstadoActivo());
        dto.setEstadoVerificado(usuario.getEstadoVerificado());
        dto.setUltimoLogin(usuario.getUltimoLogin());
        
        return dto;
    }
    
    /**
     * Convert UsuarioDTO to Usuario entity
     * Note: Does not include password for security
     */
    public static Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setEmail(dto.getEmail());
        usuario.setNombres(dto.getNombres());
        usuario.setApellidoPaterno(dto.getApellidoPaterno());
        usuario.setApellidoMaterno(dto.getApellidoMaterno());
        usuario.setRol(dto.getRol());
        usuario.setEstadoActivo(dto.getEstadoActivo());
        usuario.setEstadoVerificado(dto.getEstadoVerificado());
        
        return usuario;
    }
    
    /**
     * Update existing Usuario entity with DTO data
     */
    public static void updateEntity(Usuario usuario, UsuarioDTO dto) {
        if (usuario == null || dto == null) {
            return;
        }
        
        if (dto.getEmail() != null) usuario.setEmail(dto.getEmail());
        if (dto.getNombres() != null) usuario.setNombres(dto.getNombres());
        if (dto.getApellidoPaterno() != null) usuario.setApellidoPaterno(dto.getApellidoPaterno());
        if (dto.getApellidoMaterno() != null) usuario.setApellidoMaterno(dto.getApellidoMaterno());
    }
}
