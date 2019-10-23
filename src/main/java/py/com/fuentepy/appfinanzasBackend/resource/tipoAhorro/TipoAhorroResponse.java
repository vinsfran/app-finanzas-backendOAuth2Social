package py.com.fuentepy.appfinanzasBackend.resource.tipoAhorro;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;

import java.util.List;

@Data
@ApiModel(value = "ahorroTipoResponse", description = "Response de Tipo Ahorro")
public class TipoAhorroResponse extends BaseResponse {

    @JsonProperty("data")
    @SerializedName("data")
    private TipoAhorroModel tipoAhorroModel;

    TipoAhorroResponse(Integer status, List<MessageResponse> messages, TipoAhorroModel tipoAhorroModel) {
        super(status, messages);
        this.tipoAhorroModel = tipoAhorroModel;
    }

}
