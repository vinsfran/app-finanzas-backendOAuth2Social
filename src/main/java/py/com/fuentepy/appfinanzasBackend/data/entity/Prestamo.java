package py.com.fuentepy.appfinanzasBackend.data.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author vinsfran
 */
@Data
@Entity
@Table(name = "prestamos")
public class Prestamo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "monto_prestamo")
    private Long montoPrestamo;

    @Column(name = "fecha_desembolso")
    @Temporal(TemporalType.DATE)
    private Date fechaDesembolso;

    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;

    @Column(name = "interes")
    private Long interes;

    @Column(name = "tasa")
    private Long tasa;

    @Column(name = "cantidad_cuotas")
    private Integer cantidadCuotas;

    @Column(name = "cantidad_cuotas_pagadas")
    private Long cantidadCuotasPagadas;
    
    @Column(name = "monto_cuota")
    private Long montoCuota;

    @Column(name = "monto_pagado")
    private Long montoPagado;

    @Column(name = "monto_ultimo_pago")
    private Long montoUltimoPago;

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
