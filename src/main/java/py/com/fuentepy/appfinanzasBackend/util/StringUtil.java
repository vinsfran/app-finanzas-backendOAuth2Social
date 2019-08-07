package py.com.fuentepy.appfinanzasBackend.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class StringUtil {

    private static final Log LOG = LogFactory.getLog(StringUtil.class);

    public static String encodeBase64(String cadena) {
        String retorno = Base64.getEncoder().encodeToString(cadena.getBytes());
        return retorno;
    }

    public static String decodeBase64(String cadena) {
        String retorno = new String(Base64.getDecoder().decode(cadena));
        return retorno;
    }
}
