package py.com.fuentepy.appfinanzasBackend.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class DateUtil {

    private static final Log LOG = LogFactory.getLog(DateUtil.class);

    public static Date sumarDiasAFecha(Date fecha, int dias) {
        if (dias == 0) return fecha;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, dias);
        return calendar.getTime();
    }

    public static Date restarDiasAFecha(Date fecha, int dias) {
        if (dias == 0) return fecha;
        dias = dias * -1;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, dias);
        return calendar.getTime();
    }

    public static Integer extraerAnio(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        return calendar.get(Calendar.YEAR);
    }

    public static Integer extraerMes(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static Integer extraerDia(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean compararFechas(Date fecha1, Date fecha2) {
        if (extraerAnio(fecha1).equals(extraerAnio(fecha2))) {
            if (extraerMes(fecha1).equals(fecha2)) {
                if (extraerDia(fecha1).equals(extraerDia(fecha2))) {
                    return true;
                }
            }
        }
        return false;
    }
}
