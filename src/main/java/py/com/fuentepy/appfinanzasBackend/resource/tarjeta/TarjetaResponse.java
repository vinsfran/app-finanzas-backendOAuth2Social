package py.com.fuentepy.appfinanzasBackend.resource.tarjeta;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;

import java.util.List;

@Data
@ApiModel(value = "tarjetaResponse", description = "Response de Tarjeta")
public class TarjetaResponse extends BaseResponse {

    @JsonProperty("data")
    @SerializedName("data")
    private TarjetaModel tarjetaModel;

    TarjetaResponse(Integer status, List<MessageResponse> messages, TarjetaModel tarjetaModel) {
        super(status, messages);
        this.tarjetaModel = tarjetaModel;
    }

}
