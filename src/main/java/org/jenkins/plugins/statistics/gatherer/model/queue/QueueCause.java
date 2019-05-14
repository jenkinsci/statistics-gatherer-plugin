package org.jenkins.plugins.statistics.gatherer.model.queue;

import java.util.Date;

/**
 * Created by hthakkallapally on 4/20/2015.
 */
public class QueueCause {

    private Date entryTime;
    private Date exitTime;
    private String reasonForWaiting;
    private String type;

    public QueueCause(Date entryTime,
                      Date exitTime,
                      String reasonForWaiting,
                      String type) {
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.reasonForWaiting = reasonForWaiting;
        this.type = type;
    }

    public QueueCause() {
        this.entryTime = new Date();
        this.exitTime = new Date();
        this.reasonForWaiting = "";
        this.type = "";
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public Date getExitTime() {
        return exitTime;
    }

    public void setExitTime(Date exitTime) {
        this.exitTime = exitTime;
    }

    public String getReasonForWaiting() {
        return reasonForWaiting;
    }

    public void setReasonForWaiting(String reasonForWaiting) {
        this.reasonForWaiting = reasonForWaiting;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
