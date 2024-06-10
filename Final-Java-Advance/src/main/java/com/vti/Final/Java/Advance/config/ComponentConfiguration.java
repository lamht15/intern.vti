package com.vti.Final.Java.Advance.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class ComponentConfiguration {
    @Bean
    public ModelMapper initModelMapper(){
        return new ModelMapper();
    }
}
