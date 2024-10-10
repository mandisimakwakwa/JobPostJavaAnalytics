package com.input.services;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.input.events.DataInputEvent;
import com.input.services.events.DataInputEventProdService;

@Service
public class DataInputService {

    @Autowired
	private ScrapperService scrapperService;

    @Autowired
	private CSVReaderService csvReaderService;

	private final DataInputEventProdService dataInputEventProdService;

    public DataInputService(DataInputEventProdService dataInputEventProdServ) {

        this.dataInputEventProdService = dataInputEventProdServ;
    }

    public void createCronJobProc() throws IOException {

        scrapperService.runScrapperApp();

		List<String[]> csvDataList = csvReaderService.createDataListFromCSV();

		DataInputEvent dataInputEvent = new DataInputEvent();

		dataInputEvent.setDataInputEventArray(csvDataList);
		dataInputEventProdService.sendDataInputEvent(dataInputEvent);
		System.out.println("Data Input Event Produced");

		String fileDir = System.getProperty("user.dir");
		String fileName = "/jobPosts.csv";

		File csvFile = new File(fileDir + fileName);
		boolean delFileResp = csvFile.delete();

		if (delFileResp)
			System.out.println("jobPosts.csv deleted");
    }
}
