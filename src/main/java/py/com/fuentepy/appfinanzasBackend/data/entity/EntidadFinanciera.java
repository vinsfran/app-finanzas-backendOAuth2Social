package py.com.fuentepy.appfinanzasBackend.data.entity;

import lombok.Data;
import py.com.fuentepy.appfinanzasBackend.util.ConstantUtil;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author vinsfran
 */
@Data
@Entity
@Table(name = ConstantUtil.ENTIDADES_FINANCIERAS)
public class EntidadFinanciera implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioId;

}
