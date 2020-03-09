package py.com.fuentepy.appfinanzasBackend.resource.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import py.com.fuentepy.appfinanzasBackend.json.JsonDateSimpleDeserializer;
import py.com.fuentepy.appfinanzasBackend.json.JsonDateSimpleSerializer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

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

    @ApiModelProperty(value = "Fecha de Nacimiento", required = false, example = "AAAA-MM-DD")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("fecha_nacimiento")
    @SerializedName("fecha_nacimiento")
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    private Date fechaNacimiento;

    @JsonProperty("sexo")
    @SerializedName("sexo")
    private String sexo;

}
