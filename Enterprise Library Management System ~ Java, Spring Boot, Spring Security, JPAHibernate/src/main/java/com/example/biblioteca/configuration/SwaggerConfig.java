package com.example.biblioteca.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)//OAS_30
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.biblioteca.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Management competitions API")
                .description("This API exposes endpoints to manage competitions.")
                .build();
    }
//    @Value("${management-competitii.openapi.dev-url}")
//    private String devUrl;
//
//    @Bean
//    public OpenAPI myOpenAPI() {
//        Server devServer = new Server();
//        devServer.setUrl(devUrl);
//        devServer.setDescription("Server URL in Development environment");
//
//        Contact contact = new Contact();
//        contact.setEmail("dev@example.com");
//        contact.setName("Dev");
//        contact.setUrl("https://www.example.com");
//
//        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");
//
//        Info info = new Info()
//                .title("Management competitions API")
//                .version("1.0")
//                .contact(contact)
//                .description("This API exposes endpoints to manage competitions.").termsOfService("https://www.example.com/terms")
//                .license(mitLicense);
//
//        return new OpenAPI().info(info).servers(List.of(devServer));
//    }

}
