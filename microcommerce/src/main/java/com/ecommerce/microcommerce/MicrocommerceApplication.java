package com.ecommerce.microcommerce;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//Anotation pour Swagger
@EnableSwagger2
public class MicrocommerceApplication {

	public static void main(String[] args) {

		SpringApplication.run(MicrocommerceApplication.class, args);
	}

}
