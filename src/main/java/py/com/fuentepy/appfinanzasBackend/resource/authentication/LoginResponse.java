package py.com.fuentepy.appfinanzasBackend.resource.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import py.com.fuentepy.appfinanzasBackend.resource.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.MessageResponse;

import java.util.List;

@Data
@ApiModel(value = "LoginResponse", description = "Response del Login")
public class LoginResponse extends BaseResponse {

    @JsonProperty("token")
    @SerializedName("token")
    private TokenDTO tokenDTO;

    LoginResponse(Integer status, List<MessageResponse> messages, TokenDTO tokenDTO) {
        super(status, messages);
        this.tokenDTO = tokenDTO;
    }

}
