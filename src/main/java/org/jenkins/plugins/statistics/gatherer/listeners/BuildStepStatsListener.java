package org.jenkins.plugins.statistics.gatherer.listeners;

import hudson.Extension;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.BuildStepListener;
import hudson.tasks.BuildStep;
import jenkins.YesNoMaybe;
import org.jenkins.plugins.statistics.gatherer.model.step.BuildStepStats;
import org.jenkins.plugins.statistics.gatherer.util.*;

import java.util.Date;

/**
 * Created by mcharron on 2016-07-12.
 */
@Extension(dynamicLoadable = YesNoMaybe.YES)
public class BuildStepStatsListener extends BuildStepListener {

    @Override
    public void finished(AbstractBuild build, BuildStep bs, BuildListener listener, boolean canContinue) {
        if (PropertyLoader.getBuildStepInfo()) {
            BuildStepStats buildStepStats = new BuildStepStats();
            buildStepStats.setBuildUrl(build.getUrl());
            buildStepStats.setBuildStepType(bs.getClass().toString().replace("class ", ""));
            buildStepStats.setBuildStepId(bs.toString());
            buildStepStats.setEndTime(new Date());
            buildStepStats.setStartTime(new Date(0));
            RestClientUtil.postToService(getRestUrl(), buildStepStats);
            SnsClientUtil.publishToSns(buildStepStats);
            SqsClientUtil.publishToSqs(buildStepStats);
            LogbackUtil.info(buildStepStats);
        }
    }

    @Override
    public void started(AbstractBuild build, BuildStep bs, BuildListener listener) {
        if (PropertyLoader.getBuildStepInfo()) {
            BuildStepStats buildStepStats = new BuildStepStats();
            buildStepStats.setBuildUrl(build.getUrl());
            buildStepStats.setBuildStepType(bs.getClass().toString().replace("class ", ""));
            buildStepStats.setBuildStepId(bs.toString());
            buildStepStats.setStartTime(new Date());
            buildStepStats.setEndTime(new Date(0));
            RestClientUtil.postToService(getRestUrl(), buildStepStats);
            SnsClientUtil.publishToSns(buildStepStats);
            SqsClientUtil.publishToSqs(buildStepStats);
            LogbackUtil.info(buildStepStats);
        }
    }

    private String getRestUrl() {
        return PropertyLoader.getBuildStepEndPoint();
    }
}
