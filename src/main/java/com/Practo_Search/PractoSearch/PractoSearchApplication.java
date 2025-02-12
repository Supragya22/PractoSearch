package com.Practo_Search.PractoSearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(basePackages ="com.Practo_Search.PractoSearch.repository")
//@ComponentScan(basePackages = {"com.Practo_Search.PractoSearch.security", "com.Practo_Search.PractoSearch.service","com.pPracto_Search.PractoSearch.controller"})
@EntityScan(basePackages ="com.Practo_Search.PractoSearch.model")
//@EnableAutoConfiguration
public class PractoSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(PractoSearchApplication.class, args);
	}

}
