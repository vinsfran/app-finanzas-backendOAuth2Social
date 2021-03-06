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
@ApiModel(value = "conceptoRequestUpdate", description = "Ejemplo para un modelo a editar de Concepto")
public class ConceptoRequestUpdate implements Serializable {

    @JsonProperty("id")
    @SerializedName("id")
    private Long id;

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
