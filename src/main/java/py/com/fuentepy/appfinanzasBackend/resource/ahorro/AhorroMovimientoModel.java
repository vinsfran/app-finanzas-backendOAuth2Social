package py.com.fuentepy.appfinanzasBackend.resource.ahorro;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
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
@ApiModel(value = "ahorroMovimientoModel", description = "Ejemplo de para Movimiento de un Ahorro")
public class AhorroMovimientoModel implements Serializable {

    @ApiModelProperty(value = "Identificador Unico del Movimiento", required = true)
    @JsonProperty("movimiento_id")
    @SerializedName("movimiento_id")
    private Long movimientoId;

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

    @JsonProperty("tipo_movimiento")
    @SerializedName("tipo_movimiento")
    private String tipoMovimiento;

    @JsonProperty("moneda")
    @SerializedName("moneda")
    private String moneda;

}
