package vom.spring.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "VOM API 명세서",
                description = "VOM API 명세서",
                version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/**"};
        return GroupedOpenApi.builder()
                .group("VOM API v1")
                .pathsToMatch(paths)
                .build();
    }
}
