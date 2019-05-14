package org.jenkins.plugins.statistics.gatherer.model.step;

import java.util.Date;

/**
 * Created by mcharron on 2016-07-12.
 */
public class BuildStepStats {

    private Date startTime;
    private Date endTime;
    private String buildUrl;
    private String buildStepType;
    private String buildStepId;

    public BuildStepStats(Date startTime, Date endTime, String buildUrl, String buildStepType, String buildStepId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.buildUrl = buildUrl;
        this.buildStepType = buildStepType;
        this.buildStepId = buildStepId;
    }

    public BuildStepStats() {
        this.startTime = new Date(0);
        this.endTime = new Date(0);
        this.buildUrl = "";
        this.buildStepType = "";
        this.buildStepId = "";
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

    public String getBuildUrl() {
        return buildUrl;
    }

    public void setBuildUrl(String buildUrl) {
        this.buildUrl = buildUrl;
    }

    public String getBuildStepType() {
        return buildStepType;
    }

    public void setBuildStepType(String buildStepType) {
        this.buildStepType = buildStepType;
    }

    public String getBuildStepId() {
        return buildStepId;
    }

    public void setBuildStepId(String buildStepId) {
        this.buildStepId = buildStepId;
    }
}
