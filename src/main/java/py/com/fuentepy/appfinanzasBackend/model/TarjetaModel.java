package py.com.fuentepy.appfinanzasBackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author vinsfran
 */
@Data
public class TarjetaModel implements Serializable {

    @JsonProperty("id")
    @SerializedName("id")
    private Long id;

    @JsonProperty("numeroTarjeta")
    @SerializedName("numeroTarjeta")
    private String numeroTarjeta;

    @JsonProperty("marca")
    @SerializedName("marca")
    private String marca;

    @JsonProperty("lineaCredito")
    @SerializedName("lineaCredito")
    private Long lineaCredito;

    @JsonProperty("fechaVencimiento")
    @SerializedName("fechaVencimiento")
    private Date fechaVencimiento;

    @JsonProperty("estado")
    @SerializedName("estado")
    private Boolean estado;

    @JsonProperty("entidadFinancieraId")
    @SerializedName("entidadFinancieraId")
    private Integer entidadFinancieraId;

    @JsonProperty("entidadFinancieraNombre")
    @SerializedName("entidadFinancieraNombre")
    private String entidadFinancieraNombre;

    @JsonProperty("usuarioId")
    @SerializedName("usuarioId")
    private Long usuarioId;

}
