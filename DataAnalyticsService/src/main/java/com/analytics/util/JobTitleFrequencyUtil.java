package com.analytics.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.analytics.rest.models.JobPost;

@Component
public class JobTitleFrequencyUtil {

    // HashMap Reduce Pattern Impl

    public List<String> findMostFrequentWords(List<JobPost> jobPosts) {

        List<String> mostFrequentWords = new ArrayList<>();
        List<String> jobTitleWordsList = extractWordListFromJobTitles(jobPosts);

        Map<String, Integer> jobTitleWordsMap = new HashMap<>();

        int wordListSize = jobTitleWordsList.size();

        for (int i = 0; i < wordListSize; i++) {

            String jobTitleWord = jobTitleWordsList.get(i).toLowerCase();

            if (jobTitleWord.isEmpty() || jobTitleWord.isBlank())
                continue;
            
            jobTitleWordsMap.put(
                jobTitleWord,
                jobTitleWordsMap.getOrDefault(jobTitleWord, 0) + 1);
        }

        mostFrequentWords = reduceMostFreqWords(jobTitleWordsMap);
        return mostFrequentWords;
    }

    private static List<String> reduceMostFreqWords(Map<String, Integer> jobTitleWordsMap) {
        
        List<String> mostFreqWords = new ArrayList<>();

        int maxWordCount = 0;

        for (Map.Entry<String, Integer> jobTitleWordsEntry : jobTitleWordsMap.entrySet()) {
            
            int count = jobTitleWordsEntry.getValue();
            String entryWord = jobTitleWordsEntry.getKey();

            if (count > maxWordCount) {

                maxWordCount = count;
                mostFreqWords.clear();
                mostFreqWords.add(entryWord);
            } else if(count == maxWordCount)
                mostFreqWords.add(entryWord);
        }
        return mostFreqWords;
    }

    private static List<String> extractWordListFromJobTitles(List<JobPost> jobPosts) {

        List<String> wordList = new ArrayList<>();

        for (JobPost jobPost : jobPosts) {
            
            String jobTitle = jobPost.getTitle();

            String[] jobTitleWords = null;
            jobTitleWords = jobTitle.split(" ");
            wordList = addTitleWordsToWordList(jobTitleWords, wordList);
        }
        return wordList;
    }

    private static List<String> addTitleWordsToWordList(String[] jobTitleWords, List<String> wordList) {
        
        for (String jobTitleWord : jobTitleWords) {
            wordList.add(jobTitleWord);
        }
        return wordList;
    }
}
