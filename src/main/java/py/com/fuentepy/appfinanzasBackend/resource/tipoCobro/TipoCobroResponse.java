package py.com.fuentepy.appfinanzasBackend.resource.tipoCobro;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;

import java.util.List;

@Data
@ApiModel(value = "tipoCobroResponse", description = "Response de Tipo Cobro")
public class TipoCobroResponse extends BaseResponse {

    @JsonProperty("data")
    @SerializedName("data")
    private TipoCobroModel tipoCobroModel;

    TipoCobroResponse(Integer status, List<MessageResponse> messages, TipoCobroModel tipoCobroModel) {
        super(status, messages);
        this.tipoCobroModel = tipoCobroModel;
    }

}
