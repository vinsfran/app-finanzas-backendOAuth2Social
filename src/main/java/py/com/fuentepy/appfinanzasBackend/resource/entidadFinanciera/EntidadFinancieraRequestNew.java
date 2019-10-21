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
@ApiModel(value = "entidadFinancieraRequestNew", description = "Ejemplo de para un modelo nuevo de Entidad Financiera")
public class EntidadFinancieraRequestNew implements Serializable {

    @JsonProperty("nombre")
    @SerializedName("nombre")
    private String nombre;

}
