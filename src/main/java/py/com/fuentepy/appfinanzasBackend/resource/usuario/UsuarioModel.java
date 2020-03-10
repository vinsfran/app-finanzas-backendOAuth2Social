package py.com.fuentepy.appfinanzasBackend.resource.usuario;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import py.com.fuentepy.appfinanzasBackend.json.JsonDateSimpleDeserializer;
import py.com.fuentepy.appfinanzasBackend.json.JsonDateSimpleSerializer;

import java.io.Serializable;
import java.util.Date;

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
