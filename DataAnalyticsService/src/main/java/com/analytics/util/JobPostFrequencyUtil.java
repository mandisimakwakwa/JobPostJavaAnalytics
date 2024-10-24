package com.analytics.util;

import java.math.BigDecimal;
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

    private DataInputCleanerUtil dataInputCleanerUtil;

    public JobPostFrequencyUtil(DataInputCleanerUtil cleanerUtil) {

        this.dataInputCleanerUtil = cleanerUtil;
    }

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
    public BigDecimal calculateAvgRenumerationPerAnnum(List<JobPost> jobPosts) {

        BigDecimal avgRenum = BigDecimal.valueOf(0.00);
        BigDecimal renumSum = BigDecimal.valueOf(0.00);

        List<BigDecimal> extractedRenums = extractJobRenumeratios(jobPosts);
        Set<BigDecimal> renumSet = dataInputCleanerUtil.cleanJobRenum(extractedRenums);

        int sizeOfRenumSet = renumSet.size();

        for (BigDecimal renum : renumSet) {

            int renumDigitsBefDecimalPoint = getDigitsOfBigD(renum);
            
            if (renumDigitsBefDecimalPoint < 4)
                renum = convertHourlyToAnnum(renum);

            if (renumDigitsBefDecimalPoint > 4 && renumDigitsBefDecimalPoint < 6)
                renum = convertMonthlyToAnnum(renum);

            renumSum = renumSum.add(renum);
        }

        avgRenum = renumSum.divide(BigDecimal.valueOf(sizeOfRenumSet));
        return avgRenum;
    }

    public JobPost findJobPostWithHighestRenum(List<JobPost> jobPosts) {
        
        JobPost jobPost = new JobPost();

        BigDecimal maxJobPostRenum = BigDecimal.valueOf(0.00);

        for (JobPost jobPostIn : jobPosts) {
            
            Set<BigDecimal> jobPostRenumSet = new HashSet<BigDecimal>(jobPostIn.getRenumeration());

            for (BigDecimal jobPostRenum : jobPostRenumSet) {

                int renumDigitsBefDecimalPoint = getDigitsOfBigD(jobPostRenum);

                if (renumDigitsBefDecimalPoint < 4)
                    jobPostRenum = convertHourlyToAnnum(jobPostRenum);

                if (renumDigitsBefDecimalPoint > 4 && renumDigitsBefDecimalPoint < 6)
                    jobPostRenum = convertMonthlyToAnnum(jobPostRenum);
                
                if (jobPostRenum.compareTo(maxJobPostRenum) == 1) {

                    maxJobPostRenum = jobPostRenum;
                    jobPost = jobPostIn;
                }
            }
        }
        return jobPost;
    }

	public JobPost findJobPostWithLowestRenum(List<JobPost> jobPosts) {
		
        JobPost jobPost = new JobPost();

        BigDecimal minJobPostRenum = BigDecimal.valueOf(1000000000.00);

        for (JobPost jobPostIn : jobPosts) {
            
            Set<BigDecimal> jobPostRenumSet = new HashSet<>(jobPostIn.getRenumeration());

            for (BigDecimal jobPostRenum : jobPostRenumSet) {

                int renumDigitsBefDecimalPoint = getDigitsOfBigD(jobPostRenum);

                if (renumDigitsBefDecimalPoint < 4)
                    jobPostRenum = convertHourlyToAnnum(jobPostRenum);

                if (renumDigitsBefDecimalPoint > 4 && renumDigitsBefDecimalPoint < 6)
                    jobPostRenum = convertMonthlyToAnnum(jobPostRenum);
                
                if (jobPostRenum.compareTo(minJobPostRenum) == -1) {

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

    private List<BigDecimal> extractJobRenumeratios(List<JobPost> jobPosts) {
        
        List<BigDecimal> renums = new ArrayList<>();

        for (JobPost jobPost : jobPosts) {

            Set<BigDecimal> jobPostRenums = jobPost.getRenumeration();

            for (BigDecimal jobPostRenum : jobPostRenums)
                renums.add(jobPostRenum);
        }
        return renums;
    }

    private BigDecimal convertHourlyToAnnum(BigDecimal renum) {
        
        BigDecimal day = BigDecimal.valueOf(8);
        BigDecimal week = BigDecimal.valueOf(5);
        BigDecimal month = BigDecimal.valueOf(4);
        BigDecimal year = BigDecimal.valueOf(12) ;

        renum = renum.multiply(day);
        renum = renum.multiply(week);
        renum = renum.multiply(month);
        renum = renum.multiply(year);

        return renum;
    }

    private BigDecimal convertMonthlyToAnnum(BigDecimal renum) {

        BigDecimal year = BigDecimal.valueOf(12);

        return renum.multiply(year);
    }

    private int getDigitsOfBigD(BigDecimal renum) {

        BigDecimal tmpRenum = renum.stripTrailingZeros();
        
        return tmpRenum.precision() - tmpRenum.scale();
    }
}
