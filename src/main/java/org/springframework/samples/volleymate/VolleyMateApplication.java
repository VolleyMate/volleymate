package org.springframework.samples.volleymate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@EnableScheduling
public class VolleyMateApplication {

	public static void main(String[] args) {
		SpringApplication.run(VolleyMateApplication.class, args);
	}

}
