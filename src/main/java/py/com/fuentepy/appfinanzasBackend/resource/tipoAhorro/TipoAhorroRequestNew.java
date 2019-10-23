package py.com.fuentepy.appfinanzasBackend.resource.tipoAhorro;

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
@ApiModel(value = "tipoAhorroRequestNew", description = "Ejemplo de para un modelo nuevo de Tipo Ahorro")
public class TipoAhorroRequestNew implements Serializable {

    @JsonProperty("nombre")
    @SerializedName("nombre")
    private String nombre;

}
