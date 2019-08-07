package py.com.fuentepy.appfinanzasBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import py.com.fuentepy.appfinanzasBackend.config.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class AppFinanzasBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppFinanzasBackendApplication.class, args);
    }

}
