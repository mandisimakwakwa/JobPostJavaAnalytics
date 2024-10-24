package com.analytics.rest.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

public class JobPost {

    String title;
    String department;
    Set<BigDecimal> renumeration;
    Date date;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }

    public Set<BigDecimal> getRenumeration() {
        return renumeration;
    }
    public void setRenumeration(Set<BigDecimal> renumeration) {
        this.renumeration = renumeration;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
