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
@Table(name = ConstantUtil.DISPOSITIVOS)
public class Dispositivo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "token")
    private String token;

    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioId;

}
