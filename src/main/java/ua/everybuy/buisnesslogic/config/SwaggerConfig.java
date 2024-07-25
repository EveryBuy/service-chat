package ua.everybuy.buisnesslogic.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {
    @Bean
    public GroupedOpenApi customApi() {
        return GroupedOpenApi.builder()
                .group("ua.everybuy")
                .pathsToMatch("/**")
                .build();
    }
}