package com.analytics.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.analytics.events.DataAnalyticsTrendsEvent;
import com.analytics.events.DataInputEvent;
import com.analytics.rest.models.JobPost;
import com.analytics.services.events.DataAnalyticsEventProdService;
import com.analytics.util.DataInputsConversionUtil;
import com.analytics.util.JobPostFrequencyUtil;

@Service
public class DataAnalyticsService {

    private final DataAnalyticsEventProdService dataAnalyticsEventProdService;

    private DataInputsConversionUtil dataInputsConversionUtil;

    private JobPostFrequencyUtil frequencyUtil;
    
    public DataAnalyticsService(
        DataAnalyticsEventProdService dataAnalyticsEventProdServ,
        DataInputsConversionUtil dataInputsConvUtil,
        JobPostFrequencyUtil jobPostFreqUtil) {
            
            this.dataAnalyticsEventProdService = dataAnalyticsEventProdServ;
            this.dataInputsConversionUtil = dataInputsConvUtil;
            this.frequencyUtil = jobPostFreqUtil;
    }

    public void parseDataInputEvent(DataInputEvent dataInputEvent) throws Exception {

        List<String[]> dataInputArray = dataInputEvent.getDataInputEventArray();
        List<JobPost> jobPosts = dataInputsConversionUtil
            .convDataInputsToJobPosts(dataInputArray);

        String mostPopularDailyPost = getMostPopularDailyPost(jobPosts);
        String mostPopularDailyDepartment = getMostPopularDailyDepartment(jobPosts);
        // String mostPopularDailyLocation = getMostPopularDailyLocation(jobPosts);

        BigDecimal avgDailyRenumeration = getAvgDailyRenumeration(jobPosts);
        
        JobPost highestRenumerationJobPost = getPostWithHighestRenumeration(jobPosts);
        JobPost lowestRenumerationJobPost = getPostWithLowestRenumeration(jobPosts);

        DataAnalyticsTrendsEvent dataAnalyticsTrendsEvent = new DataAnalyticsTrendsEvent();
        dataAnalyticsTrendsEvent.setMostPopularDailyPost(mostPopularDailyPost);
        // dataAnalyticsTrendsEvent.setMostPopularDailyLocation(mostPopularDailyLocation);
        dataAnalyticsTrendsEvent.setMostPopularDailyDepartment(mostPopularDailyDepartment);
        dataAnalyticsTrendsEvent.setAvgDailyRenumeration(avgDailyRenumeration);
        dataAnalyticsTrendsEvent.setHighestRenumerationJobPost(highestRenumerationJobPost);
        dataAnalyticsTrendsEvent.setLowestRenumerationJobPost(lowestRenumerationJobPost);

        dataAnalyticsEventProdService.sendDataAnalyticsTrendsEvent(dataAnalyticsTrendsEvent);
    }
    
    // Determine most popular daily post
    private String getMostPopularDailyPost(List<JobPost> jobPosts) {

        String mostFreqJobTitle = frequencyUtil.findMostFrequentJobTitle(jobPosts);
        return mostFreqJobTitle;
    }

    // Determine most poplular daily location
    // private static String getMostPopularDailyLocation(List<JobPost> jobPosts) {
    //     return null;
    // }

    // Determine most popular daily department
    private String getMostPopularDailyDepartment(List<JobPost> jobPosts) {

        String mostFreqJobDepartment = frequencyUtil.findMostFrequentJobDepartment(jobPosts);
        return mostFreqJobDepartment;
    }

    // Determine average daily renumeration
    private BigDecimal getAvgDailyRenumeration(List<JobPost> jobPosts) {

        BigDecimal avgRenum = frequencyUtil.calculateAvgRenumerationPerAnnum(jobPosts);
        return avgRenum;
    }
    
    // Determine post with highest renumeration
    private JobPost getPostWithHighestRenumeration(List<JobPost> jobPosts) {

        JobPost jobPostWithHighestRenum = frequencyUtil.findJobPostWithHighestRenum(jobPosts);
        return jobPostWithHighestRenum;
    }

    // Determine post with lowest renumeration
    private JobPost getPostWithLowestRenumeration(List<JobPost> jobPosts) {

        JobPost jobPostWithLowestRenum = frequencyUtil.findJobPostWithLowestRenum(jobPosts);
        return jobPostWithLowestRenum;
    }
}
