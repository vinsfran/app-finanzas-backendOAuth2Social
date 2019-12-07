package py.com.fuentepy.appfinanzasBackend.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.UUID;

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

    public static String armarNombreArchivo(Long tablaId, String tablaNombre, String contentType, String nombreArchivo) {
        String ext = contentType.split("/")[1];
        String uuid = UUID.randomUUID().toString();
        nombreArchivo = nombreArchivo.replace(" ", "");
        nombreArchivo = removeCaracteresEspeciales(nombreArchivo);
        String b64 = encodeBase64(tablaId + tablaNombre + nombreArchivo).toLowerCase() + uuid;
        nombreArchivo = b64 + "." + ext;
        return nombreArchivo;
    }

    public static String removeCaracteresEspeciales(String input) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }//for i
        return output;
    }
}
