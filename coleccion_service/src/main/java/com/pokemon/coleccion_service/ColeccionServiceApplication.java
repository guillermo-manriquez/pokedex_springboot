package com.pokemon.coleccion_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ColeccionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ColeccionServiceApplication.class, args);
	}

}
