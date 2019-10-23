package py.com.fuentepy.appfinanzasBackend.data.entity;

import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author vinsfran
 */
@Data
@CommonsLog
@Entity
@Table(name = "tipos_conceptos")
public class TipoConcepto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "signo")
    private String signo;
    
}
