package py.com.fuentepy.appfinanzasBackend.resource.ahorroTipo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author vinsfran
 */
@Data
@ApiModel(value = "ahorroTipoRequestNew", description = "Ejemplo de para un modelo nuevo de Ahorro Tipo")
public class AhorroTipoRequestNew implements Serializable {

    @JsonProperty("nombre")
    @SerializedName("nombre")
    private String nombre;

}
