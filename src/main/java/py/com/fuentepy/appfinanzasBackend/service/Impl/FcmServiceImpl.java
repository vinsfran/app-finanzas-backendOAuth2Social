package py.com.fuentepy.appfinanzasBackend.service.Impl;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
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

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost postRequest = new HttpPost(urlFcmSend);

        String body = null;
        List<Mensaje> mensajesBorrar = new ArrayList<>();
        for (Dispositivo dispositivo : dispositivoRepository.findAll()) {
            for (Mensaje mensaje : mensajeRepository.findByUsuarioId(dispositivo.getUsuarioId())) {
                if (DateUtil.compararFechas(new Date(), mensaje.getFechaEnvio())) {
                    LOG.info(mensaje.getBody());
                    NotificationRequestModel notificationRequestModel = new NotificationRequestModel();
                    notificationRequestModel.setTo(dispositivo.getToken());
                    notificationRequestModel.setNotification(new NotificationDataModel());
                    notificationRequestModel.getNotification().setTitle(mensaje.getTitulo());
                    notificationRequestModel.getNotification().setBody(mensaje.getBody());
                    notificationRequestModel.getNotification().setIcon("ic_stat_icon_controlate");
                    notificationRequestModel.getNotification().setColor("#25cf38");
                    DataModel dataModel = new DataModel();
                    dataModel.setClickAction("FLUTTER_NOTIFICATION_CLICK");
                    notificationRequestModel.setData(dataModel);

                    Gson gson = new Gson();
                    Type type = new TypeToken<NotificationRequestModel>() {
                    }.getType();
                    String json = gson.toJson(notificationRequestModel, type);
                    LOG.info(json);

                    StringEntity input = new StringEntity(json);
                    input.setContentType("application/json");

                    postRequest.addHeader("Authorization", "Bearer " + key);
                    postRequest.setEntity(input);

                    HttpResponse response = httpClient.execute(postRequest);

                    if (response.getStatusLine().getStatusCode() != 200) {
                        throw new RuntimeException("Failed : HTTP error code : "
                                + response.getStatusLine().getStatusCode());
                    } else if (response.getStatusLine().getStatusCode() == 200) {
                        body = "response:" + EntityUtils.toString(response.getEntity());
                        LOG.info(body);
                        mensajesBorrar.add(mensaje);
                    }
                }
            }
        }
        if (!mensajesBorrar.isEmpty()) {
            for (Mensaje mensajeBorrar : mensajesBorrar) {
                mensajeRepository.delete(mensajeBorrar);
            }
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
