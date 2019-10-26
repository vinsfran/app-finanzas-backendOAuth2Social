package py.com.fuentepy.appfinanzasBackend.resource.concepto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author vinsfran
 */
@Data
public class ConceptoModel implements Serializable {

    @JsonProperty("id")
    @SerializedName("id")
    private Long id;

    @JsonProperty("nombre")
    @SerializedName("nombre")
    private String nombre;

    @JsonProperty("tipo_concepto_id")
    @SerializedName("tipo_concepto_id")
    private Integer tipoConceptoId;

    @JsonProperty("tipo_concepto_nombre")
    @SerializedName("tipo_concepto_nombre")
    private String tipoConceptoNombre;

    @JsonProperty("tipo_concepto_signo")
    @SerializedName("tipo_concepto_signo")
    private String tipoConceptoSigno;

    @JsonProperty("usuario_id")
    @SerializedName("usuario_id")
    private Long usuarioId;

}
