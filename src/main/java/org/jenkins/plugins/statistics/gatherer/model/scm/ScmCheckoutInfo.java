package org.jenkins.plugins.statistics.gatherer.model.scm;

import java.util.Date;

/**
 * Created by mcharron on 2016-07-18.
 */
public class ScmCheckoutInfo {

    private String buildUrl;
    private Date startTime;
    private Date endTime;

    public ScmCheckoutInfo(Date startTime, Date endTime, String buildUrl) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.buildUrl = buildUrl;
    }

    public ScmCheckoutInfo() {
        this.startTime = new Date(0);
        this.endTime = new Date(0);
        this.buildUrl = "";
    }

    public String getBuildUrl() {
        return buildUrl;
    }

    public void setBuildUrl(String buildUrl) {
        this.buildUrl = buildUrl;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
