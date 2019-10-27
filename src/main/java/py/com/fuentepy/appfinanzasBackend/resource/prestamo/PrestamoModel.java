package py.com.fuentepy.appfinanzasBackend.resource.prestamo;

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

    @JsonProperty("numero_comprobante")
    @SerializedName("numero_comprobante")
    private String numeroComprobante;

    @JsonProperty("monto_prestamo")
    @SerializedName("monto_prestamo")
    private Double montoPrestamo;

    @JsonProperty("fecha_desembolso")
    @SerializedName("fecha_desembolso")
    private Date fechaDesembolso;

    @JsonProperty("fecha_vencimiento")
    @SerializedName("fecha_vencimiento")
    private Date fechaVencimiento;

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
    private Long cantidadCuotasPagadas;

    @JsonProperty("monto_cuota")
    @SerializedName("monto_cuota")
    private Double montoCuota;

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
