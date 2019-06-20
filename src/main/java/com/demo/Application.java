package com.demo;

import com.demo.config.ApolloEnvInitializer;
import com.demo.config.LoggingReInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Application.class);
		application.addInitializers(new ApolloEnvInitializer());
		application.addInitializers(new LoggingReInitializer());
		application.run(args);
	}
}
