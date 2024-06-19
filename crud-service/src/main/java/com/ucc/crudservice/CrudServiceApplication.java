package com.ucc.crudservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@SpringBootApplication
public class CrudServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudServiceApplication.class, args);
	}

}
