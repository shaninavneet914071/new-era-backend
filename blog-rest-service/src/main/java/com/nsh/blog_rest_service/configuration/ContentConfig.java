package com.nsh.blog_rest_service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.modelmapper.ModelMapper;
@Configuration
public class ContentConfig implements WebMvcConfigurer {
    /**
     * @param configurer
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
     configurer.favorParameter(true).parameterName("mediaType").defaultContentType(MediaType.APPLICATION_JSON).mediaType("json",MediaType.APPLICATION_JSON).mediaType("xml",MediaType.APPLICATION_XML);
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
