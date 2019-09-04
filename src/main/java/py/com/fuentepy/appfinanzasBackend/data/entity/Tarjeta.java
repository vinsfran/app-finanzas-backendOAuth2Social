package py.com.fuentepy.appfinanzasBackend.data.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author vinsfran
 */
@Data
@Entity
@Table(name = "tarjetas")
public class Tarjeta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_tarjeta")
    private String numeroTarjeta;

    @Column(name = "marca")
    private String marca;

    @Column(name = "linea_credito")
    private Long lineaCredito;

    @Column(name = "monto_pagado")
    private Long montoPagado;

    @Column(name = "monto_ultimo_pago")
    private Long montoUltimoPago;

    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;

    @Column(name = "estado")
    private Boolean estado;

    @JoinColumn(name = "entidades_financieras_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private EntidadFinanciera entidadFinancieraId;

    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioId;

}
