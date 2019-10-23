package py.com.fuentepy.appfinanzasBackend.resource.tipoConcepto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author vinsfran
 */
@Data
public class TipoConceptoModel implements Serializable {

    @JsonProperty("id")
    @SerializedName("id")
    private Integer id;

    @JsonProperty("nombre")
    @SerializedName("nombre")
    private String nombre;

    @JsonProperty("signo")
    @SerializedName("signo")
    private String signo;

}
