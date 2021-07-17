package com.ed2nd.mywallet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MywalletApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MywalletApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}

}
