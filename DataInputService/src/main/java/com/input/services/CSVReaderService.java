package com.input.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CSVReaderService {

    public List<String[]> createDataListFromCSV() {

        List<String[]>  csvDataList = new ArrayList<>();

        String csvSeperator = ",";
        String csvInputLn = "";

        String srcDir = System.getProperty("user.dir");
        String fileSrcStr = srcDir + "/jobPosts.csv";

        try {

            FileReader csvFile = new FileReader(fileSrcStr);
            
            BufferedReader csvBF = new BufferedReader(csvFile);

            while ((csvInputLn = csvBF.readLine()) != null) {

                String[] csvDataLnAr = csvInputLn.split(csvSeperator);

                System.out.println(
                    "Job Post " +
                        "[" +
                            "Title=" + csvDataLnAr[0] +
                            ", Department=" + csvDataLnAr[1] +
                            ", Renumeration=" + csvDataLnAr[2] +
                        "]");
                csvDataList.add(csvDataLnAr);
            }

            csvBF.close();
        } catch (Exception e) {
            
            e.getMessage();
        }

        return csvDataList;
    }
}
