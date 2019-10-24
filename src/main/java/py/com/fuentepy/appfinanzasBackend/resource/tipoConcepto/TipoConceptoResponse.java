package py.com.fuentepy.appfinanzasBackend.resource.tipoConcepto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;

import java.util.List;

@Data
@ApiModel(value = "tipoConceptoResponse", description = "Response de Tipo Concepto")
public class TipoConceptoResponse extends BaseResponse {

    @JsonProperty("data")
    @SerializedName("data")
    private TipoConceptoModel tipoConceptoModel;

    TipoConceptoResponse(Integer status, List<MessageResponse> messages, TipoConceptoModel tipoConceptoModel) {
        super(status, messages);
        this.tipoConceptoModel = tipoConceptoModel;
    }

}
