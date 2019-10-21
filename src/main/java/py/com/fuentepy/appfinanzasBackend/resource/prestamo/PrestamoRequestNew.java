package py.com.fuentepy.appfinanzasBackend.resource.prestamo;

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
@ApiModel(value = "prestamoNew", description = "Ejemplo de para un nuevo Prestamo")
public class PrestamoRequestNew implements Serializable {

    @ApiModelProperty(value = "Monto del Prestamo", required = true)
    @JsonProperty("monto_prestamo")
    @SerializedName("monto_prestamo")
    private Double montoPrestamo;

    @ApiModelProperty(value = "Fecha de Desembolso", required = false, example = "AAAA-MM-DD")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("fecha_desembolso")
    @SerializedName("fecha_desembolso")
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    private Date fechaDesembolso;

    @ApiModelProperty(value = "Fecha de Vencimiento", required = false, example = "AAAA-MM-DD")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("fecha_vencimiento")
    @SerializedName("fecha_vencimiento")
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    private Date fechaVencimiento;

    @ApiModelProperty(value = "Interes del Prestamo", required = true)
    @JsonProperty("interes")
    @SerializedName("interes")
    private Double interes;

    @ApiModelProperty(value = "Tasa del Prestamo", required = true)
    @JsonProperty("tasa")
    @SerializedName("tasa")
    private Double tasa;

    @ApiModelProperty(value = "Cantidad Cuotas del Prestamo", required = true)
    @JsonProperty("cantidad_cuotas")
    @SerializedName("cantidad_cuotas")
    private Integer cantidadCuotas;

    @ApiModelProperty(value = "Cantidad Cuotas Pagadas del Prestamo", required = true)
    @JsonProperty("cantidad_cuotas_pagadas")
    @SerializedName("cantidad_cuotas_pagadas")
    private Long cantidadCuotasPagadas;

    @ApiModelProperty(value = "Monto Cuota del Prestamo", required = true)
    @JsonProperty("monto_cuota")
    @SerializedName("monto_cuota")
    private Double montoCuota;

    @ApiModelProperty(value = "Monto Pagado del Prestamo", required = true)
    @JsonProperty("monto_pagado")
    @SerializedName("monto_pagado")
    private Double montoPagado;

    @ApiModelProperty(value = "Destino del Prestamo", required = true)
    @JsonProperty("destino_prestamo")
    @SerializedName("destino_prestamo")
    private String destinoPrestamo;

    @ApiModelProperty(value = "Estado del Prestamo", required = true)
    @JsonProperty("estado")
    @SerializedName("estado")
    private Boolean estado;

    @ApiModelProperty(value = "Identificador de la Moneda", required = true)
    @JsonProperty("moneda_id")
    @SerializedName("moneda_id")
    private Integer monedaId;

    @ApiModelProperty(value = "Identificador de la Entidad Financiera", required = true)
    @JsonProperty("entidad_financiera_id")
    @SerializedName("entidad_financiera_id")
    private Long entidadFinancieraId;

}
