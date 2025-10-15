package com.dwi.ruwana.model;

import com.dwi.ruwana.model.catalogo.CatTipoDocumento;
import com.dwi.ruwana.model.enums.EstadoAprobacion;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Organization entity
 */
@Entity
@Table(name = "organizaciones")
public class Organizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "nombre_organizacion", nullable = false, length = 255)
    private String nombreOrganizacion;

    @Column(length = 100)
    private String pais = "Perú";

    @Column(columnDefinition = "TEXT")
    private String direccion;

    @Column(name = "contacto_principal", length = 255)
    private String contactoPrincipal;

    @Column(length = 20)
    private String telefono;

    @Column(length = 20)
    private String celular;

    // Legal data
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_documento_id")
    private CatTipoDocumento tipoDocumento;

    @Column(name = "numero_documento", unique = true, length = 50)
    private String numeroDocumento;

    @Column(name = "constituida_legalmente")
    private Boolean constituidaLegalmente = false;

    @Column(name = "razon_social", length = 255)
    private String razonSocial;

    // Additional information
    @Column(name = "motivo_registro", columnDefinition = "TEXT")
    private String motivoRegistro;

    @Column(name = "descripcion_beneficiarios", columnDefinition = "TEXT")
    private String descripcionBeneficiarios;

    // Approval status
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_aprobacion", nullable = false, length = 20)
    private EstadoAprobacion estadoAprobacion = EstadoAprobacion.PENDIENTE;

    @Column(name = "fecha_aprobacion")
    private LocalDateTime fechaAprobacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_aprobador_id")
    private Usuario adminAprobador;

    @Column(name = "motivo_rechazo", columnDefinition = "TEXT")
    private String motivoRechazo;

    // Metadata
    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "ultima_actualizacion")
    private LocalDateTime ultimaActualizacion;

    // Relationships
    @OneToMany(mappedBy = "organizacion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Evento> eventos = new HashSet<>();

    @OneToMany(mappedBy = "organizacion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Donacion> donaciones = new HashSet<>();

    // Constructors
    public Organizacion() {
    }

    public Organizacion(Usuario usuario, String nombreOrganizacion) {
        this.usuario = usuario;
        this.nombreOrganizacion = nombreOrganizacion;
    }

    // Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
        ultimaActualizacion = LocalDateTime.now();
        if (estadoAprobacion == null) {
            estadoAprobacion = EstadoAprobacion.PENDIENTE;
        }
        if (constituidaLegalmente == null) {
            constituidaLegalmente = false;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombreOrganizacion() {
        return nombreOrganizacion;
    }

    public void setNombreOrganizacion(String nombreOrganizacion) {
        this.nombreOrganizacion = nombreOrganizacion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getContactoPrincipal() {
        return contactoPrincipal;
    }

    public void setContactoPrincipal(String contactoPrincipal) {
        this.contactoPrincipal = contactoPrincipal;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public CatTipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(CatTipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Boolean getConstituidaLegalmente() {
        return constituidaLegalmente;
    }

    public void setConstituidaLegalmente(Boolean constituidaLegalmente) {
        this.constituidaLegalmente = constituidaLegalmente;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getMotivoRegistro() {
        return motivoRegistro;
    }

    public void setMotivoRegistro(String motivoRegistro) {
        this.motivoRegistro = motivoRegistro;
    }

    public String getDescripcionBeneficiarios() {
        return descripcionBeneficiarios;
    }

    public void setDescripcionBeneficiarios(String descripcionBeneficiarios) {
        this.descripcionBeneficiarios = descripcionBeneficiarios;
    }

    public EstadoAprobacion getEstadoAprobacion() {
        return estadoAprobacion;
    }

    public void setEstadoAprobacion(EstadoAprobacion estadoAprobacion) {
        this.estadoAprobacion = estadoAprobacion;
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

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public LocalDateTime getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    public void setUltimaActualizacion(LocalDateTime ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }

    public Set<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(Set<Evento> eventos) {
        this.eventos = eventos;
    }

    public Set<Donacion> getDonaciones() {
        return donaciones;
    }

    public void setDonaciones(Set<Donacion> donaciones) {
        this.donaciones = donaciones;
    }

    // Helper methods
    public void addEvento(Evento evento) {
        eventos.add(evento);
        evento.setOrganizacion(this);
    }

    public void removeEvento(Evento evento) {
        eventos.remove(evento);
        evento.setOrganizacion(null);
    }

    public boolean isAprobada() {
        return estadoAprobacion == EstadoAprobacion.APROBADO;
    }

    public boolean isPendiente() {
        return estadoAprobacion == EstadoAprobacion.PENDIENTE;
    }

    public boolean isRechazada() {
        return estadoAprobacion == EstadoAprobacion.RECHAZADO;
    }

    public void aprobar(Usuario admin) {
        this.estadoAprobacion = EstadoAprobacion.APROBADO;
        this.fechaAprobacion = LocalDateTime.now();
        this.adminAprobador = admin;
        this.motivoRechazo = null;
    }

    public void rechazar(Usuario admin, String motivo) {
        this.estadoAprobacion = EstadoAprobacion.RECHAZADO;
        this.fechaAprobacion = LocalDateTime.now();
        this.adminAprobador = admin;
        this.motivoRechazo = motivo;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organizacion)) return false;
        Organizacion that = (Organizacion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Organizacion{" +
                "id=" + id +
                ", nombreOrganizacion='" + nombreOrganizacion + '\'' +
                ", estadoAprobacion=" + estadoAprobacion +
                '}';
    }
}
