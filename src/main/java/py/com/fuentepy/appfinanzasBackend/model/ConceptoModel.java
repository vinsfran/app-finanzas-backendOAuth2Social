package py.com.fuentepy.appfinanzasBackend.model;

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
    private Integer id;

    @JsonProperty("nombre")
    @SerializedName("nombre")
    private String nombre;

    @JsonProperty("tipoConcepto")
    @SerializedName("tipoConcepto")
    private String tipoConcepto;

    @JsonProperty("codigoConcepto")
    @SerializedName("codigoConcepto")
    private String codigoConcepto;

    @JsonProperty("usuarioId")
    @SerializedName("usuarioId")
    private Long usuarioId;

}
