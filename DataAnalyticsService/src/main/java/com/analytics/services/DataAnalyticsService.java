package com.analytics.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analytics.events.DataAnalyticsTrendsEvent;
import com.analytics.events.DataInputEvent;
import com.analytics.rest.models.JobPost;
import com.analytics.services.events.DataAnalyticsEventProdService;
import com.analytics.util.DataInputsConversionUtil;
import com.analytics.util.JobTitleFrequencyUtil;

@Service
public class DataAnalyticsService {

    @Autowired
    private DataInputsConversionUtil dataInputsConversionUtil;
    @Autowired
    private JobTitleFrequencyUtil frequencyUtil;

    private final DataAnalyticsEventProdService dataAnalyticsEventProdService;
    
    public DataAnalyticsService(
        DataAnalyticsEventProdService dataAnalyticsEventProdServ) {
            this.dataAnalyticsEventProdService = dataAnalyticsEventProdServ;
    }

    public void parseDataInputEvent(DataInputEvent dataInputEvent) throws Exception {

        List<String[]> dataInputArray = dataInputEvent.getDataInputEventArray();
        List<JobPost> jobPosts = dataInputsConversionUtil
            .convDataInputsToJobPosts(dataInputArray);

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
    
    // Determine most popular daily post
    private String getMostPopularDailyPost(List<JobPost> jobPosts) {

        List<String> mostFreqWords = frequencyUtil.findMostFrequentWords(jobPosts);
        System.out.println("most frequent words : " + mostFreqWords.get(0));
        return mostFreqWords.get(0);
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
