package py.com.fuentepy.appfinanzasBackend.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author vinsfran
 */
@Data
@Entity
@Table(name = "prestamos_pagos")
public class PrestamoPago implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    //    Ya esta en movimientos
    @Column(name = "numero_cuota")
    private Integer numeroCuota;

    @Column(name = "fecha_pago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;

//    Ya esta en movimientos
    @Column(name = "monto_pagado")
    private Long montoPagado;

    //    Ya esta en movimientos
    @JoinColumn(name = "prestamo_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Prestamo prestamoId;

    //    Ya esta en movimientos
    @JoinColumn(name = "tipo_pago_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoPago tipoPagoId;

    //    Ya esta en movimientos
    @JoinColumn(name = "usuarios_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioId;

}
