package com.dwi.ruwana.model;

import com.dwi.ruwana.model.enums.EstadoEvento;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Event entity
 */
@Entity
@Table(name = "eventos")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacion_id", nullable = false)
    private Organizacion organizacion;

    // Basic information
    @Column(nullable = false, length = 255)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "descripcion_html", columnDefinition = "MEDIUMTEXT")
    private String descripcionHtml;

    @Column(name = "informacion_programa", columnDefinition = "TEXT")
    private String informacionPrograma;

    // Dates and schedule
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(length = 255)
    private String horario;

    // Location
    @Column(length = 255)
    private String lugar;

    @Column(columnDefinition = "TEXT")
    private String direccion;

    // Contact
    @Column(name = "telefono_contacto", length = 20)
    private String telefonoContacto;

    @Column(name = "email_contacto", length = 255)
    private String emailContacto;

    // Capacity (NULL or 0 = unlimited)
    @Column(name = "cupos_maximos")
    private Integer cuposMaximos;

    @Column(name = "cupos_disponibles")
    private Integer cuposDisponibles;

    // Multimedia
    @Column(name = "imagen_portada", length = 500)
    private String imagenPortada;

    // Status and approval
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EstadoEvento estado = EstadoEvento.PENDIENTE_APROBACION;

    @Column(name = "fecha_aprobacion")
    private LocalDateTime fechaAprobacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_aprobador_id")
    private Usuario adminAprobador;

    @Column(name = "motivo_rechazo", columnDefinition = "TEXT")
    private String motivoRechazo;

    // Metadata
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "ultima_actualizacion")
    private LocalDateTime ultimaActualizacion;

    // Relationships
    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Inscripcion> inscripciones = new HashSet<>();

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Donacion> donaciones = new HashSet<>();

    // Constructors
    public Evento() {
    }

    public Evento(String nombre, Organizacion organizacion, LocalDate fechaInicio, LocalDate fechaFin) {
        this.nombre = nombre;
        this.organizacion = organizacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        ultimaActualizacion = LocalDateTime.now();
        if (estado == null) {
            estado = EstadoEvento.PENDIENTE_APROBACION;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        ultimaActualizacion = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcionHtml() {
        return descripcionHtml;
    }

    public void setDescripcionHtml(String descripcionHtml) {
        this.descripcionHtml = descripcionHtml;
    }

    public String getInformacionPrograma() {
        return informacionPrograma;
    }

    public void setInformacionPrograma(String informacionPrograma) {
        this.informacionPrograma = informacionPrograma;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getEmailContacto() {
        return emailContacto;
    }

    public void setEmailContacto(String emailContacto) {
        this.emailContacto = emailContacto;
    }

    public Integer getCuposMaximos() {
        return cuposMaximos;
    }

    public void setCuposMaximos(Integer cuposMaximos) {
        this.cuposMaximos = cuposMaximos;
        // If setting max capacity, also set available capacity if not set
        if (cuposMaximos != null && cuposMaximos > 0 && cuposDisponibles == null) {
            this.cuposDisponibles = cuposMaximos;
        }
    }

    public Integer getCuposDisponibles() {
        return cuposDisponibles;
    }

    public void setCuposDisponibles(Integer cuposDisponibles) {
        this.cuposDisponibles = cuposDisponibles;
    }

    public String getImagenPortada() {
        return imagenPortada;
    }

    public void setImagenPortada(String imagenPortada) {
        this.imagenPortada = imagenPortada;
    }

    public EstadoEvento getEstado() {
        return estado;
    }

    public void setEstado(EstadoEvento estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(LocalDateTime fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public Usuario getAdminAprobador() {
        return adminAprobador;
    }

    public void setAdminAprobador(Usuario adminAprobador) {
        this.adminAprobador = adminAprobador;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    public void setUltimaActualizacion(LocalDateTime ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }

    public Set<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(Set<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }

    public Set<Donacion> getDonaciones() {
        return donaciones;
    }

    public void setDonaciones(Set<Donacion> donaciones) {
        this.donaciones = donaciones;
    }

    // Helper methods
    public boolean tieneLimiteCupos() {
        return cuposMaximos != null && cuposMaximos > 0;
    }

    public boolean tieneCuposDisponibles() {
        if (!tieneLimiteCupos()) {
            return true; // Unlimited capacity
        }
        return cuposDisponibles != null && cuposDisponibles > 0;
    }

    public int getInscritosCount() {
        if (!tieneLimiteCupos()) {
            return (int) inscripciones.stream()
                    .filter(i -> i.getEstado() != com.dwi.ruwana.model.enums.EstadoInscripcion.CANCELADO)
                    .count();
        }
        return cuposMaximos - (cuposDisponibles != null ? cuposDisponibles : cuposMaximos);
    }

    public boolean decrementarCupo() {
        if (!tieneLimiteCupos()) {
            return true; // No limit, always allow
        }
        if (cuposDisponibles != null && cuposDisponibles > 0) {
            cuposDisponibles--;
            return true;
        }
        return false;
    }

    public void incrementarCupo() {
        if (tieneLimiteCupos() && cuposDisponibles != null) {
            cuposDisponibles++;
        }
    }

    public void aprobar(Usuario admin) {
        this.estado = EstadoEvento.APROBADO;
        this.fechaAprobacion = LocalDateTime.now();
        this.adminAprobador = admin;
        this.motivoRechazo = null;
    }

    public void rechazar(Usuario admin, String motivo) {
        this.estado = EstadoEvento.RECHAZADO;
        this.fechaAprobacion = LocalDateTime.now();
        this.adminAprobador = admin;
        this.motivoRechazo = motivo;
    }

    public boolean isAprobado() {
        return estado == EstadoEvento.APROBADO;
    }

    public boolean isPendiente() {
        return estado == EstadoEvento.PENDIENTE_APROBACION;
    }

    public boolean isFinalizado() {
        return estado == EstadoEvento.FINALIZADO;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Evento)) return false;
        Evento evento = (Evento) o;
        return Objects.equals(id, evento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", estado=" + estado +
                '}';
    }
}
