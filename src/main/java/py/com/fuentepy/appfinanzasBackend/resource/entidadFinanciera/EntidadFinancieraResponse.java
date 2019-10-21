package py.com.fuentepy.appfinanzasBackend.resource.entidadFinanciera;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;

import java.util.List;

@Data
@ApiModel(value = "entidadFinancieraResponse", description = "Response de Entidad Financiera")
public class EntidadFinancieraResponse extends BaseResponse {

    @JsonProperty("data")
    @SerializedName("data")
    private EntidadFinancieraModel entidadFinancieraModel;

    EntidadFinancieraResponse(Integer status, List<MessageResponse> messages, EntidadFinancieraModel entidadFinancieraModel) {
        super(status, messages);
        this.entidadFinancieraModel = entidadFinancieraModel;
    }

}
