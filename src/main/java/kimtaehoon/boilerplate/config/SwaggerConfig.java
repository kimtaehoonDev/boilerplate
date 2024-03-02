package kimtaehoon.boilerplate.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

// TODO 2. Swagger 설정을 추가한다. 아래 설정은 다음과 같다.
//  - local, dev 프로필에서만 활성화
//  - JWT 로그인 기능 추가
@OpenAPIDefinition
@Configuration
@Profile({"local", "dev"})
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Info info = new Info()
            .title("타이틀")
            .version("v1.0.0");

        String securityRequirementName = "accessToken을 붙여넣으세요. (ex. ey....)";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(securityRequirementName);

        Components components = new Components()
            .addSecuritySchemes(securityRequirementName, new SecurityScheme()
                .name(securityRequirementName)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT"));

        return new OpenAPI()
            .info(info)
            .addSecurityItem(securityRequirement)
            .components(components);
    }
}
