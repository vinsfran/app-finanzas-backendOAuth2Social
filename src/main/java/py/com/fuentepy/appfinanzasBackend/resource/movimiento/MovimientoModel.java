package py.com.fuentepy.appfinanzasBackend.resource.movimiento;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import py.com.fuentepy.appfinanzasBackend.json.JsonDateSimpleDeserializer;
import py.com.fuentepy.appfinanzasBackend.json.JsonDateSimpleSerializer;

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

    @JsonProperty("numero_comprobante")
    @SerializedName("numero_comprobante")
    private String numeroComprobante;

    @ApiModelProperty(value = "Fecha de Movimiento", required = false, example = "AAAA-MM-DD")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("fecha_movimiento")
    @SerializedName("fecha_movimiento")
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    private Date fechaMovimiento;

    @JsonProperty("monto")
    @SerializedName("monto")
    private Double monto;

    @JsonProperty("numero_cuota")
    @SerializedName("numero_cuota")
    private Integer numeroCuota;

    @JsonProperty("signo")
    @SerializedName("signo")
    private String signo;

    @JsonProperty("detalle")
    @SerializedName("detalle")
    private String detalle;

    @JsonProperty("comentario")
    @SerializedName("comentario")
    private String comentario;

    @JsonProperty("tabla_id")
    @SerializedName("tabla_id")
    private Long tablaId;

    @JsonProperty("tabla_name")
    @SerializedName("tabla_name")
    private String tablaName;

    @JsonProperty("moneda_id")
    @SerializedName("moneda_id")
    private Integer monedaId;

    @JsonProperty("moneda_nombre")
    @SerializedName("moneda_nombre")
    private String monedaNombre;

    @JsonProperty("usuario_id")
    @SerializedName("usuario_id")
    private Long usuarioId;

}
