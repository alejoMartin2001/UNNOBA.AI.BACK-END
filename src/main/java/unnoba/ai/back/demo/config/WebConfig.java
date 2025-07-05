package unnoba.ai.back.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://unnobaestudiantes.github.io/UNNOBA.AI/")
                .allowedOrigins("http://localhost:5173")
                .allowedOrigins("http://localhost:3000") // Cambia si tu front está en otro puerto
                .allowedMethods("GET")
                .allowedHeaders("*");
    }
}
