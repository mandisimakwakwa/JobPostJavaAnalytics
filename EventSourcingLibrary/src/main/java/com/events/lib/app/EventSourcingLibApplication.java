package com.events.lib.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({"com.events"})
public class EventSourcingLibApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventSourcingLibApplication.class, args);
	}

}
