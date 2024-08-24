package com.docservice.backend.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration



public class WebService {

    public void addCorsMappings(CorsRegistry registry) {
       registry.addMapping("/**").allowedOrigins("http://localhost:4200")
              .allowedMethods("GET", "POST", "DELETE", "PUT");

    }
}
