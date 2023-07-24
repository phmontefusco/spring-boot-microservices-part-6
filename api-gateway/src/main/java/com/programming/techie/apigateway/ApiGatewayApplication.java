package com.programming.techie.apigateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.ObjectInputFilter;

@SpringBootApplication
@EnableEurekaClient
public class ApiGatewayApplication {

    @Autowired
    private Propriedades propriedades;

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);

//        String iss = System.getProperty("spring.security.oauth2.resourceserver.jwt.issuer-uri");
//        System.out.println("1-configuração ISS: " + iss);
//        System.out.println("2-configuração ISS: " + propriedades.getIss());
    }


}
