package py.com.fuentepy.appfinanzasBackend.resource.fcm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author vinsfran
 */
@Data
public class NotificationRequestModel {

    @JsonProperty("to")
    @SerializedName("to")
    private String to;

    @JsonProperty("notification")
    @SerializedName("notification")
    private NotificationDataModel notification;

    @JsonProperty("data")
    @SerializedName("data")
    private DataModel data;

}
