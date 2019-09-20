package py.com.fuentepy.appfinanzasBackend.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vinsfran.
 */
public class JsonDateSimpleSerializer extends JsonSerializer<Date> {
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider arg2) throws IOException, JsonProcessingException {
        gen.writeString(formatter.format(value));
    }

}