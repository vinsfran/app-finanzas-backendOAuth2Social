package py.com.fuentepy.appfinanzasBackend.resource.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;

import java.util.List;

@Data
@ApiModel(value = "usuarioResponse", description = "Response de Usuario")
public class UsuarioResponse extends BaseResponse {

    @JsonProperty("data")
    @SerializedName("data")
    private UsuarioModel usuarioModel;

    public UsuarioResponse(Integer status, List<MessageResponse> messages, UsuarioModel usuarioModel) {
        super(status, messages);
        this.usuarioModel = usuarioModel;
    }

}
