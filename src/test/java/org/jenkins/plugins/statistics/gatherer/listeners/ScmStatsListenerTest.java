package org.jenkins.plugins.statistics.gatherer.listeners;

import hudson.FilePath;
import hudson.model.AbstractBuild;
import hudson.model.TaskListener;
import hudson.remoting.VirtualChannel;
import hudson.scm.SCM;
import hudson.scm.SCMRevisionState;
import org.jenkins.plugins.statistics.gatherer.model.scm.ScmCheckoutInfo;
import org.jenkins.plugins.statistics.gatherer.util.PropertyLoader;
import org.jenkins.plugins.statistics.gatherer.util.RestClientUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

/**
 * Created by mcharron on 2016-07-27.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({PropertyLoader.class, RestClientUtil.class})
public class ScmStatsListenerTest {

    private ScmStatsListener listener;

    @Before
    public void setup() {
        listener = new ScmStatsListener();
    }

    @Test
    public void givenBuildAndBuildInfoTrue_whenStarted_thenPost() {
        PowerMockito.mockStatic(PropertyLoader.class);
        Mockito.when(PropertyLoader.getScmCheckoutInfo()).thenReturn(true);
        Mockito.when(PropertyLoader.getScmCheckoutEndPoint()).thenReturn("http://localhost");
        AbstractBuild build = Mockito.mock(AbstractBuild.class);
        Mockito.when(build.getUrl()).thenReturn("aUrl");
        SCM scm = Mockito.mock(SCM.class);
        FilePath filePath = new FilePath(Mockito.mock(VirtualChannel.class), "");
        TaskListener taskListener = Mockito.mock(TaskListener.class);
        File file = Mockito.mock(File.class);
        SCMRevisionState scmRevisionState = Mockito.mock(SCMRevisionState.class);
        PowerMockito.mockStatic(RestClientUtil.class);
        ArgumentCaptor<ScmCheckoutInfo> scmCheckoutInfoArgumentCaptor = ArgumentCaptor.forClass(ScmCheckoutInfo.class);
        listener.onCheckout(build, scm, filePath, taskListener, file, scmRevisionState);

        PowerMockito.verifyStatic(RestClientUtil.class);
        RestClientUtil.postToService(anyString(), scmCheckoutInfoArgumentCaptor.capture());

        ScmCheckoutInfo buildStepStats = scmCheckoutInfoArgumentCaptor.getValue();
        assertEquals("aUrl", buildStepStats.getBuildUrl());
        assertEquals(new Date(0), buildStepStats.getStartTime());
    }

    @Test
    public void givenBuildAndBuildInfoFalse_whenStarted_thenDoNotPost() {
        PowerMockito.mockStatic(PropertyLoader.class);
        Mockito.when(PropertyLoader.getScmCheckoutInfo()).thenReturn(false);
        AbstractBuild build = Mockito.mock(AbstractBuild.class);
        SCM scm = Mockito.mock(SCM.class);
        FilePath filePath = new FilePath(Mockito.mock(VirtualChannel.class), "");
        TaskListener taskListener = Mockito.mock(TaskListener.class);
        File file = Mockito.mock(File.class);
        SCMRevisionState scmRevisionState = Mockito.mock(SCMRevisionState.class);
        listener.onCheckout(build, scm, filePath, taskListener, file, scmRevisionState);
        PowerMockito.mockStatic(RestClientUtil.class);
        PowerMockito.verifyZeroInteractions(RestClientUtil.class);
    }
}
