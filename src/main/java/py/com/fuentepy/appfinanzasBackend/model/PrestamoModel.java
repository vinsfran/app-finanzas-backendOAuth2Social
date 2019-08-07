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
public class PrestamoModel implements Serializable {

    @JsonProperty("id")
    @SerializedName("id")
    private Long id;

    @JsonProperty("montoPrestamo")
    @SerializedName("montoPrestamo")
    private Long montoPrestamo;

    @JsonProperty("fechaDesembolso")
    @SerializedName("fechaDesembolso")
    private Date fechaDesembolso;

    @JsonProperty("fechaVencimiento")
    @SerializedName("fechaVencimiento")
    private Date fechaVencimiento;

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

    @JsonProperty("montoPagado")
    @SerializedName("montoPagado")
    private Long montoPagado;

    @JsonProperty("destinoPrestamo")
    @SerializedName("destinoPrestamo")
    private String destinoPrestamo;

    @JsonProperty("estado")
    @SerializedName("estado")
    private Boolean estado;

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

    @JsonProperty("usuarioId")
    @SerializedName("usuarioId")
    private Long usuarioId;

}
