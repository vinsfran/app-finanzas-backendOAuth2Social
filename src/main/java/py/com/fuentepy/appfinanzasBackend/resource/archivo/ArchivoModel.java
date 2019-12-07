package py.com.fuentepy.appfinanzasBackend.resource.archivo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "archivoModel", description = "Ejemplo de para Achivo")
public class ArchivoModel implements Serializable {

    @JsonProperty("id")
    @SerializedName("id")
    private Long id;

    @JsonProperty("nombre")
    @SerializedName("nombre")
    private String nombre;

    @JsonProperty("content_type")
    @SerializedName("content_type")
    private String contentType;

}
