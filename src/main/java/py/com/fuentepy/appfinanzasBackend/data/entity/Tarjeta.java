package py.com.fuentepy.appfinanzasBackend.data.entity;

import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;
import py.com.fuentepy.appfinanzasBackend.util.ConstantUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author vinsfran
 */
@Data
@CommonsLog
@Entity
@Table(name = ConstantUtil.TARJETAS)
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
    private Double lineaCredito;

    @Column(name = "monto_disponible")
    private Double montoDisponible;

    @Column(name = "monto_ultimo_pago")
    private Double montoUltimoPago;

    @Column(name = "deuda_total")
    private Double deudaTotal;

    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;

    @Column(name = "estado")
    private Boolean estado;

    @JoinColumn(name = "monedas_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Moneda monedaId;

    @JoinColumn(name = "entidades_financieras_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private EntidadFinanciera entidadFinancieraId;

    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioId;

}
