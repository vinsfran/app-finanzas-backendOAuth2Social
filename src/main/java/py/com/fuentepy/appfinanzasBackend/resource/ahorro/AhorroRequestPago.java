package py.com.fuentepy.appfinanzasBackend.resource.ahorro;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import py.com.fuentepy.appfinanzasBackend.json.JsonDateSimpleDeserializer;
import py.com.fuentepy.appfinanzasBackend.json.JsonDateSimpleSerializer;
import py.com.fuentepy.appfinanzasBackend.resource.archivo.ArchivoModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author vinsfran
 */
@Data
@ApiModel(value = "ahorroRequestPago", description = "Ejemplo de para Pago de un Ahorro")
public class AhorroRequestPago implements Serializable {

    @ApiModelProperty(value = "Identificador Unico del Ahorro", required = true)
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
    private Integer numeroCuota;

}
