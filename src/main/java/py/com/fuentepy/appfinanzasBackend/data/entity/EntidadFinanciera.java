package py.com.fuentepy.appfinanzasBackend.data.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author vinsfran
 */
@Data
@Entity
@Table(name = "entidades_financieras")
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
