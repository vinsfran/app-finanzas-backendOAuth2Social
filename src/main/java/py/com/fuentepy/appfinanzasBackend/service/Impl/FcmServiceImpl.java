package py.com.fuentepy.appfinanzasBackend.service.Impl;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import py.com.fuentepy.appfinanzasBackend.data.entity.Dispositivo;
import py.com.fuentepy.appfinanzasBackend.data.entity.Mensaje;
import py.com.fuentepy.appfinanzasBackend.data.repository.DispositivoRepository;
import py.com.fuentepy.appfinanzasBackend.data.repository.MensajeRepository;
import py.com.fuentepy.appfinanzasBackend.resource.fcm.DataModel;
import py.com.fuentepy.appfinanzasBackend.resource.fcm.NotificationDataModel;
import py.com.fuentepy.appfinanzasBackend.resource.fcm.NotificationRequestModel;
import py.com.fuentepy.appfinanzasBackend.service.FcmService;
import py.com.fuentepy.appfinanzasBackend.util.DateUtil;

import java.util.Date;

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
                        notificationRequestModel.getNotification().setTitle(mensaje.getTitulo());
                        notificationRequestModel.getNotification().setBody(mensaje.getBody());
                        notificationRequestModel.getNotification().setIcon("ic_stat_icon_controlate");
                        notificationRequestModel.getNotification().setColor("#25cf38");
                        DataModel dataModel = new DataModel();
                        dataModel.setClickAction("FLUTTER_NOTIFICATION_CLICK");
                        notificationRequestModel.setData(dataModel);

//                        Gson gson = new Gson();
//                        Type type = new TypeToken<NotificationRequestModel>() {
//                        }.getType();
//                        String json = gson.toJson(notificationRequestModel, type);
//                        LOG.info(json);
//                        StringEntity input = new StringEntity(json);
//                        input.setContentType("application/json");

                        response = Unirest.post(urlFcmSend)
                                .header("Content-Type", "application/json")
                                .header("Authorization", "key=" + key)
                                .body(notificationRequestModel)
                                .asJson();
                        if (response.getStatus() == 200) {
                            body = response.getBody().toString();
                            LOG.info(body);
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
//    @Scheduled(cron = "${app.fcm.cron.mensajes}")
    // Se ejecuta cada 24 horas en segundos
//    @Scheduled(fixedRate = 86400)
    @Scheduled(fixedRateString = "${app.fcm.cron.mensajes}")
    public void envioMensajesAutomatico() throws Exception {
        LOG.info("ENTRO en envioMensajesAutomatico");
        send();
    }

}
