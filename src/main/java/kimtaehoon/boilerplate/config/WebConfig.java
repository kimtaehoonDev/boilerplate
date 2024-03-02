package kimtaehoon.boilerplate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// TODO 1. WebConfig에 addCorsMappings 설정을 추가한다.
//  주의! 만약 이후에 Interceptor를 등록한다면, OPTIONS 메서드는 통과할 수 있게 만들어줘야 한다.
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("Authorization", "Content-Type")
            .exposedHeaders("Set-Cookie")
            .allowCredentials(true);
    }
}
