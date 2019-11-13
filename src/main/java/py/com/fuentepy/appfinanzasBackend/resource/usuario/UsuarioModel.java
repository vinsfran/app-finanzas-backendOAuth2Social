package py.com.fuentepy.appfinanzasBackend.resource.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author vinsfran
 */
@Data
@ApiModel(value = "usuarioModel", description = "Ejemplo de para datos del Usuario")
public class UsuarioModel implements Serializable {

    @ApiModelProperty(value = "Identificador unico del Usuario", required = true)
    @JsonProperty("id")
    @SerializedName("id")
    private Long id;

    @JsonProperty("last_name")
    @SerializedName("last_name")
    private String lastName;

    @JsonProperty("first_name")
    @SerializedName("first_name")
    private String firstName;

    @JsonProperty("email")
    @SerializedName("email")
    private String email;

    @JsonProperty("image_profile_name")
    @SerializedName("image_profile_name")
    private String imageProfileName;

    @JsonProperty("image_profile_base64")
    @SerializedName("image_profile_base64")
    private String imageProfileBase64;

}
