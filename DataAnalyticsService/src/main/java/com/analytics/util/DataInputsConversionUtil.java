package com.analytics.util;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.analytics.rest.models.JobPost;

@Component
public class DataInputsConversionUtil {

    private final static List<String> deparmentList = Arrays.asList(
        "Agriculture, Land Reform and Rural Development",
        "Basic Education",
        "Civilian Secretariat for Police",
        "Communications and Digital Technologies",
        "Cooperative Governance and Traditional Affairs",
        "Co-Operative Governance and Traditional Affairs",
        "Correctional Services",
        "Defence",
        "Employment and Labour",
        "Forestry, Fisheries and the Environment",
        "Government Communication and Information System",
        "Health",
        "Higher Education and Training",
        "Home Affairs",
        "Human Settlements",
        "Independent Police Investigative Directorate",
        "International Relations and Cooperation",
        "Justice and Constitutional Development",
        "Military Veterans",
        "Mineral Resources and Energy",
        "National School of Government",
        "National Treasury",
        "Office of the Chief Justice",
        "Planning Monitoring and Evaluation",
        "Public Enterprises",
        "Public Service and Administration",
        "Public Service Commission",
        "Public Works and Infrastructure",
        "Infrastructure Development",
        "Science and Innovation",
        "Small Business Development",
        "Social Development",
        "SA Police Service",
        "SA Revenue Service",
        "State Security Agency",
        "Sport, Arts and Culture",
        "Statistics South Africa",
        "Tourism",
        "Trade, Industry and Competition",
        "Transport",
        "Water and Sanitation",
        "Women, Youth and Persons with Disabilities",
        "The Presidency"
    );

    public List<JobPost> convDataInputsToJobPosts(List<String[]> dataInputs) throws Exception {

        List<JobPost> jobPosts = new ArrayList<>();

        int index = -1;

        for (String[] dataInput : dataInputs) {

            if (index < 1) {
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

    private static JobPost convDataInputToJobPost(String[] dataInput) throws Exception {

        JobPost jobPost = new JobPost();

        String jobTitle = extractJobTitleFromDataInput(dataInput);
        String jobDepartment = extractJobDepartmentFromDataInput(dataInput);
        Date jobClosingDate = extractJobClosingDateFromDataInput(dataInput);
        List<Double> jobRenumerations = extractJobRenumerationsFromDataInput(dataInput);

        jobPost.setTitle(jobTitle);
        jobPost.setDepartment(jobDepartment);
        jobPost.setDate(jobClosingDate);
        jobPost.setRenumeration(jobRenumerations);
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

        return formatJobTitleStr(jobTitle);
    }

    private static String extractJobDepartmentFromDataInput(String[] dataInput) throws Exception {
        
        String jobDepartment = "";

        int departmentStrPos = findDepartmentStrPos(dataInput);

        jobDepartment = dataInput[departmentStrPos];
        return jobDepartment;
    }

    private static Date extractJobClosingDateFromDataInput(String[] dataInput) throws Exception {
        
        Date closingDate = null;

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

        closingDate = Date.valueOf(closingDateStr);
        return closingDate;
    }

    private static List<Double> extractJobRenumerationsFromDataInput(String[] dataInput) {
        
        List<Double> renumerations = new ArrayList<>();

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
            String nextInAmountStr = dataInput[renumerationStrPos + 1] != null ? dataInput[renumerationStrPos + 1] : centsRepStr;
            String newAmountStr = "";

            if (curInAmountStr.contains(centsRepStr) && !curInAmountStr.contains("R")) {

                renumerationStrPos++;
                continue;
            }

            if (!nextInAmountStr.equals(centsRepStr) && (renumerationStrPos + 1) == (tracker - 1)) {
                nextInAmountStr = centsRepStr;
                renumerationStrPos++;
            }

            List<String> cleanCurInAmountStrArr = cleanAmountString(curInAmountStr);
            List<String> cleanNextInAmountStrArr = cleanAmountString(nextInAmountStr);

            String cleanCurInAmountStr = cleanCurInAmountStrArr.get(0);
            String cleanNextInAmountStr = cleanNextInAmountStrArr.get(0);

            if (cleanNextInAmountStr.equals(cleanCurInAmountStr)) {
                cleanNextInAmountStr = centsRepStr;
                renumerationStrPos++;
            }

            if (cleanCurInAmountStrArr.size() > 1) {

                cleanCurInAmountStr = "";
                for (String cleanCurInAmount : cleanCurInAmountStrArr) {
                    cleanCurInAmountStr += cleanCurInAmount;
                }
            }

            if (cleanNextInAmountStr.contains(centsRepStr) && !cleanCurInAmountStrArr.isEmpty())
                newAmountStr = cleanCurInAmountStr + "." + cleanNextInAmountStr;

            if (newAmountStr.isEmpty() && !cleanCurInAmountStrArr.isEmpty())
                newAmountStr = cleanCurInAmountStr + centsRepStr;

            Double newAmount = Double.valueOf(newAmountStr);
            renumerations.add(newAmount);
            
            renumerationStrPos++;
        }
        
        return renumerations;
    }

    private static String formatJobTitleStr(String jobTitle) {

        String spaceRegex = "\\s+";
        String lettersAndNumbersOnlyRegex = "[^a-zA-Z0-9\\s]";

        if (jobTitle.isEmpty())
         throw new NullPointerException("Cannot format empty job title string.");

        jobTitle = jobTitle.replaceAll(lettersAndNumbersOnlyRegex, " ");
        jobTitle = jobTitle.trim().replaceAll(spaceRegex, " ");

        return jobTitle;
    }

    private static int findDepartmentStrPos(String[] dataInput) {

        int dataInputLen = dataInput.length;

        int departmentPos = -1;

        for (int i = 0; i < dataInputLen; i++) {
            
            String dataInputVal = dataInput[i];
            if (deparmentList.contains(dataInputVal)) {

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

    private static boolean findDateStrMatch(String dateStr) {

        String dateStrRegex = "\\[\\'\\d{4}/\\d{2}/\\d{2}\\'\\]";

        boolean dateStrMatch = false;

        dateStrMatch = Pattern.compile(dateStrRegex).matcher(dateStr).matches();
        return dateStrMatch;
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

    private static List<String> cleanAmountString(String curInAmountStr) {
        
        List<String> cleanedAmountString = new ArrayList<>();

        String amountStrRegex = "\\d+";

        Pattern amountStrPattern = Pattern.compile(amountStrRegex);
        Matcher amountStrMatcher = amountStrPattern.matcher(curInAmountStr);

        while (amountStrMatcher.find()) {
            cleanedAmountString.add(amountStrMatcher.group());
        }
        return cleanedAmountString;
    }
}
