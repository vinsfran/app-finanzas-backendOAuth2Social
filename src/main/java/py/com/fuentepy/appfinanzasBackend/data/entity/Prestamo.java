package py.com.fuentepy.appfinanzasBackend.data.entity;

import lombok.Data;
import py.com.fuentepy.appfinanzasBackend.util.ConstantUtil;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author vinsfran
 */
@Data
@Entity
@Table(name = ConstantUtil.PRESTAMOS)
public class Prestamo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_comprobante")
    private String numeroComprobante;

    @Column(name = "monto_prestamo")
    private Double montoPrestamo;

    @Column(name = "fecha_desembolso")
    @Temporal(TemporalType.DATE)
    private Date fechaDesembolso;

    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;

    @Column(name = "fecha_prox_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaProxVencimiento;

    @Column(name = "interes")
    private Double interes;

    @Column(name = "tasa")
    private Double tasa;

    @Column(name = "cantidad_cuotas")
    private Integer cantidadCuotas;

    @Column(name = "cantidad_cuotas_pagadas")
    private Integer cantidadCuotasPagadas;

    @Column(name = "siguiente_cuota")
    private Integer siguienteCuota;

    @Column(name = "monto_cuota")
    private Double montoCuota;

    @Column(name = "monto_pagado")
    private Double montoPagado;

    @Column(name = "monto_mora_total")
    private Double montoMoraTotal;

    @Column(name = "saldo_cuota")
    private Double saldoCuota;

    @Column(name = "monto_ultimo_pago")
    private Double montoUltimoPago;

    @Size(max = 255)
    @Column(name = "destino_prestamo")
    private String destinoPrestamo;

    @Column(name = "estado")
    private Boolean estado;

    @JoinColumn(name = "moneda_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Moneda monedaId;

    @JoinColumn(name = "entidad_financiera_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private EntidadFinanciera entidadFinancieraId;

    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioId;

}
