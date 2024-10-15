package com.input.app;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.input.services.DataInputService;;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan({"com.input"})
@EnableScheduling
public class InputApplication {

	private DataInputService dataInputService;

    public InputApplication(DataInputService dataInputServ) {

        this.dataInputService = dataInputServ;
    }

	public static void main(String[] args) {

		SpringApplication.run(InputApplication.class, args);
	}

	// @Scheduled(cron = "0 0 0 * * *")
	@Scheduled(cron = "0 0 5 * * *")
    public void cronJobScheduler() throws InterruptedException, IOException {

        dataInputService.createCronJobProc();
    }
}
