package org.jenkins.plugins.statistics.gatherer.listeners;

import hudson.Extension;
import hudson.model.Cause;
import hudson.model.Queue.*;
import hudson.model.queue.QueueListener;
import hudson.triggers.SCMTrigger;
import hudson.triggers.TimerTrigger;
import jenkins.YesNoMaybe;
import jenkins.model.Jenkins;
import org.jenkins.plugins.statistics.gatherer.model.queue.QueueCause;
import org.jenkins.plugins.statistics.gatherer.model.queue.QueueStats;
import org.jenkins.plugins.statistics.gatherer.util.*;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by hthakkallapally on 3/13/2015.
 */
@Extension(dynamicLoadable = YesNoMaybe.YES)
public class QueueStatsListener extends QueueListener {
    private static final Logger LOGGER = Logger.getLogger(
            QueueStatsListener.class.getName());

    public QueueStatsListener() {
        //Necessary for jenkins
    }

    @Override
    public void onEnterWaiting(WaitingItem waitingItem) {
        if (PropertyLoader.getQueueInfo()) {
            try {
                QueueStats queue = getCiQueue(waitingItem);
                addStartedBy(waitingItem, queue);
                queue.setEntryTime(new Date());
                queue.setExitTime(null);
                queue.setStatus(Constants.ENTERED);
                if (waitingItem.getCauseOfBlockage() != null) {
                    addEntryQueueCause("waiting", waitingItem, queue);
                }
                RestClientUtil.postToService(getRestUrl(), queue);
                LogbackUtil.info(queue);
            } catch (Exception e) {
                logExceptionWaiting(waitingItem, e);
            }
        }
    }

    private void logExceptionWaiting(WaitingItem waitingItemi, Exception e) {
        LOGGER.log(Level.WARNING, "Failed to add Queue info for " +
                "job " + waitingItemi.task.getFullDisplayName() +
                " with queue id " + waitingItemi.getId() + " using " + getRestUrl(), e);
    }

    private void addEntryQueueCause(String type, Item item,
                                    QueueStats queue) {
        QueueCause cause = new QueueCause();
        cause.setType(type);
        cause.setEntryTime(new Date());
        cause.setExitTime(null);
        cause.setReasonForWaiting(item.getCauseOfBlockage().getShortDescription());
        queue.addQueueCause(cause);
    }

    @Override
    public void onLeaveWaiting(WaitingItem waitingItem) {
        if (PropertyLoader.getQueueInfo()) {
            try {
                QueueStats queue = getCiQueue(waitingItem);
                if (waitingItem.getCauseOfBlockage() != null) {
                    addExitQueueCause("waiting", waitingItem, queue);
                }
                RestClientUtil.postToService(getRestUrl(), queue);
                SnsClientUtil.publishToSns(queue);
                LogbackUtil.info(queue);
            } catch (Exception e) {
                logExceptionWaiting(waitingItem, e);
            }
        }
    }

    private void addExitQueueCause(String type, Item item, QueueStats queue) {
        QueueCause cause = new QueueCause();
        cause.setType(type);
        cause.setEntryTime(null);
        cause.setExitTime(new Date());
        cause.setReasonForWaiting(item.getCauseOfBlockage().getShortDescription());
        queue.addQueueCause(cause);
    }

    /**
     * onEnterBlocked is used to update Reason for waiting in Queue.
     * for ex. "Build #35 is already in progress (ETA:9 min 3 sec)"
     *
     * @param blockedItem
     */
    @Override
    public void onEnterBlocked(BlockedItem blockedItem) {
        if (PropertyLoader.getQueueInfo()) {
            try {
                QueueStats queue = getCiQueue(blockedItem);
                if (blockedItem.getCauseOfBlockage() != null) {
                    addEntryQueueCause("blocked", blockedItem, queue);
                }
                RestClientUtil.postToService(getRestUrl(), queue);
                SnsClientUtil.publishToSns(queue);
            } catch (Exception e) {
                logExceptionBlocked(blockedItem, e);
            }
        }
    }

    private void logExceptionBlocked(BlockedItem blockedItem, Exception e) {
        LOGGER.log(Level.WARNING, "Job " + blockedItem.task.getFullDisplayName() +
                " with queue id " + blockedItem.getId() +
                "failed with exception : " + e);
    }

