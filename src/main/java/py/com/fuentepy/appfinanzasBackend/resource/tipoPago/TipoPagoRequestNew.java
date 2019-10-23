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
@ApiModel(value = "tipoPagoRequestNew", description = "Ejemplo de para un modelo nuevo de Tipo Pago")
public class TipoPagoRequestNew implements Serializable {

    @JsonProperty("nombre")
    @SerializedName("nombre")
    private String nombre;

}
