package py.com.fuentepy.appfinanzasBackend.resource.tipoPago;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;

import java.util.List;

@Data
@ApiModel(value = "tipoPagoResponse", description = "Response de Tipo Pago")
public class TipoPagoResponse extends BaseResponse {

    @JsonProperty("data")
    @SerializedName("data")
    private TipoPagoModel tipoPagoModel;

    TipoPagoResponse(Integer status, List<MessageResponse> messages, TipoPagoModel tipoPagoModel) {
        super(status, messages);
        this.tipoPagoModel = tipoPagoModel;
    }

}
