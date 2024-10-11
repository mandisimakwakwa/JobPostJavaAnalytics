package com.analytics.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan({"com.analytics"})
@EnableScheduling
public class DataAnalyticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataAnalyticsApplication.class, args);
	}

}
