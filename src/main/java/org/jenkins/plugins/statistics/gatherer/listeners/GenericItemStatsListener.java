package org.jenkins.plugins.statistics.gatherer.listeners;

import hudson.model.AbstractItem;
import hudson.model.AbstractProject;
import hudson.model.Item;
import hudson.model.User;
import hudson.model.listeners.ItemListener;
import jenkins.model.Jenkins;
import org.jenkins.plugins.statistics.gatherer.model.job.JobStats;
import org.jenkins.plugins.statistics.gatherer.util.*;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class GenericItemStatsListener<T extends AbstractItem> extends ItemListener {
    protected final Logger LOGGER = Logger.getLogger(this.getClass().getName());
    protected final JobStats.JobType type;

    protected GenericItemStatsListener(JobStats.JobType type) {
        this.type = type;
    }

    protected abstract boolean canHandle(Item item);

    protected void logException(Item item, Exception e) {
        LOGGER.log(Level.WARNING, "Failed to call API " + getRestUrl() +
                " for job " + item.getDisplayName(), e);
    }

    /**
     * Construct REST API url for project resource.
     *
     * @return
     */
    protected String getRestUrl() {
        return PropertyLoader.getProjectEndPoint();
    }

    protected T castItem(Item item) {
        if (canHandle(item)) {
            return (T) item;
        } else {
            throw new IllegalArgumentException("Discarding item " + item.getDisplayName() + "/" + item.getClass()
                    + " because it is not of the expected type");
        }
    }

    @Override
    public void onCreated(Item item) {
        if (PropertyLoader.getProjectInfo() && canHandle(item)) {
            try {
                T typedItem = castItem(item);
                JobStats ciJob = addCIJobData(typedItem);
                ciJob.setCreatedDate(new Date());
                ciJob.setStatus(Constants.ACTIVE);
                setConfig(typedItem, ciJob);
                publishEvent(ciJob);
            } catch (Exception e) {
                logException(item, e);
            }
        }
    }

    @Override
    public void onDeleted(Item item) {
        if (PropertyLoader.getProjectInfo() && canHandle(item)) {
            T project = castItem(item);
            try {
                JobStats ciJob = addCIJobData(project);
                ciJob.setUpdatedDate(new Date());
                ciJob.setStatus(Constants.DELETED);
                publishEvent(ciJob);
            } catch (Exception e) {
                logException(item, e);
            }
        }
    }

    /**
     * Get job configuration as a string and store it in DB.
     *
     * @param item
     * @param ciJob
     */
    protected void setConfig(T item, JobStats ciJob) {
        try {
            ciJob.setConfigFile(item.getConfigFile().asString());
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Failed to get config.xml file " +
                    " for " + item.getDisplayName(), e);
        }
    }

    /**
     * Construct CIJob model and populate common data in helper method.
     *
     * @param item
     * @return
     */
    protected JobStats addCIJobData(T item) {
        JobStats ciJob = new JobStats();
        ciJob.setJobType(type);
        ciJob.setCiUrl(Jenkins.getInstance().getRootUrl());
        ciJob.setName(item.getName());
        ciJob.setJobUrl(item.getUrl());
        String userName = Jenkins.getAuthentication().getName();
        User user = Jenkins.getInstance().getUser(userName);
        if (user != null) {
            ciJob.setUserId(user.getId());
            ciJob.setUserName(user.getFullName());
        }

        return ciJob;
    }

    protected void publishEvent(Object event) {
        RestClientUtil.postToService(getRestUrl(), event);
        SnsClientUtil.publishToSns(event);
        LogbackUtil.info(event);
    }
}
