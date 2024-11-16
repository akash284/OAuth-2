package com.akash.Oauth2Project_Backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    // customizing the cors settings for sb application
    // configuring the aspect of spring mvc such as cors,view resolver
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {


            //  customize CORS handling for the entire application.
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // all url paths in app are allowed
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedOrigins("*");
            }
        };
    }
}
