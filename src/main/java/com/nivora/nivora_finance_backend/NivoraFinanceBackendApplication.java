package com.nivora.nivora_finance_backend;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NivoraFinanceBackendApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.configure()
						.load();

		SpringApplication.run(NivoraFinanceBackendApplication.class, args);
	}

}
