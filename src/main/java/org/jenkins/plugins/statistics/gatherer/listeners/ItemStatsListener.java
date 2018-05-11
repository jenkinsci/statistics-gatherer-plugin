package org.jenkins.plugins.statistics.gatherer.listeners;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.model.Item;
import hudson.model.User;
import hudson.model.listeners.ItemListener;
import jenkins.YesNoMaybe;
import jenkins.model.Jenkins;
import org.jenkins.plugins.statistics.gatherer.model.job.JobStats;
import org.jenkins.plugins.statistics.gatherer.util.*;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by hthakkallapally on 3/12/2015.
 */
@Extension(dynamicLoadable = YesNoMaybe.YES)
public class ItemStatsListener extends ItemListener {
    private static final Logger LOGGER = Logger.getLogger(ItemStatsListener.class.getName());

    public ItemStatsListener() {
        //Necessary for jenkins
    }

    @Override
    public void onCreated(Item item) {
        if (PropertyLoader.getProjectInfo() && canHandle(item)) {
            try {
                AbstractProject<?, ?> project = asProject(item);
                JobStats ciJob = addCIJobData(project);
                ciJob.setCreatedDate(new Date());
                ciJob.setStatus(Constants.ACTIVE);
                setConfig(project, ciJob);
                RestClientUtil.postToService(getRestUrl(), ciJob);
                SnsClientUtil.publishToSns(ciJob);
                LogbackUtil.info(ciJob);
            } catch (Exception e) {
                logException(item, e);
            }
        }
    }

    private AbstractProject<?, ?> asProject(Item item) {
        if(canHandle(item)) {
            return (AbstractProject<?, ?>) item;
        } else {
            throw new IllegalArgumentException("Discarding item " + item.getDisplayName() + "/" + item.getClass() + " because it is not an AbstractProject");
        }
    }

    private boolean canHandle(Item item) {
        return item instanceof AbstractProject<?, ?>;
    }

    private void logException(Item item, Exception e) {
        LOGGER.log(Level.WARNING, "Failed to call API " + getRestUrl() +
                " for job " + item.getDisplayName(), e);
    }

    /**
     * Construct REST API url for project resource.
     *
     * @return
     */
    private String getRestUrl() {
        return PropertyLoader.getProjectEndPoint();
    }

    /**
     * Construct CIJob model and populate common data in helper method.
     *
     * @param project
     * @return
     */
    private JobStats addCIJobData(AbstractProject<?, ?> project) {
        JobStats ciJob = new JobStats();
        ciJob.setCiUrl(Jenkins.getInstance().getRootUrl());
        ciJob.setName(project.getName());
        ciJob.setJobUrl(project.getUrl());
        String userName = Jenkins.getAuthentication().getName();
        User user = Jenkins.getInstance().getUser(userName);
        if(user != null) {
            ciJob.setUserId(user.getId());
            ciJob.setUserName(user.getFullName());
        }

        return ciJob;
    }

    /**
     * Get job configuration as a string and store it in DB.
     *
     * @param project
     * @param ciJob
     */
    private void setConfig(AbstractProject<?, ?> project, JobStats ciJob) {
        try {
            ciJob.setConfigFile(project.getConfigFile().asString());
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Failed to get config.xml file " +
                    " for " + project.getDisplayName(), e);
        }
    }

    @Override
    public void onUpdated(Item item) {
        if (PropertyLoader.getProjectInfo() && canHandle(item)) {
            AbstractProject<?, ?> project = asProject(item);
            try {
                JobStats ciJob = addCIJobData(project);
                ciJob.setUpdatedDate(new Date());
                ciJob.setStatus(project.isDisabled() ? Constants.DISABLED : Constants.ACTIVE);
                setConfig(project, ciJob);
                RestClientUtil.postToService(getRestUrl(), ciJob);
                SnsClientUtil.publishToSns(ciJob);
                LogbackUtil.info(ciJob);
            } catch (Exception e) {
                logException(item, e);
            }
        }
    }

    @Override
    public void onDeleted(Item item) {
        if (PropertyLoader.getProjectInfo() && canHandle(item)) {
            AbstractProject<?, ?> project = asProject(item);
            try {
                JobStats ciJob = addCIJobData(project);
                ciJob.setUpdatedDate(new Date());
                ciJob.setStatus(Constants.DELETED);
                RestClientUtil.postToService(getRestUrl(), ciJob);
                SnsClientUtil.publishToSns(ciJob);
                LogbackUtil.info(ciJob);
            } catch (Exception e) {
                logException(item, e);
            }
        }
    }
}