    @Override
    public void onLeaveBlocked(BlockedItem blockedItem) {
        if (PropertyLoader.getQueueInfo()) {
            try {
                QueueStats queue = getCiQueue(blockedItem);
                if (blockedItem.getCauseOfBlockage() != null) {
                    addExitQueueCause("blocked", blockedItem, queue);
                }

                RestClientUtil.postToService(getRestUrl(), queue);
                SnsClientUtil.publishToSns(queue);
                LogbackUtil.info(queue);
            } catch (Exception e) {
                logExceptionBlocked(blockedItem, e);
            }
        }
    }

    @Override
    public void onEnterBuildable(BuildableItem buildableItem) {
        if (PropertyLoader.getQueueInfo()) {
            try {
                QueueStats queue = getCiQueue(buildableItem);
                if (buildableItem.getCauseOfBlockage() != null) {
                    addEntryQueueCause("buildable", buildableItem, queue);
                }
                RestClientUtil.postToService(getRestUrl(), queue);
                SnsClientUtil.publishToSns(queue);
                LogbackUtil.info(queue);
            } catch (Exception e) {
                logExceptionLeave(buildableItem, e);
            }
        }
    }

    @Override
    public void onLeaveBuildable(BuildableItem buildableItem) {
        if (PropertyLoader.getQueueInfo()) {
            try {
                QueueStats queue = getCiQueue(buildableItem);
                if (buildableItem.getCauseOfBlockage() != null) {
                    addExitQueueCause("buildable", buildableItem, queue);
                }
                RestClientUtil.postToService(getRestUrl(), queue);
                SnsClientUtil.publishToSns(queue);
                LogbackUtil.info(queue);
            } catch (Exception e) {
                logExceptionLeave(buildableItem, e);
            }
        }
    }

    private void logExceptionLeave(BuildableItem buildableItem, Exception e) {
        LOGGER.log(Level.WARNING, "JOb " + buildableItem.task.getFullDisplayName() +
                " with queue id " + buildableItem.getId() +
                "failed with exception : " + e);
    }

    private String getRestUrl() {
        return PropertyLoader.getQueueEndPoint();
    }

    private QueueStats getCiQueue(Item item) {
        QueueStats queue = new QueueStats();
        String ciUrl = Jenkins.getInstance() != null
                ? Jenkins.getInstance().getRootUrl()
                : "";
        queue.setCiUrl(ciUrl);
        queue.setJobName(item.task.getFullDisplayName());
        queue.setJenkinsQueueId((int) item.getId());
        queue.setEntryTime(new Date(item.getInQueueSince()));
        return queue;
    }

    private void addStartedBy(Item item, QueueStats queue) {
        List<Cause> causes = item.getCauses();
        for (Cause cause : causes) {
            if (cause instanceof Cause.UserIdCause) {
                queue.setStartedBy(((Cause.UserIdCause) cause).getUserName());
            } else if (cause instanceof Cause.UpstreamCause) {
                queue.setStartedBy(JenkinsCauses.UPSTREAM);
            } else if (cause instanceof SCMTrigger.SCMTriggerCause) {
                queue.setStartedBy(JenkinsCauses.SCM);
            } else if (cause instanceof TimerTrigger.TimerTriggerCause) {
                queue.setStartedBy(JenkinsCauses.TIMER);
            }
        }
    }


    @Override
    public void onLeft(LeftItem leftItem) {
        if (PropertyLoader.getQueueInfo()) {
            try {
                QueueStats queue = getCiQueue(leftItem);
                queue.setEntryTime(new Date(leftItem.getInQueueSince()));
                queue.setExitTime(new Date());
                queue.setStatus(Constants.LEFT);
                queue.setDuration(System.currentTimeMillis() - leftItem.getInQueueSince());
                if (leftItem.outcome != null) {
                    queue.setContextId(leftItem.outcome.hashCode());
                }
                RestClientUtil.postToService(getRestUrl(), queue);
                SnsClientUtil.publishToSns(queue);
                LogbackUtil.info(queue);
            } catch (Exception e) {
                logExceptionLeft(leftItem, e);
            }
        }
    }

    private void logExceptionLeft(LeftItem leftItem, Exception e) {
        LOGGER.log(Level.WARNING, "Failed to add Queue info for " +
                "job " + leftItem.task.getFullDisplayName() +
                " with queue id " + leftItem.getId() + " using " + getRestUrl(), e);
    }
}
