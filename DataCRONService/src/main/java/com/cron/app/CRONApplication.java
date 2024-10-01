package com.cron.app;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.cron.services.ScrapperService;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan({"com.cron"})
@EnableScheduling
public class CRONApplication {

	@Autowired
	private ScrapperService scrapperService;

	public static void main(String[] args) {

		SpringApplication.run(CRONApplication.class, args);
	}

	// Runs cron job every midnight of every day
	// @Scheduled(cron = "0 0 0 * * *")
	@Scheduled(cron = "0 49 17 * * *")
    public void cronJobScheduler() throws InterruptedException, IOException {

        scrapperService.runScrapperApp();
    }
}
