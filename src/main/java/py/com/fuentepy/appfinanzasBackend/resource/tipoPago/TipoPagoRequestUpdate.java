package py.com.fuentepy.appfinanzasBackend.resource.tipoPago;

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
@ApiModel(value = "tipoPagoRequestUpdate", description = "Ejemplo de para un modelo a editar de Tipo Pago")
public class TipoPagoRequestUpdate implements Serializable {

    @JsonProperty("id")
    @SerializedName("id")
    private Long id;

    @JsonProperty("nombre")
    @SerializedName("nombre")
    private String nombre;

}
