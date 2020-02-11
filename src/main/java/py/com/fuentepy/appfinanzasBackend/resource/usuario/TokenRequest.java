package py.com.fuentepy.appfinanzasBackend.resource.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author vinsfran
 */
@Data
@ApiModel(value = "tokenRequest", description = "Ejemplo de para un modelo para Registro de token")
public class TokenRequest implements Serializable {

    @JsonProperty("token")
    @SerializedName("token")
    private String token;

}
