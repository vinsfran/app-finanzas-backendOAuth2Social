package py.com.fuentepy.appfinanzasBackend.resource.prestamo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;

import java.util.List;

@Data
@ApiModel(value = "prestamoResponse", description = "Response de Prestamo")
public class PrestamoResponse extends BaseResponse {

    @JsonProperty("data")
    @SerializedName("data")
    private PrestamoModel prestamoModel;

    PrestamoResponse(Integer status, List<MessageResponse> messages, PrestamoModel prestamoModel) {
        super(status, messages);
        this.prestamoModel = prestamoModel;
    }

}
