package py.com.fuentepy.appfinanzasBackend.resource.archivo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;

import java.util.List;

@Data
@ApiModel(value = "archivoResponse", description = "Response de Archivo")
public class ArchivoResponse extends BaseResponse {

    @JsonProperty("data")
    @SerializedName("data")
    private List<ArchivoModel> archivoModelList;

    public ArchivoResponse(Integer status, List<MessageResponse> messages, List<ArchivoModel> archivoModelList) {
        super(status, messages);
        this.archivoModelList = archivoModelList;
    }

}
