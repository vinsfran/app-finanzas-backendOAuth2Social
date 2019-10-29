package py.com.fuentepy.appfinanzasBackend.resource.tarjeta;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
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
@ApiModel(value = "tarjetaRequestUpdate", description = "Ejemplo de para editar Tarjeta")
public class TarjetaRequestUpdate implements Serializable {

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
    private Double lineaCredito;

    @JsonProperty("monto_disponible")
    @SerializedName("monto_disponible")
    private Double montoDisponible;

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

    @JsonProperty("moneda_id")
    @SerializedName("moneda_id")
    private Integer monedaId;

    @JsonProperty("entidad_financiera_id")
    @SerializedName("entidad_financiera_id")
    private Long entidadFinancieraId;

}
