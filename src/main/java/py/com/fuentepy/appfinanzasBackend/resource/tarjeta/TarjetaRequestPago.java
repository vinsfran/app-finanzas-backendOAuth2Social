package py.com.fuentepy.appfinanzasBackend.resource.tarjeta;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author vinsfran
 */
@Data
@ApiModel(value = "tarjetaRequestPago", description = "Ejemplo de para Pago de una Tarjeta")
public class TarjetaRequestPago implements Serializable {

    @ApiModelProperty(value = "Identificador Unico del Tarjeta", required = true)
    @JsonProperty("id")
    @SerializedName("id")
    private Long id;

    @JsonProperty("numero_comprobante")
    @SerializedName("numero_comprobante")
    private String numeroComprobante;

    @JsonProperty("monto_pagado")
    @SerializedName("monto_pagado")
    private Double montoPagado;

}
