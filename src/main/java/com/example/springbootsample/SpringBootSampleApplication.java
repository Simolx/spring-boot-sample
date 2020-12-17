package com.example.springbootsample;

import com.example.springbootsample.consumingrest.Quote;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication
@RestController
@EnableScheduling
public class SpringBootSampleApplication {

	private static final Logger log = LoggerFactory.getLogger(SpringBootSampleApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSampleApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			log.info("Let's inspect the beans provided by System:");
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for(var beanName : beanNames) {
				log.info(beanName);
			}
		};
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) {
		return args -> {
			Quote quote = restTemplate.getForObject("https://gturnquist-quoters.cfapps.io/api/random", Quote.class);
			log.info(quote.toString());
		};
	}

	@Operation(summary = "hello get handler")
	@GetMapping("/hello")
	public String hello(@Parameter(description = "name value") @RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

}
