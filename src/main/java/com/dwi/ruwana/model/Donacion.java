package com.dwi.ruwana.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Donation entity
 */
@Entity
@Table(name = "donaciones")
public class Donacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voluntario_id", nullable = false)
    private Voluntario voluntario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id")
    private Evento evento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacion_id")
    private Organizacion organizacion;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(length = 3)
    private String moneda = "PEN";

    @Column(name = "tipo_donacion", length = 20)
    private String tipoDonacion = "MONETARIA";

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "metodo_pago", length = 20)
    private String metodoPago;

    @Column(name = "numero_transaccion", length = 100)
    private String numeroTransaccion;

    @Column(length = 20)
    private String estado = "PENDIENTE";

    @Column(name = "comprobante_url", length = 500)
    private String comprobanteUrl;

    @Column(name = "fecha_donacion", nullable = false, updatable = false)
    private LocalDateTime fechaDonacion;

    @Column(name = "fecha_confirmacion")
    private LocalDateTime fechaConfirmacion;

    // Constructors
    public Donacion() {
    }

    public Donacion(Voluntario voluntario, BigDecimal monto) {
        this.voluntario = voluntario;
        this.monto = monto;
    }

    // Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        fechaDonacion = LocalDateTime.now();
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

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getTipoDonacion() {
        return tipoDonacion;
    }

    public void setTipoDonacion(String tipoDonacion) {
        this.tipoDonacion = tipoDonacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getNumeroTransaccion() {
        return numeroTransaccion;
    }

    public void setNumeroTransaccion(String numeroTransaccion) {
        this.numeroTransaccion = numeroTransaccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getComprobanteUrl() {
        return comprobanteUrl;
    }

    public void setComprobanteUrl(String comprobanteUrl) {
        this.comprobanteUrl = comprobanteUrl;
    }

    public LocalDateTime getFechaDonacion() {
        return fechaDonacion;
    }

    public void setFechaDonacion(LocalDateTime fechaDonacion) {
        this.fechaDonacion = fechaDonacion;
    }

    public LocalDateTime getFechaConfirmacion() {
        return fechaConfirmacion;
    }

    public void setFechaConfirmacion(LocalDateTime fechaConfirmacion) {
        this.fechaConfirmacion = fechaConfirmacion;
    }

    // Helper methods
    public void confirmar() {
        this.estado = "CONFIRMADA";
        this.fechaConfirmacion = LocalDateTime.now();
    }

    public void rechazar() {
        this.estado = "RECHAZADA";
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Donacion)) return false;
        Donacion donacion = (Donacion) o;
        return Objects.equals(id, donacion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Donacion{" +
                "id=" + id +
                ", monto=" + monto +
                ", moneda='" + moneda + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
