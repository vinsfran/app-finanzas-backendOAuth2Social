package py.com.fuentepy.appfinanzasBackend.resource.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@RequiredArgsConstructor
@ApiModel(value = "LoginRequest", description = "Ejemplo de para un Login")
public class LoginRequest implements Serializable {

    @Email
    @NotBlank
    @NonNull
    @JsonProperty("email")
    @SerializedName("email")
    private String email;

    @NotBlank
    @NonNull
    @JsonProperty("password")
    @SerializedName("password")
    private String password;

}
