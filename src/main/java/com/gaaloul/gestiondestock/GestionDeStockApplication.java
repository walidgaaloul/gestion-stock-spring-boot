package com.gaaloul.gestiondestock;

//import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



@EnableJpaAuditing
@SpringBootApplication
//@EnableJpaRepositories
//@ComponentScan({"com.delivery.request"})
//@EntityScan("com.delivery.domain")
//@EnableSwagger2
//@EnableWebMvc
//@OpenAPIDefinition

public class GestionDeStockApplication {

	public static void main(String[] args) {
		System.setProperty("server.port", "8081");
		SpringApplication.run(GestionDeStockApplication.class, args);
	}

}
