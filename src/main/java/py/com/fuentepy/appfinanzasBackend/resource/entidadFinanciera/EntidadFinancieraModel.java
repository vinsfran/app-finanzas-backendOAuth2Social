package py.com.fuentepy.appfinanzasBackend.resource.entidadFinanciera;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author vinsfran
 */
@Data
@ApiModel(value = "entidadFinancieraModel", description = "Ejemplo de para un modelo de Entidad Financiera")
public class EntidadFinancieraModel implements Serializable {

    @JsonProperty("id")
    @SerializedName("id")
    private Long id;

    @JsonProperty("nombre")
    @SerializedName("nombre")
    private String nombre;

}
