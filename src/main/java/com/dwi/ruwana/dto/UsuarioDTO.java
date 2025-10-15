package com.dwi.ruwana.dto;

import com.dwi.ruwana.model.enums.RolUsuario;
import java.time.LocalDateTime;

/**
 * DTO for Usuario entity
 * Used to transfer user data without exposing sensitive information
 */
public class UsuarioDTO {
    
    private Long id;
    private String email;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono;
    private RolUsuario rol;
    private Boolean estadoActivo;
    private Boolean estadoVerificado;
    private LocalDateTime fechaRegistro;
    private LocalDateTime ultimoLogin;
    private String avatarUrl;

    // Constructors
    public UsuarioDTO() {}

    public UsuarioDTO(Long id, String email, String nombres, String apellidoPaterno, 
                     String apellidoMaterno, RolUsuario rol) {
        this.id = id;
        this.email = email;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.rol = rol;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }

    public Boolean getEstadoActivo() {
        return estadoActivo;
    }

    public void setEstadoActivo(Boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    public Boolean getEstadoVerificado() {
        return estadoVerificado;
    }

    public void setEstadoVerificado(Boolean estadoVerificado) {
        this.estadoVerificado = estadoVerificado;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public LocalDateTime getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin(LocalDateTime ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getNombreCompleto() {
        StringBuilder sb = new StringBuilder();
        if (nombres != null) sb.append(nombres);
        if (apellidoPaterno != null) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(apellidoPaterno);
        }
        if (apellidoMaterno != null) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(apellidoMaterno);
        }
        return sb.toString();
    }
}
