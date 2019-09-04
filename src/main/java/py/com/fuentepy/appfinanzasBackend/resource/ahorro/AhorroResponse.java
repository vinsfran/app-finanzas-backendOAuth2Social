package py.com.fuentepy.appfinanzasBackend.resource.ahorro;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;

import java.util.List;

@Data
@ApiModel(value = "ahorroResponse", description = "Response de Ahorro")
public class AhorroResponse extends BaseResponse {

    @JsonProperty("data")
    @SerializedName("data")
    private AhorroModel ahorroModel;

    AhorroResponse(Integer status, List<MessageResponse> messages, AhorroModel ahorroModel) {
        super(status, messages);
        this.ahorroModel = ahorroModel;
    }

}
