package com.byteworks.configuration;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Kelvin
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.byteworks.controller"))
                .paths(PathSelectors.regex("/api/v1.*"))
                .build()
                .enableUrlTemplating(Boolean.valueOf("${swagger.enableUrlTemplating}"))
                .apiInfo(apiInfo()).securitySchemes(Lists.newArrayList(apiKey()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .termsOfServiceUrl("Terms of Service")
                .title("Byteworks Food Vendor API")
                .description("Byteworks Food Vendor API using Spring")
                .license("Apache License Version 2.0")
                .version("v1")
                .contact(new Contact("Isievwore Kelvin", "http://kelvinator07.github.io", "Isievwore.kelvin@gmail.com"))
                .build();
    }

    @Bean
    UiConfiguration uiConfiguration() {
        return UiConfigurationBuilder.builder()
                .deepLinking(Boolean.valueOf("${swagger.deepLinking}"))
                .displayOperationId(Boolean.valueOf("${swagger.displayOperationId}"))
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(Boolean.valueOf("${swagger.displayRequestDuration}"))
                .docExpansion(DocExpansion.NONE)
                .filter(Boolean.valueOf("${swagger.filter}"))
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(Boolean.valueOf("${swagger.showExtensions}"))
                .tagsSorter(TagsSorter.ALPHA)
                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
                .validatorUrl(null).build();
    }

    @Bean
    public SecurityConfiguration securityConfiguration() {
        return new SecurityConfiguration(null, null, null, null, "", ApiKeyVehicle.HEADER, "Authorization", "");
    }

    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }

}
