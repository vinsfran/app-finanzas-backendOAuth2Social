package py.com.fuentepy.appfinanzasBackend.resource.ahorro;

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

}
