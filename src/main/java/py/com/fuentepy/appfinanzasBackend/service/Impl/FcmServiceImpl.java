package py.com.fuentepy.appfinanzasBackend.service.Impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import py.com.fuentepy.appfinanzasBackend.data.entity.Dispositivo;
import py.com.fuentepy.appfinanzasBackend.data.entity.Mensaje;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.data.repository.DispositivoRepository;
import py.com.fuentepy.appfinanzasBackend.data.repository.MensajeRepository;
import py.com.fuentepy.appfinanzasBackend.resource.fcm.NotificationDataModel;
import py.com.fuentepy.appfinanzasBackend.resource.fcm.NotificationRequestModel;
import py.com.fuentepy.appfinanzasBackend.service.FcmService;
import py.com.fuentepy.appfinanzasBackend.util.DateUtil;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * @author vinsfran
 */
@Service("fcmServiceImpl")
public class FcmServiceImpl implements FcmService {

    private static final Log LOG = LogFactory.getLog(FcmServiceImpl.class);

    @Value("${app.fcm.url.send}")
    private String urlFcmSend;

    @Value("${app.fcm.key}")
    private String key;

    @Autowired
    private DispositivoRepository dispositivoRepository;

    @Autowired
    private MensajeRepository mensajeRepository;

    @Override
    public String send() throws Exception {
        String body = null;
        HttpResponse<JsonNode> response;
        try {
            for (Dispositivo dispositivo : dispositivoRepository.findAll()) {
                for (Mensaje mensaje : mensajeRepository.findByUsuarioId(dispositivo.getUsuarioId())) {
                    if (DateUtil.compararFechas(new Date(), mensaje.getFechaEnvio())) {
                        LOG.info(mensaje.getBody());
                        NotificationRequestModel notificationRequestModel = new NotificationRequestModel();
                        notificationRequestModel.setTo(dispositivo.getToken());
//                    notificationRequestModel.setTo("c-HC2bTtLJE:APA91bF7lUnAqFwyP-2wMJuQsE1QBIQA89Pig1HryhvovrY8aI1EqC_5CkMsRbu5cyPNAqDXVD9_4Nwuohj9XqA8OjpkqU77nV13J6dTKmM2kS8J2HKt3-SRjM_3ORltSc0vOkVZCZao");
                        notificationRequestModel.setNotification(new NotificationDataModel());
                        notificationRequestModel.getNotification().setBody(mensaje.getBody());
                        notificationRequestModel.getNotification().setTitle(mensaje.getTitulo());
                        Gson gson = new Gson();
                        Type type = new TypeToken<NotificationRequestModel>() {
                        }.getType();
                        String json = gson.toJson(notificationRequestModel, type);
                        StringEntity input = new StringEntity(json);
                        input.setContentType("application/json");
                        response = Unirest.post(urlFcmSend)
                                .header("Content-Type", "application/json")
                                .header("Authorization", "key=" + key)
                                .body(json)
                                .asJson();
                        if (response.getStatus() == 200) {
                            body = response.getBody().toString();
                        } else {
                            throw new Exception("Error code: " + response.getStatus());
                        }
                    }
                }
            }
        } catch (UnirestException ex) {
            throw new Exception(ex.getMessage());
        }
        return body;
    }

    // @Scheduled(cron = “1 2 3 4 5 6”, zone = “America/Mexico_City”) en donde:
//1 -> segundos (0-59)
//2 -> minutos (0-59)
//3 -> horas (0-23)
//4 -> días (1-31)
//5 -> meses (1-12)
//6 -> día de la semana (1-7)
    @Scheduled(cron = "0 0 10 * * *")
    public void envioMensajesAutomatico() throws Exception {
        send();
    }

}