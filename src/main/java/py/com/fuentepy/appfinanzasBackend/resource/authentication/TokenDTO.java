package py.com.fuentepy.appfinanzasBackend.resource.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@ApiModel(value = "LoginRequest", description = "Ejemplo de para un Login")
public class TokenDTO {

    @NonNull
    @JsonProperty("value")
    @SerializedName("value")
    private String value;

    @NonNull
    @JsonProperty("type")
    @SerializedName("type")
    private String type = "Bearer";

}
