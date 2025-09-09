package com.writeit.write_it;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WriteItApplication {

	public static void main(String[] args) {
		SpringApplication.run(WriteItApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			System.out.println("WriteIt Application has started successfully!");
		};
	}
}
