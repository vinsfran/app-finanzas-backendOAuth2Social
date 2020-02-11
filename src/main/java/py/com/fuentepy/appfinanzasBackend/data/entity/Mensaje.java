package py.com.fuentepy.appfinanzasBackend.data.entity;

import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;
import py.com.fuentepy.appfinanzasBackend.util.ConstantUtil;

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
@Table(name = ConstantUtil.MENSAJES)
public class Mensaje implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @Temporal(TemporalType.TIMESTAMP)
    private Date id;

    @Column(name = "fecha_envio")
    private Date fechaEnvio;

    @Basic(optional = false)
    @NotNull
    @Column(name = "titulo")
    private String titulo;

    @Basic(optional = false)
    @NotNull
    @Column(name = "body")
    private String body;

    @Basic(optional = false)
    @NotNull
    @Column(name = "data_json")
    private String dataJson;

    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private boolean status;

    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioId;

}
