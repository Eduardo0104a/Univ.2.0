package com.dwi.ruwana.dto;

import java.time.LocalDate;

/**
 * DTO for volunteer registration
 */
public class RegistroVoluntarioDTO {
    
    // User data
    private String email;
    private String password;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono;
    
    // Volunteer data
    private LocalDate fechaNacimiento;
    private Integer generoId;
    private Integer estadoCivilId;
    private String nacionalidad;
    private Integer tipoDocumentoId;
    private String numeroDocumento;
    
    // Educational data
    private Integer gradoInstruccionId;
    private Integer centroEstudiosId;
    private String nombreCentroEstudios;
    private String correoInstitucional;
    private String carrera;

    // Constructors
    public RegistroVoluntarioDTO() {}

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getGeneroId() {
        return generoId;
    }

    public void setGeneroId(Integer generoId) {
        this.generoId = generoId;
    }

    public Integer getEstadoCivilId() {
        return estadoCivilId;
    }

    public void setEstadoCivilId(Integer estadoCivilId) {
        this.estadoCivilId = estadoCivilId;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Integer getTipoDocumentoId() {
        return tipoDocumentoId;
    }

    public void setTipoDocumentoId(Integer tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Integer getGradoInstruccionId() {
        return gradoInstruccionId;
    }

    public void setGradoInstruccionId(Integer gradoInstruccionId) {
        this.gradoInstruccionId = gradoInstruccionId;
    }

    public Integer getCentroEstudiosId() {
        return centroEstudiosId;
    }

    public void setCentroEstudiosId(Integer centroEstudiosId) {
        this.centroEstudiosId = centroEstudiosId;
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
}
