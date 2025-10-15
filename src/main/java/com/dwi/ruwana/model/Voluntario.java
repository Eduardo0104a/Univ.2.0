package com.dwi.ruwana.model;

import com.dwi.ruwana.model.catalogo.*;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Volunteer entity with specific volunteer data
 */
@Entity
@Table(name = "voluntarios")
public class Voluntario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_civil_id")
    private CatEstadoCivil estadoCivil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genero_id")
    private CatGenero genero;

    @Column(length = 100)
    private String nacionalidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_documento_id")
    private CatTipoDocumento tipoDocumento;

    @Column(name = "numero_documento", length = 50)
    private String numeroDocumento;

    // Educational data
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grado_instruccion_id")
    private CatGradoInstruccion gradoInstruccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "centro_estudios_id")
    private CatCentroEstudios centroEstudios;

    @Column(name = "nombre_centro_estudios", length = 255)
    private String nombreCentroEstudios;

    @Column(name = "correo_institucional", length = 255)
    private String correoInstitucional;

    @Column(length = 255)
    private String carrera;

    // Metadata
    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "ultima_actualizacion")
    private LocalDateTime ultimaActualizacion;

    // Relationships
    @OneToMany(mappedBy = "voluntario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Inscripcion> inscripciones = new HashSet<>();

    @OneToMany(mappedBy = "voluntario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Donacion> donaciones = new HashSet<>();

    // Constructors
    public Voluntario() {
    }

    public Voluntario(Usuario usuario) {
        this.usuario = usuario;
    }

    // Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
        ultimaActualizacion = LocalDateTime.now();
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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public CatEstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(CatEstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public CatGenero getGenero() {
        return genero;
    }

    public void setGenero(CatGenero genero) {
        this.genero = genero;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
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

    public CatGradoInstruccion getGradoInstruccion() {
        return gradoInstruccion;
    }

    public void setGradoInstruccion(CatGradoInstruccion gradoInstruccion) {
        this.gradoInstruccion = gradoInstruccion;
    }

    public CatCentroEstudios getCentroEstudios() {
        return centroEstudios;
    }

    public void setCentroEstudios(CatCentroEstudios centroEstudios) {
        this.centroEstudios = centroEstudios;
    }

    public String getNombreCentroEstudios() {
        return nombreCentroEstudios;
    }

    public void setNombreCentroEstudios(String nombreCentroEstudios) {
        this.nombreCentroEstudios = nombreCentroEstudios;
    }

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
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
    public void addInscripcion(Inscripcion inscripcion) {
        inscripciones.add(inscripcion);
        inscripcion.setVoluntario(this);
    }

    public void removeInscripcion(Inscripcion inscripcion) {
        inscripciones.remove(inscripcion);
        inscripcion.setVoluntario(null);
    }

    public int getEdad() {
        if (fechaNacimiento == null) {
            return 0;
        }
        return LocalDate.now().getYear() - fechaNacimiento.getYear();
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Voluntario)) return false;
        Voluntario that = (Voluntario) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Voluntario{" +
                "id=" + id +
                ", usuario=" + (usuario != null ? usuario.getEmail() : null) +
                ", numeroDocumento='" + numeroDocumento + '\'' +
                '}';
    }
}
