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
@ApiModel(value = "presupuestoModel", description = "Ejemplo de para un modelo de Presupuesto")
public class PresupuestoModel implements Serializable {

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
    private Double monto;

    @JsonProperty("anio")
    @SerializedName("anio")
    private Integer anio;

    @JsonProperty("porcentaje_alerta")
    @SerializedName("porcentaje_alerta")
    private Integer porcentajeAlerta;;

    @JsonProperty("mes_id")
    @SerializedName("mes_id")
    private Integer mesId;

    @JsonProperty("mes_nombre")
    @SerializedName("mes_nombre")
    private String mesNombre;

    @JsonProperty("mes_numero")
    @SerializedName("mes_numero")
    private Integer mesNumero;

    @JsonProperty("moneda_id")
    @SerializedName("moneda_id")
    private Integer monedaId;

    @JsonProperty("moneda_nombre")
    @SerializedName("moneda_nombre")
    private String monedaNombre;

    @JsonProperty("moneda_codigo")
    @SerializedName("moneda_codigo")
    private String monedaCodigo;

    @JsonProperty("usuario_id")
    @SerializedName("usuario_id")
    private Long usuarioId;

}
