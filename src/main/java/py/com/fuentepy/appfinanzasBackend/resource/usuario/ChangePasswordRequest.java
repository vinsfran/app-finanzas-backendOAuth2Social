package py.com.fuentepy.appfinanzasBackend.resource.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author vinsfran
 */
@Data
@ApiModel(value = "changePasswordRequest", description = "Ejemplo de para un modelo para Change Password")
public class ChangePasswordRequest implements Serializable {

    @JsonProperty("password_old")
    @SerializedName("password_old")
    private String passwordOld;

    @JsonProperty("password_new")
    @SerializedName("password_new")
    private String passwordNew;

}
