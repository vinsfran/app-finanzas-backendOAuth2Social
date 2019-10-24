package py.com.fuentepy.appfinanzasBackend.resource.tarjeta;

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
public class TarjetaModel implements Serializable {

    @JsonProperty("id")
    @SerializedName("id")
    private Long id;

    @JsonProperty("numero_tarjeta")
    @SerializedName("numero_tarjeta")
    private String numeroTarjeta;

    @JsonProperty("marca")
    @SerializedName("marca")
    private String marca;

    @JsonProperty("linea_credito")
    @SerializedName("linea_credito")
    private Long lineaCredito;

    @ApiModelProperty(value = "Fecha de Vencimiento", required = false, example = "AAAA-MM-DD")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("fecha_vencimiento")
    @SerializedName("fecha_vencimiento")
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    private Date fechaVencimiento;

    @JsonProperty("estado")
    @SerializedName("estado")
    private Boolean estado;

    @JsonProperty("entidad_financiera_id")
    @SerializedName("entidad_financiera_id")
    private Long entidadFinancieraId;

    @JsonProperty("entidad_financiera_nombre")
    @SerializedName("entidad_financiera_nombre")
    private String entidadFinancieraNombre;

    @JsonProperty("usuario_id")
    @SerializedName("usuario_id")
    private Long usuarioId;

}
