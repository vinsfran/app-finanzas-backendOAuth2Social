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
@ApiModel(value = "forgotRequest", description = "Ejemplo de para un modelo para Forgot Password")
public class ForgotRequest implements Serializable {

    @JsonProperty("email")
    @SerializedName("email")
    private String email;

}
