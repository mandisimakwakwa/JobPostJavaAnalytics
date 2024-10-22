package com.analytics.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.analytics.rest.models.JobPost;

@Component
public class JobPostFrequencyUtil {

    // Strategy Comparator Pattern
    public String findMostFrequentJobTitle(List<JobPost> jobPosts) {

        List<String> jobTitles = extractJobTitleStrings(jobPosts);

        return findStringWithMostRepeatedWords(jobTitles);
    }

    // Hashmap Reducer Pattern
    public String findMostFrequentJobDepartment(List<JobPost> jobPosts) {

        List<String> jobDepartments = extractJobDepartmentStrings(jobPosts);

        return findMostRepeatedWord(jobDepartments);
    }

    // Calc avg renum per annum val
    public Double calculateAvgRenumerationPerAnnum(List<JobPost> jobPosts) {

        Double avgRenum = 0.00;
        Double renumSum = 0.00;

        List<Double> extractedRenums = extractJobRenumeratios(jobPosts);
        
        Set<Double> renumSet = convertDoubleListToSet(extractedRenums);

        int sizeOfRenumSet = renumSet.size();

        for (Double renum : renumSet) {

            int renumDigitsBefDecimalPoint = (int)Math.log10(renum);
            
            if (renumDigitsBefDecimalPoint < 4)
                renum = convertHourlyToAnnum(renum);

            if (renumDigitsBefDecimalPoint > 4 && renumDigitsBefDecimalPoint < 6)
                renum = convertMonthlyToAnnum(renum);

            renumSum += renum;
        }

        avgRenum = renumSum/sizeOfRenumSet;
        return avgRenum;
    }

    public JobPost findJobPostWithHighestRenum(List<JobPost> jobPosts) {
        
        JobPost jobPost = new JobPost();

        Double maxJobPostRenum = 0.0;

        for (JobPost jobPostIn : jobPosts) {
            
            Set<Double> jobPostRenumSet = new HashSet<>(jobPost.getRenumeration());

            for (Double jobPostRenum : jobPostRenumSet) {

                if (jobPostRenum < 4)
                    jobPostRenum = convertHourlyToAnnum(jobPostRenum);

                if (jobPostRenum > 4 && jobPostRenum < 6)
                    jobPostRenum = convertMonthlyToAnnum(jobPostRenum);
                
                if (jobPostRenum > maxJobPostRenum) {

                    maxJobPostRenum = jobPostRenum;
                    jobPost = jobPostIn;
                }
            }
        }
        return jobPost;
    }

	public JobPost findJobPostWithLowestRenum(List<JobPost> jobPosts) {
		
        JobPost jobPost = new JobPost();

        Double minJobPostRenum = 0.0;

        for (JobPost jobPostIn : jobPosts) {
            
            Set<Double> jobPostRenumSet = new HashSet<>(jobPost.getRenumeration());

            for (Double jobPostRenum : jobPostRenumSet) {

                if (jobPostRenum < 4)
                    jobPostRenum = convertHourlyToAnnum(jobPostRenum);

                if (jobPostRenum > 4 && jobPostRenum < 6)
                    jobPostRenum = convertMonthlyToAnnum(jobPostRenum);
                
                if (jobPostRenum < minJobPostRenum) {

                    minJobPostRenum = jobPostRenum;
                    jobPost = jobPostIn;
                }
            }
        }
        return jobPost;
	}

    private String findMostRepeatedWord(List<String> inputStrings) {
        
        String mostFrequentWord = null;

        HashMap<String, Integer> frequentWordsMap = new HashMap<>();

        // count occurance of each string
        for (String inputString : inputStrings)
            frequentWordsMap.put(inputString, frequentWordsMap.getOrDefault(inputString, 0) + 1);

        // Find string with highest frequency
        int maxCnt = 0;

        for (Map.Entry<String, Integer> entry : frequentWordsMap.entrySet()) {
            
            if (entry.getValue() > maxCnt) {

                mostFrequentWord = entry.getKey();
                maxCnt = entry.getValue();
            }
        }

        return mostFrequentWord;
    }

    private String findStringWithMostRepeatedWords(List<String> jobTitles) {

        List<Set<String>> jobTitlesWordSet = new ArrayList<>();
        
        String mostRepeatedWordsStr = "";

        int jobTitlesSize = jobTitles.size();
        int maxRepeats = 0;

        for (String jobTitle : jobTitles) {
            
            jobTitlesWordSet.add(getJobTitleWords(jobTitle));
        }

        // Iterate over each string
        for (int i = 0; i < jobTitlesSize; i++) {

            Set<String> jobTitleStrWords = jobTitlesWordSet.get(i);
            int repeatedJobTitleWordsCount = 0;

            // compare current string with subsequent strings
            for (int j = 0; j < jobTitlesSize; j++) {

                if (i != j) {

                    Set<String> jobTitleAnoStrWords = jobTitlesWordSet.get(j);

                    // count how many words in current string a present in other string
                    for (String jobTitleStrWord : jobTitleStrWords) {
                        
                        if (jobTitleAnoStrWords.contains(jobTitleStrWord))
                            repeatedJobTitleWordsCount++;
                    }
                }
            }

            //  update if curr string has highest num of repeated words
            if (repeatedJobTitleWordsCount > maxRepeats) {

                maxRepeats = repeatedJobTitleWordsCount;
                mostRepeatedWordsStr =  jobTitles.get(i);
            }
        }

        return mostRepeatedWordsStr;
    }

    private static Set<String> getJobTitleWords(String jobTitle) {

        String nonWordCharRegex = "\\W+";
        List<String> jobTitleWordsList = Arrays.asList(jobTitle.split(nonWordCharRegex));

        HashSet<String> jobTitleWords = new HashSet<>(jobTitleWordsList);
        return jobTitleWords;
    }

    private static List<String> extractJobTitleStrings(List<JobPost> jobPosts) {
        
        List<String> jobTitles = new ArrayList<>();

        for (JobPost jobPost : jobPosts) {
            
            jobTitles.add(jobPost.getTitle().toLowerCase());
        }

        return jobTitles;
    }

    private List<String> extractJobDepartmentStrings(List<JobPost> jobPosts) {
        
        List<String> jobDepartments = new ArrayList<>();

        for (JobPost jobPost : jobPosts) {
            
            jobDepartments.add(jobPost.getDepartment().toLowerCase());
        }
        return jobDepartments;
    }

    private List<Double> extractJobRenumeratios(List<JobPost> jobPosts) {
        
        List<Double> renums = new ArrayList<>();

        for (JobPost jobPost : jobPosts) {

            List<Double> jobPostRenums = jobPost.getRenumeration();

            for (Double jobPostRenum : jobPostRenums) {

                renums.add(jobPostRenum);
            }
        }
        return renums;
    }

    private Set<Double> convertDoubleListToSet(List<Double> extractedRenums) {
        
        return new HashSet<Double>(extractedRenums);
    }

    private Double convertHourlyToAnnum(Double renum) {
        
        Double day = (double) 8;
        Double week = (double) 5;
        Double month = (double) 4;
        Double year = (double) 12;

        Double renumAnnum = 0.0;

        return renumAnnum * day * week * month * year;
    }

    private Double convertMonthlyToAnnum(Double renum) {

        Double year = (double) 12;
        Double renumAnnum = 0.0;

        return renumAnnum * year;
    }
}
