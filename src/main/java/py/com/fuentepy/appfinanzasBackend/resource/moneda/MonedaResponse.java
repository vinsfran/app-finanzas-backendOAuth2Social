package py.com.fuentepy.appfinanzasBackend.resource.moneda;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;

import java.util.List;

@Data
@ApiModel(value = "monedaResponse", description = "Response de Moneda")
public class MonedaResponse extends BaseResponse {

    @JsonProperty("data")
    @SerializedName("data")
    private MonedaModel monedaModel;

    MonedaResponse(Integer status, List<MessageResponse> messages, MonedaModel monedaModel) {
        super(status, messages);
        this.monedaModel = monedaModel;
    }

}
