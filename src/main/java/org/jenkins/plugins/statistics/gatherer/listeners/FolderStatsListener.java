package org.jenkins.plugins.statistics.gatherer.listeners;

import com.cloudbees.hudson.plugins.folder.AbstractFolder;
import hudson.Extension;
import hudson.model.Item;
import org.jenkins.plugins.statistics.gatherer.model.job.JobStats;
import org.jenkins.plugins.statistics.gatherer.util.*;

import java.util.Date;

@Extension
public class FolderStatsListener extends GenericItemStatsListener<AbstractFolder<?>> {

    public FolderStatsListener() {
        //Necessary for jenkins
        super(JobStats.JobType.FOLDER);
    }

    @Override
    public void onUpdated(Item item) {
        if (PropertyLoader.getProjectInfo() && canHandle(item)) {
            AbstractFolder<?> project = castItem(item);
            try {
                JobStats ciJob = addCIJobData(project);
                ciJob.setUpdatedDate(new Date());
                setConfig(project, ciJob);
                publishEvent(ciJob);
            } catch (Exception e) {
                logException(item, e);
            }
        }
    }

    @Override
    protected boolean canHandle(Item item) {
        return item instanceof AbstractFolder<?>;
    }
}
