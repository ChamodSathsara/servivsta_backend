package com.gestetner.servvista;

import org.springframework.boot.SpringApplication;

public class TestServvistaApplication {

	public static void main(String[] args) {
		SpringApplication.from(ServvistaApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
