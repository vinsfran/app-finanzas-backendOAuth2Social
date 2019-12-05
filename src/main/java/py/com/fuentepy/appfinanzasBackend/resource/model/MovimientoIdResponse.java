package py.com.fuentepy.appfinanzasBackend.resource.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import py.com.fuentepy.appfinanzasBackend.data.entity.Movimiento;
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;

import java.util.List;

@Data
@ApiModel(value = "ahorroResponse", description = "Response de Ahorro")
public class MovimientoIdResponse extends BaseResponse {

    @JsonProperty("movimiento_id")
    @SerializedName("movimiento_id")
    private Long movimientoId;

    public MovimientoIdResponse(Integer status, List<MessageResponse> messages, Long movimientoId) {
        super(status, messages);
        this.movimientoId = movimientoId;
    }

}
