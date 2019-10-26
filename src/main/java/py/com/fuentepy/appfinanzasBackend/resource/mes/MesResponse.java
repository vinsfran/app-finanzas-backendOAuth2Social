package py.com.fuentepy.appfinanzasBackend.resource.mes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;

import java.util.List;

@Data
@ApiModel(value = "mesResponse", description = "Response de Mes")
public class MesResponse extends BaseResponse {

    @JsonProperty("data")
    @SerializedName("data")
    private MesModel mesModel;

    MesResponse(Integer status, List<MessageResponse> messages, MesModel mesModel) {
        super(status, messages);
        this.mesModel = mesModel;
    }

}
