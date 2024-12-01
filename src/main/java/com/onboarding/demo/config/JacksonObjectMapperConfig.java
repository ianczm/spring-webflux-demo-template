package com.onboarding.demo.config;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonObjectMapperConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder
            .serializationInclusion(Include.NON_NULL)
            .failOnUnknownProperties(true);
    }

}
