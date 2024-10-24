package com.analytics.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class DataInputCleanerUtil {

    public String cleanJobTitle(String jobTitle) {

        return formatJobTitleStr(jobTitle).toLowerCase();
    }

    public String cleanJobDepartment(String jobDepartment) {

        return jobDepartment.toLowerCase();
    }

    public Date cleanJobClosingDate(Date jobClosingDate) {

        return jobClosingDate;
    }

    public Set<BigDecimal> cleanJobRenum(List<BigDecimal> jobRenumerations) {

        List<BigDecimal> jobRenums = new ArrayList<>();

        for (BigDecimal jobRenum : jobRenumerations) {
            
            BigDecimal tmpRenum = null;
            tmpRenum = jobRenum.setScale(2, RoundingMode.HALF_UP);
            jobRenums.add(tmpRenum);
        }
        return new HashSet<BigDecimal>(jobRenums);
    }

    public String cleanAmountString(String curInAmountStr) {
        
        List<String> cleanedAmountStrings = new ArrayList<>();
        String cleanedAmountStr = null;

        String amountStrRegex = "\\d+";

        Pattern amountStrPattern = Pattern.compile(amountStrRegex);
        Matcher amountStrMatcher = amountStrPattern.matcher(curInAmountStr);

        while (amountStrMatcher.find())
            cleanedAmountStrings.add(amountStrMatcher.group());

        if (cleanedAmountStrings.size() > 0) {

            cleanedAmountStr = "";
            for (String cleanCurInAmount : cleanedAmountStrings)
                cleanedAmountStr += cleanCurInAmount;
        }
        return cleanedAmountStr;
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
}
