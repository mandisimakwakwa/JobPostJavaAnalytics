package com.analytics.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analytics.events.DataAnalyticsTrendsEvent;
import com.analytics.events.DataInputEvent;
import com.analytics.rest.models.JobPost;
import com.analytics.services.events.DataAnalyticsEventProdService;
import com.analytics.util.DataInputsConversionUtil;
import com.analytics.util.JobPostFrequencyUtil;

@Service
public class DataAnalyticsService {

    @Autowired
    private DataInputsConversionUtil dataInputsConversionUtil;
    @Autowired
    private JobPostFrequencyUtil frequencyUtil;

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
        // String mostPopularDailyLocation = getMostPopularDailyLocation(jobPosts);
        String mostPopularDailyDepartment = getMostPopularDailyDepartment(jobPosts);

        Double avgDailyRenumeration = getAvgDailyRenumeration(jobPosts);
        
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
        
        System.out.println("most frequent job title : " + mostFreqJobTitle);
        return mostFreqJobTitle;
    }

    // Determine most poplular daily location
    // private static String getMostPopularDailyLocation(List<JobPost> jobPosts) {
    //     return null;
    // }

    // Determine most popular daily department
    private String getMostPopularDailyDepartment(List<JobPost> jobPosts) {

        String mostFreqJobDepartment = frequencyUtil.findMostFrequentJobDepartment(jobPosts);
        
        System.out.println("most frequent job department : " + mostFreqJobDepartment);
        return mostFreqJobDepartment;
    }

    // Determine average daily renumeration
    private Double getAvgDailyRenumeration(List<JobPost> jobPosts) {

        Double avgRenum = frequencyUtil.calculateAvgRenumerationPerAnnum(jobPosts);

        System.out.println("avg job renumeration : " + avgRenum);
        return avgRenum;
    }
    
    // Determine post with highest renumeration
    private JobPost getPostWithHighestRenumeration(List<JobPost> jobPosts) {

        JobPost jobPostWithHighestRenum = frequencyUtil.findJobPostWithHighestRenum(jobPosts);

        System.out.println("job post with highest annual renum val : " + jobPostWithHighestRenum.getTitle());
        return jobPostWithHighestRenum;
    }

    // Determine post with lowest renumeration
    private JobPost getPostWithLowestRenumeration(List<JobPost> jobPosts) {

        JobPost jobPostWithLowestRenum = frequencyUtil.findJobPostWithLowestRenum(jobPosts);

        System.out.println("job post with lowest annual renum val : " + jobPostWithLowestRenum.getTitle());
        return jobPostWithLowestRenum;
    }
}
