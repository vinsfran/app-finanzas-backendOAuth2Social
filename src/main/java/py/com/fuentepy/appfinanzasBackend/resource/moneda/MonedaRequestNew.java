package py.com.fuentepy.appfinanzasBackend.resource.moneda;

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
@ApiModel(value = "monedaRequestNew", description = "Ejemplo de para un modelo nueva de Moneda")
public class MonedaRequestNew implements Serializable {

    @JsonProperty("nombre")
    @SerializedName("nombre")
    private String nombre;

    @JsonProperty("codigo")
    @SerializedName("codigo")
    private String codigo;

}
