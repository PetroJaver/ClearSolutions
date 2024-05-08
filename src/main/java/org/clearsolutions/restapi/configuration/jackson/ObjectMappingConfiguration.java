package org.clearsolutions.restapi.configuration.jackson;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Slice;

@Configuration
public class ObjectMappingConfiguration {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> builder.mixIn(Slice.class, SliceMapping.class);
    }
}
