package py.com.fuentepy.appfinanzasBackend.data.entity;

import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;
import py.com.fuentepy.appfinanzasBackend.util.ConstantUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author vinsfran
 */
@Data
@CommonsLog
@Entity
@Table(name = ConstantUtil.TIPOS_PAGOS)
public class TipoPago implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nombre")
    private String nombre;

    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioId;

}
