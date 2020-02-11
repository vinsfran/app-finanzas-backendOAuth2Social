package py.com.fuentepy.appfinanzasBackend.resource.fcm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author vinsfran
 */
@Data
public class DataModel {

    @JsonProperty("hello")
    @SerializedName("hello")
    private String hello;

    @JsonProperty("click_action")
    @SerializedName("click_action")
    private String clickAction;


}
