package br.com.ctcea.gestaoinv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class GestaoinvApplication {
	public static void main(String[] args) {
		SpringApplication.run(GestaoinvApplication.class, args);
	}
}
