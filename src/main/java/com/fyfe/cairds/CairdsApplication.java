package com.fyfe.cairds;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CairdsApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CairdsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hello world");
	}
}
