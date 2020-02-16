package com.ma.currencyconverter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/excurrency/**")
                .allowedOrigins("https://currency-converter-back.herokuapp.com",
                        "https://currency-converter-front.herokuapp.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(false).maxAge(3600);
    }
}