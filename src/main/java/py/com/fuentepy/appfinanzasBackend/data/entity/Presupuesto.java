package py.com.fuentepy.appfinanzasBackend.data.entity;

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
@Table(name = "presupuestos")
public class Presupuesto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha_alta")
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;

    @Basic(optional = false)
    @NotNull
    @Column(name = "monto")
    private Long monto;

    @Basic(optional = false)
    @NotNull
    @Column(name = "anio")
    private Integer anio;

    @Basic(optional = false)
    @NotNull
    @Column(name = "porcentaje_alerta")
    private Integer porcentajeAlerta;

    @JoinColumn(name = "mes_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Mes mesId;

    @JoinColumn(name = "moneda_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Moneda monedaId;

    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioId;
    
}
