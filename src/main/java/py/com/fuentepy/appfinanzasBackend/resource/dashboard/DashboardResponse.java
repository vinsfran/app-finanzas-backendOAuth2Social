package py.com.fuentepy.appfinanzasBackend.resource.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;

import java.util.List;

@Data
@ApiModel(value = "DashboardResponse", description = "Response del Dashboard")
public class DashboardResponse extends BaseResponse {

    @JsonProperty("data")
    @SerializedName("data")
    private DashboardModel dashboardModel;

    DashboardResponse(Integer status, List<MessageResponse> messages, DashboardModel dashboardModel) {
        super(status, messages);
        this.dashboardModel = dashboardModel;
    }

}
