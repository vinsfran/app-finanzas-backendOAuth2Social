package py.com.fuentepy.appfinanzasBackend.resource.concepto;

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
@ApiModel(value = "conceptoRequestCobro", description = "Ejemplo de para Cobro de un Concepto")
public class ConceptoRequestCobro implements Serializable {

    @ApiModelProperty(value = "Identificador Unico del Concepto", required = true)
    @JsonProperty("id")
    @SerializedName("id")
    private Long id;

    @JsonProperty("numero_comprobante")
    @SerializedName("numero_comprobante")
    private String numeroComprobante;

    @JsonProperty("monto_cobrado")
    @SerializedName("monto_cobrado")
    private Double montoCobrado;

    @JsonProperty("archivos")
    @SerializedName("archivos")
    List<ArchivoModel> archivoModels;

}
