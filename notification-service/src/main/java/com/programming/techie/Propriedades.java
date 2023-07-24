package com.programming.techie;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Propriedades {

    @Value("${application.topic}")
    private String applicationTopic;

    public Propriedades(){

    }
//        public String getIss() {
//            return iss;
//        }

    @PostConstruct
    public void init() {
        System.out.println("2-configuração Topic ================== " + applicationTopic + "================== ");
    }
}
