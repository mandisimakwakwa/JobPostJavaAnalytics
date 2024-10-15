package com.analytics.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.analytics.events.DataAnalyticsTrendsEvent;
import com.analytics.events.DataInputEvent;
import com.analytics.rest.models.JobPost;
import com.analytics.services.events.DataAnalyticsEventProdService;

@Service
public class DataAnalyticsService {

    private final DataAnalyticsEventProdService dataAnalyticsEventProdService;

    public DataAnalyticsService(
        DataAnalyticsEventProdService dataAnalyticsEventProdServ) {

            this.dataAnalyticsEventProdService = dataAnalyticsEventProdServ;
    }

    public void parseDataInputEvent(DataInputEvent dataInputEvent) {

        List<String[]> dataInputArray = dataInputEvent.getDataInputEventArray();
        List<JobPost> jobPosts = convDataInputArrStrToJobPosts(dataInputArray);

        String mostPopularDailyPost = getMostPopularDailyPost(jobPosts);
        String mostPopularDailyLocation = getMostPopularDailyLocation(jobPosts);
        String mostPopularDailyDepartment = getMostPopularDailyDepartment(jobPosts);

        Double avgDailyRenumeration = getAvgDailyRenumeration(jobPosts);
        
        JobPost highestRenumerationJobPost = getPostWithHighestRenumeration(jobPosts);
        JobPost lowestRenumerationJobPost = getPostWithLowestRenumeration(jobPosts);

        DataAnalyticsTrendsEvent dataAnalyticsTrendsEvent = new DataAnalyticsTrendsEvent();
        dataAnalyticsTrendsEvent.setMostPopularDailyPost(mostPopularDailyPost);
        dataAnalyticsTrendsEvent.setMostPopularDailyLocation(mostPopularDailyLocation);
        dataAnalyticsTrendsEvent.setMostPopularDailyDepartment(mostPopularDailyDepartment);
        dataAnalyticsTrendsEvent.setAvgDailyRenumeration(avgDailyRenumeration);
        dataAnalyticsTrendsEvent.setHighestRenumerationJobPost(highestRenumerationJobPost);
        dataAnalyticsTrendsEvent.setLowestRenumerationJobPost(lowestRenumerationJobPost);

        dataAnalyticsEventProdService.sendDataAnalyticsTrendsEvent(dataAnalyticsTrendsEvent);
    }

    private List<JobPost> convDataInputArrStrToJobPosts(List<String[]> dataInputArray) {

        List<JobPost> jobPosts = new ArrayList<>();
        JobPost jobPost = new JobPost();

        List<Double> renumerationDoubles = new ArrayList<>();

        int dataArrPosCnt = 0;

        for (String[] dataInput : dataInputArray) {

            int dataRelPosCount = 0;
            int dataInputSize = dataInput.length;

            if (dataArrPosCnt == 0) {

                dataArrPosCnt++;
                continue;
            }
            
            jobPost.setTitle(dataInput[dataRelPosCount]);

            dataRelPosCount += 1;
            jobPost.setDepartment(dataInput[dataRelPosCount]);

            dataRelPosCount = dataInputSize - 1;
            String extractedDate = extractDateFromStr(dataInput[dataRelPosCount]);
            jobPost.setDate(Date.valueOf(extractedDate));

            dataRelPosCount = 2;
            while (dataRelPosCount < (dataInputSize - 1)) {

                String newAmt = createAmtStrFromDataInput(dataInput, dataRelPosCount);
                Double dataInputAmount = dataInputAmtConv(newAmt);

                if (newAmt.contains("."))
                    dataRelPosCount += 1;

                if (!newAmt.isBlank() && (dataInputSize != (dataRelPosCount - 1))) {
                    renumerationDoubles.add(dataInputAmount);
                }
                dataRelPosCount += 1;
            }
            jobPost.setRenumeration(renumerationDoubles);

            dataArrPosCnt++;

            System.out.println("conv Job post : " + jobPost.toString());
            jobPosts.add(jobPost);
        }
        return jobPosts;
    }

    private String createAmtStrFromDataInput(String[] dataInput, int pos) {
        
        String amtStr = dataInput[pos];
        String amtStrNext = dataInput[pos + 1] != null ? dataInput[pos + 1] : "00";
        String amtOutput = "";

        List<String> extractedAmtArr = extractAmtFromStr(amtStr);
        List<String> extractedAmtArrNxt = extractAmtFromStr(amtStrNext);

        String extractedAmt = extractedAmtArr.get(0);
        String extractedAmtSec = extractedAmtArrNxt.get(0);

        if (extractedAmtSec.equals(extractedAmt)) {

            extractedAmtSec = "00";
            pos++;
        }

        if (extractedAmtArr.size() > 1)
            extractedAmt = extractedAmtArr.get(0) + extractedAmtArr.get(1);

        if (amtStrNext.contains("R") != true)
            amtOutput = extractedAmt + "." + extractedAmtSec;

        int dataInputLength = dataInput.length;
        if (extractedAmtSec == "00" && ((dataInputLength - 2) == pos))
            amtOutput = extractedAmt + "." + extractedAmtSec;

        return amtOutput;
    }

    private List<String> extractAmtFromStr(String amtStr) {
        
        String rMatchStr = "\\d+";
        Pattern pattern = Pattern.compile(rMatchStr);
        Matcher matcher = pattern.matcher(amtStr);
        List<String> amtStrs = new ArrayList<>();

        while (matcher.find()) {
            amtStrs.add(matcher.group());
        }

        return amtStrs;
    }

    private static Double dataInputAmtConv(String dataInputAmtStr) {

        return Double.valueOf(dataInputAmtStr);
    }

    private static String extractDateFromStr(String clutteredDateStr) {

        String rMatcherStr = "\\d{4}/\\d{2}/\\d{2}";
        Pattern pattern = Pattern.compile(rMatcherStr);
        Matcher matcher = pattern.matcher(clutteredDateStr);

        if (matcher.find()) {

            String extractedDateStr = matcher.group(0);

            return extractedDateStr.replace("/", "-");
        }
        else
            return "no date found";
    }

    // Determine most popular daily post
    private static String getMostPopularDailyPost(List<JobPost> jobPosts) {
        return null;
    }

    // Determine most poplular daily location
    private static String getMostPopularDailyLocation(List<JobPost> jobPosts) {
        return null;
    }

    // Determine most popular daily department
    private static String getMostPopularDailyDepartment(List<JobPost> jobPosts) {
        return null;
    }

    // Determine average daily renumeration
    private static Double getAvgDailyRenumeration(List<JobPost> jobPosts) {
        return null;
    }
    
    // Determine post with highest renumeration
    private static JobPost getPostWithHighestRenumeration(List<JobPost> jobPosts) {
        return null;
    }

    // Determine post with lowest renumeration
    private static JobPost getPostWithLowestRenumeration(List<JobPost> jobPosts) {
        return null;
    }
}
