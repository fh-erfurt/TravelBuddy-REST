package de.travelbuddy.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;

@Configuration
@EnableSwagger2
public class TravelBuddyConfiguration {
    @Autowired
    private ServletContext servletContext;

    /**
     * Init swagger for the apis in de.travelbuddy.controller.v1.api
     */
    @Bean
    public Docket swaggerTravelBuddyApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                //.host("www.mydomain.com")
                .groupName("TravelBuddy")
                .select()
                .apis(RequestHandlerSelectors.basePackage("de.travelbuddy.controller.v1.api"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
                //securitySchemes(Arrays.asList(apiKey()));
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("API - TravelBuddy")
                .description("Manage your journeys with the TravelBuddy API").termsOfServiceUrl("")
                .contact(new Contact("TravelBuddy Inc.", "https://gitl4b.dutches.de/fhe/java1-2/travelbuddy", ""))
                .license("Copyright 2020 TravelBuddy")
                //.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .version("0.0.1")
                .build();
    }

}

