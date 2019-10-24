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
@ApiModel(value = "monedaRequestUpdate", description = "Ejemplo de para un modelo editar Moneda")
public class MonedaRequestUpdate implements Serializable {

    @JsonProperty("id")
    @SerializedName("id")
    private Integer id;

    @JsonProperty("nombre")
    @SerializedName("nombre")
    private String nombre;

    @JsonProperty("codigo")
    @SerializedName("codigo")
    private String codigo;

}
