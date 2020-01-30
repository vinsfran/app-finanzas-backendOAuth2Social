package py.com.fuentepy.appfinanzasBackend.resource.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@RequiredArgsConstructor
@ApiModel(value = "SignUpRequest", description = "Ejemplo de para un Login")
public class SignUpRequest {

    @NotBlank
    @JsonProperty("first_name")
    @SerializedName("first_name")
    private String firstName;

    @NotBlank
    @JsonProperty("last_name")
    @SerializedName("last_name")
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

}
