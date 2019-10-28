package py.com.fuentepy.appfinanzasBackend.data.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author vinsfran
 */
@Data
@Entity
@Table(name = "archivos")
public class Archivo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull
    @Column(name = "tabla_id")
    private Long tablaId;

    @NotNull
    @Column(name = "tabla_nombre")
    private String tablaNombre;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "nombre")
    private String nombre;

    //    @Lob
    @Column(name = "dato", nullable = true)
    @Basic(optional = true, fetch = FetchType.EAGER)
    private byte[] dato;

    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioId;

}
