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

    @JsonProperty("tipo_concepto_id")
    @SerializedName("tipo_concepto_id")
    private Integer tipoConceptoId;

}
