package py.com.fuentepy.appfinanzasBackend.resource.tipoConcepto;

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
@ApiModel(value = "tipoConceptoRequestNew", description = "Ejemplo de para un modelo nuevc Tipo Concepto")
public class TipoConceptoRequestNew implements Serializable {

    @JsonProperty("nombre")
    @SerializedName("nombre")
    private String nombre;

    @JsonProperty("signo")
    @SerializedName("signo")
    private String signo;

}
