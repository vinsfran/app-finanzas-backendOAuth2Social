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
public class MovimientoModel implements Serializable {

    @JsonProperty("id")
    @SerializedName("id")
    private Long id;

    @JsonProperty("numeroComprobante")
    @SerializedName("numeroComprobante")
    private String numeroComprobante;

    @JsonProperty("fechaMovimiento")
    @SerializedName("fechaMovimiento")
    private Date fechaMovimiento;

    @JsonProperty("montoPagado")
    @SerializedName("montoPagado")
    private Long montoPagado;

    @JsonProperty("nombreEntidad")
    @SerializedName("nombreEntidad")
    private String nombreEntidad;

    @JsonProperty("prestamoId")
    @SerializedName("prestamoId")
    private Long prestamoId;

    @JsonProperty("ahorroId")
    @SerializedName("ahorroId")
    private Long ahorroId;

    @JsonProperty("tarjetaId")
    @SerializedName("tarjetaId")
    private Long tarjetaId;

    @JsonProperty("numeroCuota")
    @SerializedName("numeroCuota")
    private Long numeroCuota;

    @JsonProperty("conceptoId")
    @SerializedName("conceptoId")
    private Integer conceptoId;

    @JsonProperty("conceptoNombre")
    @SerializedName("conceptoNombre")
    private String conceptoNombre;

    @JsonProperty("tipo_concepto_id")
    @SerializedName("tipo_concepto_id")
    private Integer tipoConceptoId;

    @JsonProperty("tipo_concepto_nombre")
    @SerializedName("tipo_concepto_nombre")
    private String tipoConceptoNombre;

    @JsonProperty("tipo_concepto_signo")
    @SerializedName("tipo_concepto_signo")
    private String tipoConceptoSigno;

    @JsonProperty("monedaId")
    @SerializedName("monedaId")
    private Integer monedaId;

    @JsonProperty("monedaNombre")
    @SerializedName("monedaNombre")
    private String monedaNombre;

    @JsonProperty("monedaCodigo")
    @SerializedName("monedaCodigo")
    private String monedaCodigo;

    @JsonProperty("tipoPagoId")
    @SerializedName("tipoPagoId")
    private Long tipoPagoId;

    @JsonProperty("tipoPagoNombre")
    @SerializedName("tipoPagoNombre")
    private String tipoPagoNombre;

    @JsonProperty("usuarioId")
    @SerializedName("usuarioId")
    private Long usuarioId;

}
