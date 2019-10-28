package py.com.fuentepy.appfinanzasBackend.data.entity;

import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
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

    @NotNull
    @Column(name = "numero_comprobante")
    private String numeroComprobante;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_movimiento")
    private Date fechaMovimiento;

    @Basic(optional = false)
    @NotNull
    @Column(name = "monto")
    private Double monto;

    @Column(name = "numero_cuota")
    private Long numeroCuota;

    @NotNull
    @Column(name = "signo")
    private String signo;

    @NotNull
    @Column(name = "detalle")
    private String detalle;

    @NotNull
    @Column(name = "tabla_id")
    private Long tablaId;

    @NotNull
    @Column(name = "tabla_nombre")
    private String tablaNombre;

    @JoinColumn(name = "moneda_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Moneda monedaId;

    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioId;

}
