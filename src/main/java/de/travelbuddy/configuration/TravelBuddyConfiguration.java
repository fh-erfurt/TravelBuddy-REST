package de.travelbuddy.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import springfox.documentation.oas.annotations.EnableOpenApi;

import java.text.SimpleDateFormat;

@Configuration
@EnableOpenApi
public class TravelBuddyConfiguration {

    @Bean
    public InternalResourceViewResolver defaultViewResolver() {
        return new InternalResourceViewResolver();
    }
}

