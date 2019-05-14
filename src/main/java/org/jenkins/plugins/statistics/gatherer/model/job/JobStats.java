package org.jenkins.plugins.statistics.gatherer.model.job;

import java.util.Date;

/**
 * Created by hthakkallapally on 3/16/2015.
 */
public class JobStats {

    private String name;
    private Date createdDate;
    private String userId;
    private String userName;
    private String ciUrl;
    private Date updatedDate;
    private String status;
    private String configFile;
    private String jobUrl;

    public JobStats(String name,
                    Date createdDate,
                    String userId,
                    String userName,
                    String ciUrl,
                    Date updatedDate,
                    String status,
                    String configFile,
                    String jobUrl) {
        this.name = name;
        this.createdDate = createdDate;
        this.userId = userId;
        this.userName = userName;
        this.ciUrl = ciUrl;
        this.updatedDate = updatedDate;
        this.status = status;
        this.configFile = configFile;
        this.jobUrl = jobUrl;
    }

    public JobStats() {
        this.name = "";
        this.createdDate = new Date();
        this.userId = "";
        this.userName = "";
        this.ciUrl = "";
        this.updatedDate = new Date();
        this.status = "";
        this.configFile = "";
        this.jobUrl = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedDate() {
        return createdDate == null ? null : new Date(createdDate.getTime());
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate == null ? null : new Date(createdDate.getTime());
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCiUrl() {
        return ciUrl;
    }

    public void setCiUrl(String ciUrl) {
        this.ciUrl = ciUrl;
    }

    public Date getUpdatedDate() {
        return updatedDate == null ? null : new Date(updatedDate.getTime());
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate == null ? null : new Date(updatedDate.getTime());
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    public String getJobUrl() {
        return jobUrl;
    }

    public void setJobUrl(String jobUrl) {
        this.jobUrl = jobUrl;
    }
}
