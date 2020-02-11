package py.com.fuentepy.appfinanzasBackend.resource.fcm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author vinsfran
 */
@Data
public class NotificationDataModel {

    @JsonProperty("title")
    @SerializedName("title")
    private String title;

    @JsonProperty("body")
    @SerializedName("body")
    private String body;


}
