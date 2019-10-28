package py.com.fuentepy.appfinanzasBackend;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import py.com.fuentepy.appfinanzasBackend.config.AppProperties;
import py.com.fuentepy.appfinanzasBackend.service.Impl.UsuarioServiceImpl;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class AppFinanzasBackendApplication implements CommandLineRunner {

    @Value("${app.image64}")
    private String imageProfileData;

    @Autowired
    private UsuarioServiceImpl usuarioService;

    public static void main(String[] args) {
        SpringApplication.run(AppFinanzasBackendApplication.class, args);
    }

    @Override
    public void run(String... args) {
        String imageProfileName = "imagen1.jpg";
        usuarioService.uploadImage(Base64.decodeBase64(imageProfileData), imageProfileName, "image/jpg", 1L);
    }

}
