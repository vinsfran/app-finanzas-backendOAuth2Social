package py.com.fuentepy.appfinanzasBackend.resource.concepto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;

import java.util.List;

@Data
@ApiModel(value = "conceptoResponse", description = "Response de Concepto")
public class ConceptoResponse extends BaseResponse {

    @JsonProperty("data")
    @SerializedName("data")
    private ConceptoModel conceptoModel;

    ConceptoResponse(Integer status, List<MessageResponse> messages, ConceptoModel conceptoModel) {
        super(status, messages);
        this.conceptoModel = conceptoModel;
    }

}
