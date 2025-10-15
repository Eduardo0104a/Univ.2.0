package com.dwi.ruwana.dto;

import com.dwi.ruwana.model.enums.EstadoInscripcion;
import java.time.LocalDateTime;

/**
 * DTO for Inscripcion entity
 */
public class InscripcionDTO {
    
    private Long id;
    private EstadoInscripcion estado;
    private LocalDateTime fechaInscripcion;
    private LocalDateTime fechaConfirmacion;
    private LocalDateTime fechaCancelacion;
    private Boolean certificadoGenerado;
    
    // Volunteer info
    private Long voluntarioId;
    private String nombreVoluntario;
    private String emailVoluntario;
    
    // Event info
    private Long eventoId;
    private String nombreEvento;
    private String fechaInicioEvento;
    private String fechaFinEvento;
    private String lugarEvento;

    // Constructors
    public InscripcionDTO() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EstadoInscripcion getEstado() {
        return estado;
    }

    public void setEstado(EstadoInscripcion estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDateTime fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public LocalDateTime getFechaConfirmacion() {
        return fechaConfirmacion;
    }

    public void setFechaConfirmacion(LocalDateTime fechaConfirmacion) {
        this.fechaConfirmacion = fechaConfirmacion;
    }

    public LocalDateTime getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(LocalDateTime fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    public Boolean getCertificadoGenerado() {
        return certificadoGenerado;
    }

    public void setCertificadoGenerado(Boolean certificadoGenerado) {
        this.certificadoGenerado = certificadoGenerado;
    }

    public Long getVoluntarioId() {
        return voluntarioId;
    }

    public void setVoluntarioId(Long voluntarioId) {
        this.voluntarioId = voluntarioId;
    }

    public String getNombreVoluntario() {
        return nombreVoluntario;
    }

    public void setNombreVoluntario(String nombreVoluntario) {
        this.nombreVoluntario = nombreVoluntario;
    }

    public String getEmailVoluntario() {
        return emailVoluntario;
    }

    public void setEmailVoluntario(String emailVoluntario) {
        this.emailVoluntario = emailVoluntario;
    }

    public Long getEventoId() {
        return eventoId;
    }

    public void setEventoId(Long eventoId) {
        this.eventoId = eventoId;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getFechaInicioEvento() {
        return fechaInicioEvento;
    }

    public void setFechaInicioEvento(String fechaInicioEvento) {
        this.fechaInicioEvento = fechaInicioEvento;
    }

    public String getFechaFinEvento() {
        return fechaFinEvento;
    }

    public void setFechaFinEvento(String fechaFinEvento) {
        this.fechaFinEvento = fechaFinEvento;
    }

    public String getLugarEvento() {
        return lugarEvento;
    }

    public void setLugarEvento(String lugarEvento) {
        this.lugarEvento = lugarEvento;
    }
}
