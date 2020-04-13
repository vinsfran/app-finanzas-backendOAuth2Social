package py.com.fuentepy.appfinanzasBackend.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import py.com.fuentepy.appfinanzasBackend.util.ConstantUtil;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author vinsfran
 */
@Data
@Entity
@Table(name = ConstantUtil.USUARIOS)
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Email(message = "Please provide a valid e-mail")
    @NotEmpty(message = "Please provide an e-mail")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name")
    @NotEmpty(message = "Please provide your first name")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "Please provide your last name")
    private String lastName;

    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "created_on")
    private Date createdOn;

    @Column(name = "last_login")
    private Date lastLogin;

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "email_verified", nullable = false)
    private Boolean emailVerified = false;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "monedas_id")
    private Integer monedaId;

}
