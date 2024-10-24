package com.analytics.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.analytics.constants.AppConstants;
import com.analytics.rest.models.JobPost;

@Component
public class DataInputsConversionUtil {

    private DataInputCleanerUtil dataInputCleanerUtil;

    public DataInputsConversionUtil(DataInputCleanerUtil cleanerUtil) {

        this.dataInputCleanerUtil = cleanerUtil;
    }

    public List<JobPost> convDataInputsToJobPosts(List<String[]> dataInputs) throws Exception {

        List<JobPost> jobPosts = new ArrayList<>();

        int index = -1;

        for (String[] dataInput : dataInputs) {

            if (index < 0) {
                index++;
                continue;
            }

            JobPost jobPost = new JobPost();
            jobPost = convDataInputToJobPost(dataInput);
            jobPosts.add(jobPost);

            index++;
        }
        return jobPosts;
    }

    private JobPost convDataInputToJobPost(String[] dataInput) throws Exception {

        JobPost jobPost = new JobPost();

        String jobTitle = extractJobTitleFromDataInput(dataInput);
        String jobDepartment = extractJobDepartmentFromDataInput(dataInput);

        String jobTitleClean = dataInputCleanerUtil.cleanJobTitle(jobTitle);
        String jobDepartmentClean = dataInputCleanerUtil.cleanJobDepartment(jobDepartment);

        Date jobClosingDate = extractJobClosingDateFromDataInput(dataInput);
        Date jobClosingDateClean = dataInputCleanerUtil.cleanJobClosingDate(jobClosingDate);
        
        List<BigDecimal> jobRenumerations = extractJobRenumerationsFromDataInput(dataInput);
        Set<BigDecimal> jobRenumClean = dataInputCleanerUtil.cleanJobRenum(jobRenumerations);

        jobPost.setTitle(jobTitleClean);
        jobPost.setDepartment(jobDepartmentClean);
        jobPost.setDate(jobClosingDateClean);
        jobPost.setRenumeration(jobRenumClean);
        return jobPost;
    }


    private static String extractJobTitleFromDataInput(String[] dataInput) throws Exception {
        
        String jobTitle = "";

        int departmentStrPos = findDepartmentStrPos(dataInput);
        int titleTmpCount = 0;

        if (departmentStrPos < 0)
            throw new Exception(
                "Error couldn't find depertment string in data input");

        while (titleTmpCount < departmentStrPos) {
            
            jobTitle += dataInput[titleTmpCount] + " ";
            titleTmpCount++;
        }

        return jobTitle;
    }

    private static String extractJobDepartmentFromDataInput(String[] dataInput) throws Exception {
        
        String jobDepartment = "";

        int departmentStrPos = findDepartmentStrPos(dataInput);

        jobDepartment = dataInput[departmentStrPos];
        return jobDepartment;
    }

    private static Date extractJobClosingDateFromDataInput(String[] dataInput) throws Exception {
        
        String closingDateStr = "";
        String dateRegex = "\\['(\\d{4}/\\d{2}/\\d{2})'\\]";

        int dateStrPos = findDateStrPos(dataInput);

        boolean isDateStrMatch = false;

        if (dateStrPos < 0)
            throw new Exception("Error : Date not found input string.");

        closingDateStr = dataInput[dateStrPos];

        Pattern pattern = Pattern.compile(dateRegex);
        Matcher matcher = pattern.matcher(closingDateStr);

        isDateStrMatch = matcher.matches();
        if (!isDateStrMatch)
            throw new Exception("Error : Invalid date input string given.");
        
        closingDateStr = matcher.group(1);
        closingDateStr = closingDateStr.replace("/", "-");

        return convertDateStrToDate(closingDateStr);
    }

    private static Date convertDateStrToDate(String closingDateStr) throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        return dateFormat.parse(closingDateStr);
    }

    private List<BigDecimal> extractJobRenumerationsFromDataInput(String[] dataInput) {
        
        List<BigDecimal> renumerations = new ArrayList<>();

        int renumerationStrPos = findRenumerationStrPos(dataInput);
        int tracker = dataInput.length;

        String centsRepStr = "00";

        while (renumerationStrPos < tracker) {

            String curInAmountStr = dataInput[renumerationStrPos];

            // TODO: This is terrible please fix later
            if (findDateStrMatch(curInAmountStr)) {

                renumerationStrPos++;
                continue;
            }
            String nextInAmountStr = !findDateStrMatch(dataInput[renumerationStrPos + 1]) ? dataInput[renumerationStrPos + 1] : centsRepStr;
            String newAmountStr = "";

            if (curInAmountStr.contains(centsRepStr) && !curInAmountStr.contains("R")) {

                renumerationStrPos++;
                continue;
            }

            if (!nextInAmountStr.equals(centsRepStr) && (renumerationStrPos + 1) == (tracker - 1)) {
                nextInAmountStr = centsRepStr;
                renumerationStrPos++;
            }

            if (nextInAmountStr.contains("R") && curInAmountStr.contains("R"))
                nextInAmountStr = centsRepStr;

            String cleanCurInAmountStr = dataInputCleanerUtil.cleanAmountString(curInAmountStr);
            String cleanNextInAmountStr = dataInputCleanerUtil.cleanAmountString(nextInAmountStr);

            if (cleanNextInAmountStr.equals(cleanCurInAmountStr)) {
                cleanNextInAmountStr = centsRepStr;
                renumerationStrPos++;
            }

            if (cleanNextInAmountStr.contains(centsRepStr) && !cleanCurInAmountStr.isEmpty())
                newAmountStr = cleanCurInAmountStr + "." + cleanNextInAmountStr;

            if (newAmountStr.isEmpty() && !cleanCurInAmountStr.isEmpty())
                newAmountStr = cleanCurInAmountStr + centsRepStr;

            BigDecimal newAmount = new BigDecimal(newAmountStr);
            renumerations.add(newAmount);
            
            renumerationStrPos++;
        }
        
        return renumerations;
    }

    private static int findDepartmentStrPos(String[] dataInput) {

        int dataInputLen = dataInput.length;

        int departmentPos = -1;

        for (int i = 0; i < dataInputLen; i++) {
            
            String dataInputVal = dataInput[i];
            if (AppConstants.DEPARTMENTS.contains(dataInputVal)) {

                departmentPos = i;
                return departmentPos;
            }
        }
        return departmentPos;
    }

    private static int findDateStrPos(String[] dataInput) {

        int datePos = -1;
        int index = 0;

        

        for (String dataInputStr : dataInput) {

            boolean dateMatcher = false;

            dateMatcher = findDateStrMatch(dataInputStr);
            if (dateMatcher) {

                datePos = index;
                return datePos;
            }
            index++;
        }
        return datePos;
    }

    private static int findRenumerationStrPos(String[] dataInput) {

        String renumerationRegex = "R\\d+.*";

        int renumPos = -1;
        int index = 0;

        for (String dataInputStr : dataInput) {
            
            Matcher renumerationMatcher = Pattern.compile(renumerationRegex).matcher(dataInputStr);
            if (renumerationMatcher.find()) {

                renumPos = index;
                return renumPos;
            }
            index++;
        }
        return renumPos;
    }

    private static boolean findDateStrMatch(String dateStr) {

        String dateStrRegex = "\\[\\'\\d{4}/\\d{2}/\\d{2}\\'\\]";

        boolean dateStrMatch = false;

        dateStrMatch = Pattern.compile(dateStrRegex).matcher(dateStr).matches();
        return dateStrMatch;
    }
}
