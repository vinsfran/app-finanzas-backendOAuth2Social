package py.com.fuentepy.appfinanzasBackend.resource.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;

import java.util.List;

@Data
@ApiModel(value = "listStringResponse", description = "Response de ListStringResponse")
public class ListStringResponse extends BaseResponse {

    @JsonProperty("data")
    @SerializedName("data")
    private List<String> stringList;

    public ListStringResponse(Integer status, List<MessageResponse> messages, List<String> stringList) {
        super(status, messages);
        this.stringList = stringList;
    }

}
