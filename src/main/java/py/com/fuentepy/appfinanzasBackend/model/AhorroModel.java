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
public class AhorroModel implements Serializable {

    @JsonProperty("id")
    @SerializedName("id")
    private Long id;

    @JsonProperty("montoCapital")
    @SerializedName("montoCapital")
    private Long montoCapital;

    @JsonProperty("fechaInicio")
    @SerializedName("fechaInicio")
    private Date fechaInicio;

    @JsonProperty("fechaVencimiento")
    @SerializedName("fechaVencimiento")
    private Date fechaVencimiento;

    @JsonProperty("plazoTotal")
    @SerializedName("plazoTotal")
    private Integer plazoTotal;

    @JsonProperty("interes")
    @SerializedName("interes")
    private Long interes;

    @JsonProperty("tasa")
    @SerializedName("tasa")
    private Long tasa;

    @JsonProperty("cantidadCuotas")
    @SerializedName("cantidadCuotas")
    private Integer cantidadCuotas;

    @JsonProperty("cantidadCuotasPagadas")
    @SerializedName("cantidadCuotasPagadas")
    private Integer cantidadCuotasPagadas;

    @JsonProperty("montoCuota")
    @SerializedName("montoCuota")
    private Long montoCuota;

    @JsonProperty("montoInteresCuota")
    @SerializedName("montoInteresCuota")
    private Long montoInteresCuota;

    @JsonProperty("cantidadCobro")
    @SerializedName("cantidadCobro")
    private Long cantidadCobro;

    @JsonProperty("estado")
    @SerializedName("estado")
    private Boolean estado;

    @JsonProperty("tipoAhorroId")
    @SerializedName("tipoAhorroId")
    private Integer tipoAhorroId;

    @JsonProperty("tipoAhorroNombre")
    @SerializedName("tipoAhorroNombre")
    private String tipoAhorroNombre;

    @JsonProperty("monedaId")
    @SerializedName("monedaId")
    private Integer monedaId;

    @JsonProperty("monedaNombre")
    @SerializedName("monedaNombre")
    private String monedaNombre;

    @JsonProperty("monedaCodigo")
    @SerializedName("monedaCodigo")
    private String monedaCodigo;

    @JsonProperty("entidadFinancieraId")
    @SerializedName("entidadFinancieraId")
    private Integer entidadFinancieraId;

    @JsonProperty("entidadFinancieraNombre")
    @SerializedName("entidadFinancieraNombre")
    private String entidadFinancieraNombre;

    @JsonProperty("tipoCobroId")
    @SerializedName("tipoCobroId")
    private Integer tipoCobroId;

    @JsonProperty("tipoCobroNombre")
    @SerializedName("tipoCobroNombre")
    private String tipoCobroNombre;

    @JsonProperty("usuarioId")
    @SerializedName("usuarioId")
    private Long usuarioId;

}
