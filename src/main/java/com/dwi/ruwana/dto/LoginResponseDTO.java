package com.dwi.ruwana.dto;

import com.dwi.ruwana.model.enums.RolUsuario;

/**
 * DTO for login response
 */
public class LoginResponseDTO {
    
    private Long usuarioId;
    private String email;
    private String nombreCompleto;
    private RolUsuario rol;
    private String token;
    private Long perfilId; // voluntarioId or organizacionId
    private String mensaje;

    public LoginResponseDTO() {}

    public LoginResponseDTO(Long usuarioId, String email, String nombreCompleto, 
                           RolUsuario rol, String token) {
        this.usuarioId = usuarioId;
        this.email = email;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
        this.token = token;
    }

    // Getters and Setters
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(Long perfilId) {
        this.perfilId = perfilId;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
