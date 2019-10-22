package py.com.fuentepy.appfinanzasBackend.resource.ahorroTipo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;

import java.util.List;

@Data
@ApiModel(value = "ahorroTipoResponse", description = "Response de Ahorro Tipo")
public class AhorroTipoResponse extends BaseResponse {

    @JsonProperty("data")
    @SerializedName("data")
    private AhorroTipoModel ahorroTipoModel;

    AhorroTipoResponse(Integer status, List<MessageResponse> messages, AhorroTipoModel ahorroTipoModel) {
        super(status, messages);
        this.ahorroTipoModel = ahorroTipoModel;
    }

}
