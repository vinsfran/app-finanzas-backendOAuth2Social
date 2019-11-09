package py.com.fuentepy.appfinanzasBackend.resource.concepto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author vinsfran
 */
@Data
@ApiModel(value = "conceptoRequestNew", description = "Ejemplo de para un modelo nuevo de Concepto")
public class ConceptoRequestNew implements Serializable {

    @JsonProperty("nombre")
    @SerializedName("nombre")
    private String nombre;

    @JsonProperty("tipo_concepto")
    @SerializedName("tipo_concepto")
    private String tipoConcepto;

    @JsonProperty("moneda_id")
    @SerializedName("moneda_id")
    private Integer monedaId;

}
