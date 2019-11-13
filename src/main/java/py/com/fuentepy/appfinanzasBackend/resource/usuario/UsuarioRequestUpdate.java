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
@ApiModel(value = "usuarioRequestUpdate", description = "Ejemplo para modificar un Usuario")
public class UsuarioRequestUpdate implements Serializable {

    @JsonProperty("last_name")
    @SerializedName("last_name")
    private String lastName;

    @JsonProperty("first_name")
    @SerializedName("first_name")
    private String firstName;

}
