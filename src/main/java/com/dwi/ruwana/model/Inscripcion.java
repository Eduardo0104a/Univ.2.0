package com.dwi.ruwana.model;

import com.dwi.ruwana.model.enums.EstadoInscripcion;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Registration entity (N:M relationship between Volunteer and Event)
 */
@Entity
@Table(name = "inscripciones",
       uniqueConstraints = @UniqueConstraint(columnNames = {"voluntario_id", "evento_id"}))
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voluntario_id", nullable = false)
    private Voluntario voluntario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    // Registration status
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoInscripcion estado = EstadoInscripcion.INSCRITO;

    // Confirmation dates
    @Column(name = "fecha_inscripcion", nullable = false, updatable = false)
    private LocalDateTime fechaInscripcion;

    @Column(name = "fecha_confirmacion")
    private LocalDateTime fechaConfirmacion;

    @Column(name = "fecha_asistencia")
    private LocalDateTime fechaAsistencia;

    // Certification
    @Column(name = "certificado_generado")
    private Boolean certificadoGenerado = false;

    @Column(name = "fecha_certificado")
    private LocalDateTime fechaCertificado;

    // Notes
    @Column(columnDefinition = "TEXT")
    private String observaciones;

    // Relationship
    @OneToOne(mappedBy = "inscripcion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Certificacion certificacion;

    // Constructors
    public Inscripcion() {
    }

    public Inscripcion(Voluntario voluntario, Evento evento) {
        this.voluntario = voluntario;
        this.evento = evento;
    }

    // Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        fechaInscripcion = LocalDateTime.now();
        if (estado == null) {
            estado = EstadoInscripcion.INSCRITO;
        }
        if (certificadoGenerado == null) {
            certificadoGenerado = false;
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Voluntario getVoluntario() {
        return voluntario;
    }

    public void setVoluntario(Voluntario voluntario) {
        this.voluntario = voluntario;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
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

    public LocalDateTime getFechaAsistencia() {
        return fechaAsistencia;
    }

    public void setFechaAsistencia(LocalDateTime fechaAsistencia) {
        this.fechaAsistencia = fechaAsistencia;
    }

    public Boolean getCertificadoGenerado() {
        return certificadoGenerado;
    }

    public void setCertificadoGenerado(Boolean certificadoGenerado) {
        this.certificadoGenerado = certificadoGenerado;
    }

    public LocalDateTime getFechaCertificado() {
        return fechaCertificado;
    }

    public void setFechaCertificado(LocalDateTime fechaCertificado) {
        this.fechaCertificado = fechaCertificado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Certificacion getCertificacion() {
        return certificacion;
    }

    public void setCertificacion(Certificacion certificacion) {
        this.certificacion = certificacion;
    }

    // Helper methods
    public void confirmar() {
        this.estado = EstadoInscripcion.CONFIRMADO;
        this.fechaConfirmacion = LocalDateTime.now();
    }

    public void marcarAsistencia() {
        this.estado = EstadoInscripcion.ASISTIO;
        this.fechaAsistencia = LocalDateTime.now();
    }

    public void marcarNoAsistencia() {
        this.estado = EstadoInscripcion.NO_ASISTIO;
        this.fechaAsistencia = LocalDateTime.now();
    }

    public void cancelar() {
        this.estado = EstadoInscripcion.CANCELADO;
    }

    public boolean isActiva() {
        return estado != EstadoInscripcion.CANCELADO;
    }

    public boolean asistio() {
        return estado == EstadoInscripcion.ASISTIO;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inscripcion)) return false;
        Inscripcion that = (Inscripcion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Inscripcion{" +
                "id=" + id +
                ", voluntario=" + (voluntario != null ? voluntario.getId() : null) +
                ", evento=" + (evento != null ? evento.getId() : null) +
                ", estado=" + estado +
                '}';
    }
}
