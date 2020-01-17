package py.com.fuentepy.appfinanzasBackend.resource.prestamo;

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
public class PrestamoModel implements Serializable {

    @JsonProperty("id")
    @SerializedName("id")
    private Long id;

    @JsonProperty("numero_comprobante")
    @SerializedName("numero_comprobante")
    private String numeroComprobante;

    @JsonProperty("monto_prestamo")
    @SerializedName("monto_prestamo")
    private Double montoPrestamo;

    @JsonProperty("fecha_desembolso")
    @SerializedName("fecha_desembolso")
    private Date fechaDesembolso;

    @ApiModelProperty(value = "Fecha de Vencimiento", required = false, example = "AAAA-MM-DD")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("fecha_vencimiento")
    @SerializedName("fecha_vencimiento")
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    private Date fechaVencimiento;

    @ApiModelProperty(value = "Fecha Proximo Vencimiento", required = false, example = "AAAA-MM-DD")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("fecha_prox_vencimiento")
    @SerializedName("fecha_prox_vencimiento")
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    private Date fechaProxVencimiento;

    @JsonProperty("interes")
    @SerializedName("interes")
    private Double interes;

    @JsonProperty("tasa")
    @SerializedName("tasa")
    private Double tasa;

    @JsonProperty("cantidad_cuotas")
    @SerializedName("cantidad_cuotas")
    private Integer cantidadCuotas;

    @JsonProperty("cantidad_cuotas_pagadas")
    @SerializedName("cantidad_cuotas_pagadas")
    private Integer cantidadCuotasPagadas;

    @JsonProperty("siguiente_cuota")
    @SerializedName("siguiente_cuota")
    private Integer siguienteCuota;

    @JsonProperty("monto_cuota")
    @SerializedName("monto_cuota")
    private Double montoCuota;

    @JsonProperty("saldo_cuota")
    @SerializedName("saldo_cuota")
    private Double saldoCuota;

    @JsonProperty("monto_pagado")
    @SerializedName("monto_pagado")
    private Double montoPagado;

    @JsonProperty("destino_prestamo")
    @SerializedName("destino_prestamo")
    private String destinoPrestamo;

    @JsonProperty("estado")
    @SerializedName("estado")
    private Boolean estado;

    @JsonProperty("moneda_id")
    @SerializedName("moneda_id")
    private Integer monedaId;

    @JsonProperty("moneda_nombre")
    @SerializedName("moneda_nombre")
    private String monedaNombre;

    @JsonProperty("moneda_codigo")
    @SerializedName("moneda_codigo")
    private String monedaCodigo;

    @JsonProperty("entidad_financiera_id")
    @SerializedName("entidad_financiera_id")
    private Long entidadFinancieraId;

    @JsonProperty("entidad_financiera_nombre")
    @SerializedName("entidad_financiera_nombre")
    private String entidadFinancieraNombre;

}
