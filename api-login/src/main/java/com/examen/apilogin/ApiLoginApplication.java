package com.examen.apilogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiLoginApplication.class, args);
        System.out.println("Servidor corriendo en http://localhost:8080");
    }
}
