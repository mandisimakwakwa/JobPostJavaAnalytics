package com.analytics.services;

import java.util.ArrayList;
import java.util.List;

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
        return jobPosts;
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
