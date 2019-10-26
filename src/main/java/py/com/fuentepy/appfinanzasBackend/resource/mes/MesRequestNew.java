package py.com.fuentepy.appfinanzasBackend.resource.mes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author vinsfran
 */
@Data
@ApiModel(value = "mesRequestNew", description = "Ejemplo para un modelo nuevo de Mes")
public class MesRequestNew implements Serializable {

    @JsonProperty("nombre")
    @SerializedName("nombre")
    private String nombre;

    @JsonProperty("numero")
    @SerializedName("numero")
    private Integer numero;

}
