package com.salon_beaute;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SalonBeauteApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalonBeauteApplication.class, args);
	}

}
