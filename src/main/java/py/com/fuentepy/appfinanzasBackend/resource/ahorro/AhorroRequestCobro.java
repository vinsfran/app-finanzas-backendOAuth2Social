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
@ApiModel(value = "ahorroRequestCobro", description = "Ejemplo de para Cobro de un Ahorro")
public class AhorroRequestCobro implements Serializable {

    @ApiModelProperty(value = "Identificador Unico del Ahorro", required = true)
    @JsonProperty("id")
    @SerializedName("id")
    private Long id;

    @JsonProperty("numero_comprobante")
    @SerializedName("numero_comprobante")
    private String numeroComprobante;

    @JsonProperty("monto_cobrado")
    @SerializedName("monto_cobrado")
    private Double montoCobrado;

    @JsonProperty("numero_cuota")
    @SerializedName("numero_cuota")
    private Integer numeroCuota;

    @ApiModelProperty(value = "Fecha de Movimiento", required = false, example = "AAAA-MM-DD")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("fecha_movimiento")
    @SerializedName("fecha_movimiento")
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    private Date fechaMovimiento;

    @JsonProperty("comentario")
    @SerializedName("comentario")
    private String comentario;

}
