package py.com.fuentepy.appfinanzasBackend.data.entity;

import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;
import py.com.fuentepy.appfinanzasBackend.util.ConstantUtil;

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
@Table(name = ConstantUtil.PARAMETROS)
public class Parametro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "codigo")
    private String codigo;

    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private String valor;

    @Basic(optional = false)
    @NotNull
    @Column(name = "grupo")
    private String grupo;

    @Basic(optional = false)
    @NotNull
    @Column(name = "descripcion")
    private String descripcion;

}
