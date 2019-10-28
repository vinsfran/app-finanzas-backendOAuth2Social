package py.com.fuentepy.appfinanzasBackend.resource.archivo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@ApiModel(value = "archivoModel", description = "Ejemplo de para Achivo")
public class ArchivoModel implements Serializable {

    @JsonProperty("nombre")
    @SerializedName("nombre")
    private String nombre;

    @JsonProperty("content_type")
    @SerializedName("content_type")
    private String contentType;

    @JsonProperty("dato")
    @SerializedName("dato")
    private String dato;

}
