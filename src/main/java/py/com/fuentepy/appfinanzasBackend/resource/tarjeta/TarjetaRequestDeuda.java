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
@ApiModel(value = "tarjetaRequestDeuda", description = "Ejemplo de para Deuda de una Tarjeta")
public class TarjetaRequestDeuda implements Serializable {

    @ApiModelProperty(value = "Identificador Unico del Tarjeta", required = true)
    @JsonProperty("id")
    @SerializedName("id")
    private Long id;

    @JsonProperty("deuda")
    @SerializedName("deuda")
    private Double deuda;

    @JsonProperty("detalle")
    @SerializedName("detalle")
    private String detalle;

}
