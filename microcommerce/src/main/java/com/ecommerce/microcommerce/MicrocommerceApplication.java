package com.ecommerce.microcommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//Anotation pour Sswagger
@EnableSwagger2
public class MicrocommerceApplication {

	public static void main(String[] args) {

		SpringApplication.run(MicrocommerceApplication.class, args);
	}

}
