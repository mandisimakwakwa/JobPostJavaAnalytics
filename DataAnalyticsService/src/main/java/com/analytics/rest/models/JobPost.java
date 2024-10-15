package com.analytics.rest.models;

import java.util.Date;
import java.util.List;

public class JobPost {

    String title;
    String department;
    List<Double> renumeration;
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

    public List<Double> getRenumeration() {
        return renumeration;
    }
    public void setRenumeration(List<Double> renumeration) {
        this.renumeration = renumeration;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
