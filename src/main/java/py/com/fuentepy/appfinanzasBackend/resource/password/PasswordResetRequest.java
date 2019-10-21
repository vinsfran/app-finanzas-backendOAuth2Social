package py.com.fuentepy.appfinanzasBackend.resource.password;

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
@ApiModel(value = "passwordResetRequest", description = "Ejemplo de para un modelo para reset de Password")
public class PasswordResetRequest implements Serializable {

    @JsonProperty("email")
    @SerializedName("email")
    private String email;

}
