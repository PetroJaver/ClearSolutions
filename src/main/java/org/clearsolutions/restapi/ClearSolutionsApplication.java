package org.clearsolutions.restapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Slice;

import java.util.List;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ClearSolutionsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClearSolutionsApplication.class, args);
    }
}
