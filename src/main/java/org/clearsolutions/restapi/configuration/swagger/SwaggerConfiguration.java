package org.clearsolutions.restapi.configuration.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    public static final String API_TITLE = "ClearSolutions Users API";
    public static final String API_DESCRIPTION = "This API completes the test assignment from ClearSolutions, and implement CRUD operation for User entity.";
    public static final String API_VERSION = "v0.0.1";

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info().title(API_TITLE)
                        .description(API_DESCRIPTION)
                        .version(API_VERSION));
    }
}
