package py.com.fuentepy.appfinanzasBackend.resource.movimiento;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;

import java.util.List;

@Data
@ApiModel(value = "movimientoResponse", description = "Response de Movimiento")
public class MovimientoResponse extends BaseResponse {

    @JsonProperty("data")
    @SerializedName("data")
    private MovimientoModel movimientoModel;

    MovimientoResponse(Integer status, List<MessageResponse> messages, MovimientoModel movimientoModel) {
        super(status, messages);
        this.movimientoModel = movimientoModel;
    }

}
