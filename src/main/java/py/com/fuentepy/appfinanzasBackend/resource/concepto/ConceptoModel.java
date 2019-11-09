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

    @JsonProperty("tipo_concepto")
    @SerializedName("tipo_concepto")
    private String tipoConcepto;

    @JsonProperty("usuario_id")
    @SerializedName("usuario_id")
    private Long usuarioId;

}
