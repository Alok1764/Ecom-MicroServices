package org.ecom.e_commerce_microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class EcommerceMicroservices {

    public static void main(String[] args) {

        // Set JVM timezone to UTC (or Asia/Kolkata)
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(EcommerceMicroservices.class, args);
    }

}
