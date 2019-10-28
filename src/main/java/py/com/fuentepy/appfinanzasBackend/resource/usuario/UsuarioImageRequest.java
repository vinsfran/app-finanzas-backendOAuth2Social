package py.com.fuentepy.appfinanzasBackend.resource.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@RequiredArgsConstructor
@ApiModel(value = "UsuarioImageRequest", description = "Ejemplo de para Imagen de Usuario")
public class UsuarioImageRequest implements Serializable {

    @JsonProperty("image_profile_name")
    @SerializedName("image_profile_name")
    private String imageProfileName;

    @JsonProperty("content_type")
    @SerializedName("content_type")
    private String contentType;

    @JsonProperty("image_profile_data")
    @SerializedName("image_profile_data")
    private String imageProfileData;

}
