package py.com.fuentepy.appfinanzasBackend.resource.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;

import java.util.List;

@Data
@ApiModel(value = "loginResponse", description = "Response Login")
public class LoginResponse extends BaseResponse {

    @JsonProperty("data")
    @SerializedName("data")
    private LoginModel loginModel;

    LoginResponse(Integer status, List<MessageResponse> messages, LoginModel loginModel) {
        super(status, messages);
        this.loginModel = loginModel;
    }

}
