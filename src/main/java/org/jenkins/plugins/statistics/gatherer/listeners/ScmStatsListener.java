package org.jenkins.plugins.statistics.gatherer.listeners;

import hudson.Extension;
import hudson.FilePath;
import hudson.model.AbstractBuild;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.model.listeners.SCMListener;
import hudson.scm.SCM;
import hudson.scm.SCMRevisionState;
import org.jenkins.plugins.statistics.gatherer.model.scm.ScmCheckoutInfo;
import org.jenkins.plugins.statistics.gatherer.util.LogbackUtil;
import org.jenkins.plugins.statistics.gatherer.util.PropertyLoader;
import org.jenkins.plugins.statistics.gatherer.util.RestClientUtil;
import org.jenkins.plugins.statistics.gatherer.util.SnsClientUtil;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mcharron on 2016-07-15.
 */
@Extension
public class ScmStatsListener extends SCMListener{

    @Override
    public void onCheckout(Run<?,?> run, SCM scm, FilePath workspace, TaskListener listener, File changelogFile, SCMRevisionState pollingBaseline){
        if (PropertyLoader.getScmCheckoutInfo()){
            if (!(run instanceof AbstractBuild)) {
                return;
            }
            ScmCheckoutInfo scmCheckoutInfo = new ScmCheckoutInfo();
            scmCheckoutInfo.setBuildUrl(run.getUrl());
            scmCheckoutInfo.setStartTime(new Date(0));
            scmCheckoutInfo.setEndTime(Calendar.getInstance().getTime());
            RestClientUtil.postToService(getUrl(), scmCheckoutInfo);
            SnsClientUtil.publishToSns(scmCheckoutInfo);
            LogbackUtil.info(scmCheckoutInfo);
        }
    }

    private String getUrl(){
        return PropertyLoader.getScmCheckoutEndPoint();
    }
}
