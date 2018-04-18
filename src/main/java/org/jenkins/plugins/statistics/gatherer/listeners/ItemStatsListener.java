package org.jenkins.plugins.statistics.gatherer.listeners;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.model.Item;
import org.jenkins.plugins.statistics.gatherer.model.job.JobStats;
import org.jenkins.plugins.statistics.gatherer.util.*;

import java.util.Date;

/**
 * Created by hthakkallapally on 3/12/2015.
 */
@Extension
public class ItemStatsListener extends GenericItemStatsListener<AbstractProject<?,?>> {

    public ItemStatsListener() {
        //Necessary for jenkins
        super(JobStats.JobType.PROJECT);
    }

    @Override
    public void onUpdated(Item item) {
        if (PropertyLoader.getProjectInfo() && canHandle(item)) {
            AbstractProject<?,?> project = castItem(item);
            try {
                JobStats ciJob = addCIJobData(project);
                ciJob.setUpdatedDate(new Date());
                ciJob.setStatus(project.isDisabled() ? Constants.DISABLED : Constants.ACTIVE);
                setConfig(project, ciJob);
                publishEvent(ciJob);
            } catch (Exception e) {
                logException(item, e);
            }
        }
    }

    @Override
    protected boolean canHandle(Item item) {
        return item instanceof AbstractProject<?, ?>;
    }
}
