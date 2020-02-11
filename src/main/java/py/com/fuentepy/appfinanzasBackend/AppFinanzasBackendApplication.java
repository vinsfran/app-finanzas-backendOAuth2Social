package py.com.fuentepy.appfinanzasBackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import py.com.fuentepy.appfinanzasBackend.config.AppProperties;
import py.com.fuentepy.appfinanzasBackend.service.Impl.UsuarioServiceImpl;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
@EnableScheduling
public class AppFinanzasBackendApplication implements CommandLineRunner {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    public static void main(String[] args) {
        SpringApplication.run(AppFinanzasBackendApplication.class, args);
    }

    @Override
    public void run(String... args) {
//        String imageProfileName = "imagen1.jpg";
//        usuarioService.uploadImage(Base64.decodeBase64(imageProfileData), imageProfileName, "image/jpg", 1L);
    }

}
