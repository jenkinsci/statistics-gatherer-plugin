package org.jenkins.plugins.statistics.gatherer.model.build;

/**
 * Created by hthakkallapally on 3/16/2015.
 */
public class SlaveInfo {

    private String slaveName;
    private String executor;
    private String label;

    public SlaveInfo(String slaveName, String executor, String label) {
        this.slaveName = slaveName;
        this.executor = executor;
        this.label = label;
    }

    public SlaveInfo() {
        this.slaveName = "";
        this.executor = "";
        this.label = "";
    }

    public String getSlaveName() {
        return slaveName;
    }

    public void setSlaveName(String slaveName) {
        this.slaveName = slaveName;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
