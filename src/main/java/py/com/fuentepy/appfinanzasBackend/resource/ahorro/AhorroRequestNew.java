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

@Data
@ApiModel(value = "ahorroRequestNew", description = "Ejemplo de para un nuevo Ahorro")
public class AhorroRequestNew implements Serializable {

    @ApiModelProperty(value = "Cantidad de Cobro", required = true)
    @JsonProperty("cantidad_cobro")
    @SerializedName("cantidad_cobro")
    private Double cantidadCobro;

    @ApiModelProperty(value = "Cantidad de Cuotas", required = true)
    @JsonProperty("cantidad_cuotas")
    @SerializedName("cantidad_cuotas")
    private Integer cantidadCuotas;

    @ApiModelProperty(value = "Cantidad de Cuotas Pagadas", required = true)
    @JsonProperty("cantidad_cuotas_pagadas")
    @SerializedName("cantidad_cuotas_pagadas")
    private Long cantidadCuotasPagadas;

    @ApiModelProperty(value = "Id de la Entidad Financiera", required = true)
    @JsonProperty("entidad_financiera_id")
    @SerializedName("entidad_financiera_id")
    private Long entidadFinancieraId;

    @ApiModelProperty(value = "Estado del Ahorro", required = true)
    @JsonProperty("estado")
    @SerializedName("estado")
    private Boolean estado;

    @ApiModelProperty(value = "Fecha de Inicio", required = false, example = "AAAA-MM-DD")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("fecha_inicio")
    @SerializedName("fecha_inicio")
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    private Date fechaInicio;

    @ApiModelProperty(value = "Fecha de Vencimiento", required = false, example = "AAAA-MM-DD")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("fecha_vencimiento")
    @SerializedName("fecha_vencimiento")
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    private Date fechaVencimiento;

    @JsonProperty("interes")
    @SerializedName("interes")
    private Double interes;

    @JsonProperty("moneda_id")
    @SerializedName("moneda_id")
    private Integer monedaId;

    @JsonProperty("monto_capital")
    @SerializedName("monto_capital")
    private Double montoCapital;

    @JsonProperty("monto_cuota")
    @SerializedName("monto_cuota")
    private Double montoCuota;

    @JsonProperty("monto_interes_cuota")
    @SerializedName("monto_interes_cuota")
    private Double montoInteresCuota;

    @JsonProperty("plazo_total")
    @SerializedName("plazo_total")
    private Integer plazoTotal;

    @JsonProperty("tasa")
    @SerializedName("tasa")
    private Double tasa;

    @JsonProperty("ahorro_tipo_id")
    @SerializedName("ahorro_tipo_id")
    private Long ahorroTipoId;

    @JsonProperty("tipo_cobro_id")
    @SerializedName("tipo_cobro_id")
    private Long tipoCobroId;
}
