package py.com.fuentepy.appfinanzasBackend.resource.presupuesto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;

import java.util.List;

@Data
@ApiModel(value = "presupuestoResponse", description = "Response de Presupuesto")
public class PresupuestoResponse extends BaseResponse {

    @JsonProperty("data")
    @SerializedName("data")
    private PresupuestoModel presupuestoModel;

    PresupuestoResponse(Integer status, List<MessageResponse> messages, PresupuestoModel presupuestoModel) {
        super(status, messages);
        this.presupuestoModel = presupuestoModel;
    }

}
