package org.jenkins.plugins.statistics.gatherer.listeners;

import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.tasks.BuildStep;
import org.jenkins.plugins.statistics.gatherer.model.step.BuildStepStats;
import org.jenkins.plugins.statistics.gatherer.util.LogbackUtil;
import org.jenkins.plugins.statistics.gatherer.util.PropertyLoader;
import org.jenkins.plugins.statistics.gatherer.util.RestClientUtil;
import org.jenkins.plugins.statistics.gatherer.util.SnsClientUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;


/**
 * Created by mcharron on 2016-07-27.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({PropertyLoader.class, RestClientUtil.class, SnsClientUtil.class, LogbackUtil.class})
public class BuildStepStatsListenerTest {

    private BuildStepStatsListener listener;

    @Before
    public void setup(){
        listener = new BuildStepStatsListener();
    }

    @Test
    public void givenBuildAndBuildInfoTrue_whenStarted_thenPost(){
        PowerMockito.mockStatic(PropertyLoader.class);
        Mockito.when(PropertyLoader.getBuildStepInfo()).thenReturn(true);
        Mockito.when(PropertyLoader.getBuildEndPoint()).thenReturn("");
        AbstractBuild build = Mockito.mock(AbstractBuild.class);
        Mockito.when(build.getUrl()).thenReturn("aUrl");
        BuildStep steps = Mockito.mock(BuildStep.class);
        BuildListener buildListener = Mockito.mock(BuildListener.class);
        PowerMockito.mockStatic(RestClientUtil.class);
        ArgumentCaptor<BuildStepStats> buildStepStatsArgumentCaptor =
                ArgumentCaptor.forClass(BuildStepStats.class);
        listener.started(build, steps, buildListener);

        PowerMockito.verifyStatic();
        RestClientUtil.postToService(anyString(), buildStepStatsArgumentCaptor.capture());

        BuildStepStats buildStepStats = buildStepStatsArgumentCaptor.getValue();
        assertEquals("aUrl", buildStepStats.getBuildUrl());
        assertEquals(new Date(0), buildStepStats.getEndTime());
    }

    @Test
    public void givenBuildAndBuildInfoFalse_whenStarted_thenDoNotPost(){
        PowerMockito.mockStatic(PropertyLoader.class);
        Mockito.when(PropertyLoader.getBuildStepInfo()).thenReturn(false);
        AbstractBuild build = Mockito.mock(AbstractBuild.class);
        BuildStep steps = Mockito.mock(BuildStep.class);
        BuildListener buildListener = Mockito.mock(BuildListener.class);
        listener.started(build, steps, buildListener);
        PowerMockito.mockStatic(RestClientUtil.class);
        PowerMockito.verifyStatic(Mockito.never());
    }

    @Test
    public void givenBuildAndBuildInfoTrue_whenFinished_thenPost(){
        PowerMockito.mockStatic(PropertyLoader.class);
        Mockito.when(PropertyLoader.getBuildStepInfo()).thenReturn(true);
        Mockito.when(PropertyLoader.getBuildEndPoint()).thenReturn("");
        AbstractBuild build = Mockito.mock(AbstractBuild.class);
        Mockito.when(build.getUrl()).thenReturn("aUrl");
        BuildStep steps = Mockito.mock(BuildStep.class);
        BuildListener buildListener = Mockito.mock(BuildListener.class);
        PowerMockito.mockStatic(RestClientUtil.class);
        ArgumentCaptor<BuildStepStats> buildStepStatsArgumentCaptor =
                ArgumentCaptor.forClass(BuildStepStats.class);
        listener.finished(build, steps, buildListener, true);

        PowerMockito.verifyStatic();
        RestClientUtil.postToService(anyString(), buildStepStatsArgumentCaptor.capture());

        BuildStepStats buildStepStats = buildStepStatsArgumentCaptor.getValue();
        assertEquals("aUrl", buildStepStats.getBuildUrl());
        assertEquals(new Date(0), buildStepStats.getStartTime());

    }

    @Test
    public void givenBuildAndBuildInfoTrue_whenFinished_thenPublishToSns(){
        PowerMockito.mockStatic(PropertyLoader.class);
        Mockito.when(PropertyLoader.getBuildStepInfo()).thenReturn(true);
        AbstractBuild build = Mockito.mock(AbstractBuild.class);
        Mockito.when(build.getUrl()).thenReturn("aUrl");
        BuildStep steps = Mockito.mock(BuildStep.class);
        BuildListener buildListener = Mockito.mock(BuildListener.class);
        PowerMockito.mockStatic(SnsClientUtil.class);
        ArgumentCaptor<BuildStepStats> buildStepStatsArgumentCaptor =
                ArgumentCaptor.forClass(BuildStepStats.class);
        listener.finished(build, steps, buildListener, true);

        PowerMockito.verifyStatic();
        SnsClientUtil.publishToSns(buildStepStatsArgumentCaptor.capture());

        BuildStepStats buildStepStats = buildStepStatsArgumentCaptor.getValue();
        assertEquals("aUrl", buildStepStats.getBuildUrl());
        assertEquals(new Date(0), buildStepStats.getStartTime());

    }

    @Test
    public void givenBuildAndBuildInfoFalse_whenFinished_thenDoNotPost(){
        PowerMockito.mockStatic(PropertyLoader.class);
        Mockito.when(PropertyLoader.getBuildStepInfo()).thenReturn(false);
        AbstractBuild build = Mockito.mock(AbstractBuild.class);
        BuildStep steps = Mockito.mock(BuildStep.class);
        BuildListener buildListener = Mockito.mock(BuildListener.class);
        listener.finished(build, steps, buildListener, true);
        PowerMockito.mockStatic(RestClientUtil.class);
        PowerMockito.verifyStatic(Mockito.never());
    }
}
