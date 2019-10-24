package py.com.fuentepy.appfinanzasBackend.resource.presupuesto;

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
@ApiModel(value = "presupuestoRequestUpdate", description = "Ejemplo de para un Presupuesto a modificar")
public class PresupuestoRequestUpdate implements Serializable {

    @JsonProperty("id")
    @SerializedName("id")
    private Long id;

    @ApiModelProperty(value = "Fecha de Alta", required = false, example = "AAAA-MM-DD")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("fecha_alta")
    @SerializedName("fecha_alta")
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    private Date fechaAlta;

    @JsonProperty("monto")
    @SerializedName("monto")
    private Long monto;

    @JsonProperty("anio")
    @SerializedName("anio")
    private Integer anio;

    @JsonProperty("porcentaje_alerta")
    @SerializedName("porcentaje_alerta")
    private Integer porcentajeAlerta;

    @JsonProperty("mes_id")
    @SerializedName("mes_id")
    private Integer mesId;

    @JsonProperty("moneda_id")
    @SerializedName("moneda_id")
    private Integer monedaId;

}
