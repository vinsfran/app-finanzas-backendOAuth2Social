package py.com.fuentepy.appfinanzasBackend.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.regex.Pattern;

public class TokenUtil {

    private static final Log LOG = LogFactory.getLog(TokenUtil.class);

    public static String decodeToken(String tokenCompleto) {
        String separador = Pattern.quote(".");
        String[] parts = tokenCompleto.split(separador);
        return StringUtil.decodeBase64(parts[1]);
    }

    public static Long getIdFromToken(String tokenCompleto) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(decodeToken(tokenCompleto), JsonObject.class);
        return Long.parseLong(jsonObject.get("id").toString());
    }

    public static String getUserNameFromToken(String tokenCompleto) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(decodeToken(tokenCompleto), JsonObject.class);
        return jsonObject.get("username").toString().trim();
    }
}
