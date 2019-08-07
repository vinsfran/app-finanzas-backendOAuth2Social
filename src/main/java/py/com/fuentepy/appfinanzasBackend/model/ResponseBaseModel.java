package py.com.fuentepy.appfinanzasBackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ResponseBaseModel implements Serializable {

    @NotNull
    @NotBlank
    @JsonProperty("code")
    @SerializedName("code")
    private Integer code;
    @NotNull
    @NotBlank
    @JsonProperty("status")
    @SerializedName("status")
    private boolean status;
    @NotNull
    @NotBlank
    @JsonProperty("message")
    @SerializedName("message")
    private String message;

    public ResponseBaseModel() {
    }

    public ResponseBaseModel(Integer code, boolean status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseBaseModel{" +
                "code=" + code +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
