package py.com.fuentepy.appfinanzasBackend.entity;

import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author vinsfran
 */
@Data
@CommonsLog
@Entity
@Table(name = "movimientos")
public class Movimiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_comprobante")
    private String numeroComprobante;

    @Column(name = "fecha_movimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaMovimiento;

    @Basic(optional = false)
    @NotNull
    @Column(name = "monto_pagado")
    private Long montoPagado;

    @Column(name = "nombre_entidad")
    private String nombreEntidad;

    @Column(name = "numero_cuota")
    private Integer numeroCuota;

    @JoinColumn(name = "prestamo_id", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Prestamo prestamoId;

    @JoinColumn(name = "ahorro_id", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Ahorro ahorroId;

    @JoinColumn(name = "tarjeta_id", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Tarjeta tarjetaId;

    @JoinColumn(name = "concepto_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Concepto conceptoId;

    @JoinColumn(name = "moneda_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Moneda monedaId;

    @JoinColumn(name = "tipo_pago_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoPago tipoPagoId;

    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioId;
    
}
