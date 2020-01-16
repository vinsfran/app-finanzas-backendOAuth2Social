package py.com.fuentepy.appfinanzasBackend.data.entity;

import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;
import py.com.fuentepy.appfinanzasBackend.util.ConstantUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@CommonsLog
@Entity
@Table(name = ConstantUtil.PRESTAMOS_CUOTERAS)
public class PrestamoCuotera implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_cuota")
    private Integer numeroCuota;

    @Column(name = "monto_cuota")
    private Double montoCuota;

    @Column(name = "saldo_cuota")
    private Double saldoCuota;

    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;

    @Column(name = "estado_cuota")
    private String estadoCuota;

    @JoinColumn(name = "prestamo_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Prestamo prestamoId;

    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioId;
}
