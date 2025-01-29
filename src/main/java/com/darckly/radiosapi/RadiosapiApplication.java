package com.darckly.radiosapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RadiosapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RadiosapiApplication.class, args);
	}

}
