package com.programming.techie.apigateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Propriedades {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String iss;

    public Propriedades(){

    }
//        public String getIss() {
//            return iss;
//        }

    @PostConstruct
    public void init() {
        System.out.println("2-configuração ISS ================== " + iss + "================== ");
    }
}
