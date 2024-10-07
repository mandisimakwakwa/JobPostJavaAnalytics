package com.input.app;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.input.events.DataInputEvent;
import com.input.services.CSVReaderService;
import com.input.services.ScrapperService;
import com.input.services.events.DataInputEventProdService;;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan({"com.input"})
@EnableScheduling
public class InputApplication {

	@Autowired
	private ScrapperService scrapperService;

	@Autowired
	private CSVReaderService csvReaderService;

	private final DataInputEventProdService dataInputEventProdService;

    public InputApplication(DataInputEventProdService dataInputEventProdServ) {

        this.dataInputEventProdService = dataInputEventProdServ;
    }

	public static void main(String[] args) {

		SpringApplication.run(InputApplication.class, args);
	}

	// Runs cron job every midnight of every day
	@Scheduled(cron = "0 0 0 * * *")
	// @Scheduled(cron = "0 36 4 * * *")
    public void cronJobScheduler() throws InterruptedException, IOException {

        scrapperService.runScrapperApp();

		// 1. Read data from csv
		List<String[]> csvDataList = csvReaderService.createDataListFromCSV();

		// 2. Parse data into kafka topic which is an event
		// 2.1 Produce Data Input Event
		DataInputEvent dataInputEvent = new DataInputEvent();

		dataInputEvent.setDataInputEventArray(csvDataList);
		dataInputEventProdService.sendDataInputEvent(dataInputEvent);
		System.out.println("Data Input Event Produced");

		// 2.2 Delete CSV file
		String fileDir = System.getProperty("user.dir");
		String fileName = "/jobPosts.csv";

		File csvFile = new File(fileDir + fileName);
		boolean delFileResp = csvFile.delete();

		if (delFileResp)
			System.out.println("jobPosts.csv deleted");
    }
}
