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
@ApiModel(value = "mesRequestUpdate", description = "Ejemplo de para un modelo editar Mes")
public class MesRequestUpdate implements Serializable {

    @JsonProperty("id")
    @SerializedName("id")
    private Integer id;

    @JsonProperty("nombre")
    @SerializedName("nombre")
    private String nombre;

    @JsonProperty("numero")
    @SerializedName("numero")
    private Integer numero;

}
