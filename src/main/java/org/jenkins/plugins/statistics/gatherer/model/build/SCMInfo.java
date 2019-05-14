package org.jenkins.plugins.statistics.gatherer.model.build;

/**
 * Created by hthakkallapally on 3/16/2015.
 */
public class SCMInfo {

    private String url;
    private String branch;
    private String commit;

    public SCMInfo(String url, String branch, String commit) {
        this.url = url;
        this.branch = branch;
        this.commit = commit;
    }

    public SCMInfo() {
        this.url = "";
        this.branch = "";
        this.commit = "";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }
}
