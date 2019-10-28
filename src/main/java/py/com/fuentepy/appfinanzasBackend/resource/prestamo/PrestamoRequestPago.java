package py.com.fuentepy.appfinanzasBackend.resource.prestamo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import py.com.fuentepy.appfinanzasBackend.resource.archivo.ArchivoModel;

import java.io.Serializable;
import java.util.List;

/**
 * @author vinsfran
 */
@Data
@ApiModel(value = "prestamoRequestPago", description = "Ejemplo de para Pago de un Prestamo")
public class PrestamoRequestPago implements Serializable {

    @ApiModelProperty(value = "Identificador Unico del Prestamo", required = true)
    @JsonProperty("id")
    @SerializedName("id")
    private Long id;

    @JsonProperty("numero_comprobante")
    @SerializedName("numero_comprobante")
    private String numeroComprobante;

    @JsonProperty("monto_pagado")
    @SerializedName("monto_pagado")
    private Double montoPagado;

    @JsonProperty("numero_cuota")
    @SerializedName("numero_cuota")
    private Long numeroCuota;

    @JsonProperty("archivos")
    @SerializedName("archivos")
    List<ArchivoModel> archivoModels;

}
