package de.travelbuddy.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.function.Predicate;

import static springfox.documentation.builders.PathSelectors.regex;

@Component
public class SpringConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.
                addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/swagger-ui/")
                .setViewName("forward:" + "/swagger-ui/index.html");
    }

    @Bean
    public Docket travelBuddyApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("TravelBuddy API")
                .apiInfo(apiInfo())
                .select()
                .paths(travelbuddyPaths())
                .build();
    }

    private Predicate<String> travelbuddyPaths() {
        return regex(".*"); // No need to filer, since we do not have a lot of controllers
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API documentation for TravelBuddy")
                .description("This documentation provides you with information about all the available methods in this API.")
                .contact(new Contact("TravelBuddy", "travelbuddy.de", "info@travelbuddy.de"))
                .license("GNU General Public License v3.0")
                .licenseUrl("https://gitl4b.dutches.de/fhe/java1-2/travelbuddy/-/blob/master/LICENSE")
                .version("1.0")
                .build();
    }
}