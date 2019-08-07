package py.com.fuentepy.appfinanzasBackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author vinsfran
 */
@Data
public class PresupuestoModel implements Serializable {

    @JsonProperty("id")
    @SerializedName("id")
    private Long id;

    @JsonProperty("fechaAlta")
    @SerializedName("fechaAlta")
    private Date fechaAlta;

    @JsonProperty("monto")
    @SerializedName("monto")
    private Long monto;

    @JsonProperty("anio")
    @SerializedName("anio")
    private Integer anio;

    @JsonProperty("porcentajeAlerta")
    @SerializedName("porcentajeAlerta")
    private Integer porcentajeAlerta;;

    @JsonProperty("mesId")
    @SerializedName("mesId")
    private Integer mesId;

    @JsonProperty("mesNombre")
    @SerializedName("mesNombre")
    private String mesNombre;

    @JsonProperty("mesNumero")
    @SerializedName("mesNumero")
    private Integer mesNumero;

    @JsonProperty("monedaId")
    @SerializedName("monedaId")
    private Integer monedaId;

    @JsonProperty("monedaNombre")
    @SerializedName("monedaNombre")
    private String monedaNombre;

    @JsonProperty("monedaCodigo")
    @SerializedName("monedaCodigo")
    private String monedaCodigo;

    @JsonProperty("usuarioId")
    @SerializedName("usuarioId")
    private Long usuarioId;

}
