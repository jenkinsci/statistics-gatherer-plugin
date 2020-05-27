package org.jenkins.plugins.statistics.gatherer.model.queue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.net.URI;

/**
 * Created by hthakkallapally on 3/16/2015.
 */
public class QueueStats {

    private String ciUrl;

    private String jobName;

    private Date entryTime;

    private Date exitTime;

    private String startedBy;

    private int jenkinsQueueId;

    private String status;

    private int contextId;

    private long duration;

    private List<QueueCause> queueCauses = new ArrayList<>();

    public QueueStats(String ciUrl,
                      String jobName,
                      Date entryTime,
                      Date exitTime,
                      String startedBy,
                      int jenkinsQueueId,
                      String status,
                      long duration,
                      List<QueueCause> queueCauses,
                      int contextId) {
        this.ciUrl = ciUrl;
        this.jobName = jobName;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.startedBy = startedBy;
        this.jenkinsQueueId = jenkinsQueueId;
        this.status = status;
        this.duration = duration;
        this.queueCauses = queueCauses;
        this.contextId = contextId;
    }

    public QueueStats() {
        this.ciUrl = "";
        this.jobName = "";
        this.entryTime = new Date();
        this.exitTime = new Date();
        this.startedBy = "";
        this.jenkinsQueueId = 0;
        this.status = "";
        this.duration = 0;
        this.queueCauses = new ArrayList<>();
        this.contextId = 0;
    }


    public String getCiUrl() {
        return ciUrl;
    }

    public void setCiUrl(String ciUrl) {
        this.ciUrl = ciUrl;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Date getEntryTime() {
        return entryTime == null ? null : new Date(entryTime.getTime());
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime == null ? null : new Date(entryTime.getTime());
    }

    public Date getExitTime() {
        return exitTime == null ? null : new Date(exitTime.getTime());
    }

    public void setExitTime(Date exitTime) {
        this.exitTime = exitTime == null ? null : new Date(exitTime.getTime());
    }

    public String getStartedBy() {
        return startedBy;
    }

    public void setStartedBy(String startedBy) {
        this.startedBy = startedBy;
    }

    public int getJenkinsQueueId() {
        return jenkinsQueueId;
    }

    public void setJenkinsQueueId(int jenkinsQueueId) {
        this.jenkinsQueueId = jenkinsQueueId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public List<QueueCause> getQueueCauses() {
        return queueCauses;
    }

    public void setQueueCauses(List<QueueCause> queueCauses) {
        this.queueCauses = queueCauses;
    }

    public void addQueueCause(QueueCause queueCause) {
        this.queueCauses.add(queueCause);
    }

    public int getContextId() {
        return contextId;
    }

    public void setContextId(int contextId) {
        this.contextId = contextId;
    }

    // short_message field is required for GELF format support
    public String getShort_message() {
        return String.format("queueStat: %s in queue %s cause %s", jobName, status,
            queueCauses.size()>0 ? queueCauses.get(0).getReasonForWaiting() : "unknown");
    }

    // host field is required for GELF format support
    public String getHost() {
        try {
            URI uri = new URI(ciUrl);
            return uri.getHost();
        } catch(Exception e) {
            return null;
        }
    }
}
