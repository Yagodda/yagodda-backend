package com.yagodda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
public class YagoddaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(YagoddaBackendApplication.class, args);

    }


    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yagodda.controllers"))
                .build();
    }
}
