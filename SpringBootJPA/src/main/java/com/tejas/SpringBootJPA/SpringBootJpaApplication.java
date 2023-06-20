package com.tejas.SpringBootJPA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan(basePackages = {"com.tejas.*"})
@EntityScan(basePackages = {"com.tejas.*"})
@EnableJpaRepositories("com.tejas")
public class SpringBootJpaApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJpaApplication.class, args);
	}
	
	@Bean
	public RestTemplate getRestTemplate() {
		
		var factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(60000);
		factory.setReadTimeout(60000);
		
		return new RestTemplate(factory);
	}

}