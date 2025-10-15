package com.dwi.ruwana.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Certification entity
 */
@Entity
@Table(name = "certificaciones")
public class Certificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inscripcion_id", nullable = false, unique = true)
    private Inscripcion inscripcion;

    @Column(name = "codigo_verificacion", unique = true, nullable = false, length = 100)
    private String codigoVerificacion;

    @Column(name = "horas_voluntariado")
    private Integer horasVoluntariado = 0;

    @Column(name = "descripcion_actividades", columnDefinition = "TEXT")
    private String descripcionActividades;

    @Column(name = "archivo_url", length = 500)
    private String archivoUrl;

    @Column(length = 10)
    private String formato = "PDF";

    @Column(name = "fecha_emision", nullable = false, updatable = false)
    private LocalDateTime fechaEmision;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emitido_por")
    private Usuario emitidoPor;

    @Column(length = 20)
    private String estado = "GENERADO";

    // Constructors
    public Certificacion() {
    }

    public Certificacion(Inscripcion inscripcion, Integer horasVoluntariado) {
        this.inscripcion = inscripcion;
        this.horasVoluntariado = horasVoluntariado;
        this.codigoVerificacion = generarCodigoVerificacion();
    }

    // Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        fechaEmision = LocalDateTime.now();
        if (codigoVerificacion == null) {
            codigoVerificacion = generarCodigoVerificacion();
        }
    }

    // Helper methods
    private String generarCodigoVerificacion() {
        return "CERT-" + LocalDateTime.now().getYear() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Inscripcion getInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(Inscripcion inscripcion) {
        this.inscripcion = inscripcion;
    }

    public String getCodigoVerificacion() {
        return codigoVerificacion;
    }

    public void setCodigoVerificacion(String codigoVerificacion) {
        this.codigoVerificacion = codigoVerificacion;
    }

    public Integer getHorasVoluntariado() {
        return horasVoluntariado;
    }

    public void setHorasVoluntariado(Integer horasVoluntariado) {
        this.horasVoluntariado = horasVoluntariado;
    }

    public String getDescripcionActividades() {
        return descripcionActividades;
    }

    public void setDescripcionActividades(String descripcionActividades) {
        this.descripcionActividades = descripcionActividades;
    }

    public String getArchivoUrl() {
        return archivoUrl;
    }

    public void setArchivoUrl(String archivoUrl) {
        this.archivoUrl = archivoUrl;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Usuario getEmitidoPor() {
        return emitidoPor;
    }

    public void setEmitidoPor(Usuario emitidoPor) {
        this.emitidoPor = emitidoPor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Certificacion)) return false;
        Certificacion that = (Certificacion) o;
        return Objects.equals(id, that.id) && Objects.equals(codigoVerificacion, that.codigoVerificacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigoVerificacion);
    }

    @Override
    public String toString() {
        return "Certificacion{" +
                "id=" + id +
                ", codigoVerificacion='" + codigoVerificacion + '\'' +
                ", horasVoluntariado=" + horasVoluntariado +
                '}';
    }
}
