package com.analytics.events;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.analytics.rest.models.JobPost;

@Component
public class DataAnalyticsTrendsEvent extends Event {

    String mostPopularDailyPost = "";
    // String mostPopularDailyLocation = "";
    String mostPopularDailyDepartment = "";

    BigDecimal avgDailyRenumeration = BigDecimal.valueOf(0.00);

    JobPost highestRenumerationJobPost = new JobPost();
    JobPost lowestRenumerationJobPost = new JobPost();

    public String getMostPopularDailyPost() {
        return mostPopularDailyPost;
    }

    public void setMostPopularDailyPost(String mostPopularDailyPost) {
        this.mostPopularDailyPost = mostPopularDailyPost;
    }

    // public String getMostPopularDailyLocation() {
    //     return mostPopularDailyLocation;
    // }

    // public void setMostPopularDailyLocation(String mostPopularDailyLocation) {
    //     this.mostPopularDailyLocation = mostPopularDailyLocation;
    // }

    public String getMostPopularDailyDepartment() {
        return mostPopularDailyDepartment;
    }

    public void setMostPopularDailyDepartment(String mostPopularDailyDepartment) {
        this.mostPopularDailyDepartment = mostPopularDailyDepartment;
    }

    public BigDecimal getAvgDailyRenumeration() {
        return avgDailyRenumeration;
    }

    public void setAvgDailyRenumeration(BigDecimal avgDailyRenumeration) {
        this.avgDailyRenumeration = avgDailyRenumeration;
    }

    public JobPost getHighestRenumerationJobPost() {
        return highestRenumerationJobPost;
    }

    public void setHighestRenumerationJobPost(JobPost highestRenumerationJobPost) {
        this.highestRenumerationJobPost = highestRenumerationJobPost;
    }

    public JobPost getLowestRenumerationJobPost() {
        return lowestRenumerationJobPost;
    }

    public void setLowestRenumerationJobPost(JobPost lowestRenumerationJobPost) {
        this.lowestRenumerationJobPost = lowestRenumerationJobPost;
    }
}