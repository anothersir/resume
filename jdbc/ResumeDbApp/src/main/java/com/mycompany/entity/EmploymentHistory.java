/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entity;

import java.sql.Date;

/**
 *
 * @author User
 */
public class EmploymentHistory {

    private Integer id;
    private String header;
    private Date beginDate;
    private Date endDate;
    private String jobDecsription;
    private User user;

    public EmploymentHistory() {
    }

    public EmploymentHistory(Integer id, String header, Date beginDate, Date endDate, String jobDecsription, User user) {
        this.id = id;
        this.header = header;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.jobDecsription = jobDecsription;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getJobDecsription() {
        return jobDecsription;
    }

    public void setJobDecsription(String jobDecsription) {
        this.jobDecsription = jobDecsription;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "EmploymentHistory{" + "id=" + id + ", header=" + header + ", beginDate=" + beginDate + ", endDate=" + endDate + ", jobDecsription=" + jobDecsription + ", user=" + user + '}';
    }
}
