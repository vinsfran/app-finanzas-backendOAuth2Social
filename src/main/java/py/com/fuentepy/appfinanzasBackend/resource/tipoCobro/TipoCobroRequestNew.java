package py.com.fuentepy.appfinanzasBackend.resource.tipoCobro;

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
@ApiModel(value = "tipoCobroRequestNew", description = "Ejemplo de para un modelo nuevo de Tipo Cobro")
public class TipoCobroRequestNew implements Serializable {

    @JsonProperty("nombre")
    @SerializedName("nombre")
    private String nombre;

}
