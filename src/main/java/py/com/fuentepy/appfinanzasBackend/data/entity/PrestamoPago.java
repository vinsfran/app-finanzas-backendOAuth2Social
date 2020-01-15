package py.com.fuentepy.appfinanzasBackend.data.entity;

import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;
import py.com.fuentepy.appfinanzasBackend.util.ConstantUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
@CommonsLog
@Entity
@Table(name = ConstantUtil.PRESTAMOS_PAGOS)
public class PrestamoPago implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_cuota")
    private Integer numeroCuota;

    @Column(name = "monto_pago")
    private Double montoPago;

    @Column(name = "fecha_pago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;

    @JoinColumn(name = "prestamo_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Prestamo prestamoId;

    @JoinColumn(name = "movimiento_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Movimiento movimientoId;

    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioId;

}
