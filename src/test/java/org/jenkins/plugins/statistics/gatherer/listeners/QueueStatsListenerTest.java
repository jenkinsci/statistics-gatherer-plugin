package org.jenkins.plugins.statistics.gatherer.listeners;

import hudson.model.Action;
import hudson.model.LoadBalancer;
import hudson.model.Queue;
import org.jenkins.plugins.statistics.gatherer.model.queue.QueueStats;
import org.jenkins.plugins.statistics.gatherer.util.LogbackUtil;
import org.jenkins.plugins.statistics.gatherer.util.PropertyLoader;
import org.jenkins.plugins.statistics.gatherer.util.RestClientUtil;
import org.jenkins.plugins.statistics.gatherer.util.SnsClientUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jvnet.hudson.test.JenkinsRule;
import org.mockito.ArgumentCaptor;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PropertyLoader.class, RestClientUtil.class, SnsClientUtil.class, LogbackUtil.class})
@PowerMockIgnore({"javax.crypto.*"})
public class QueueStatsListenerTest {

    @Rule
    public JenkinsRule j = new JenkinsRule();

    private QueueStatsListener listener;

    @Before
    public void setup() {
        listener = new QueueStatsListener();
        mockStatic(PropertyLoader.class);
    }

    @Test
    public void givenOnEnterWaiting_thenPost() throws IOException {
        mockRestClientUtil();

        Queue.Task project = j.createFreeStyleProject("test");
        List<Action> actions = Collections.emptyList();
        Queue.WaitingItem waitingItem = new Queue.WaitingItem(Calendar.getInstance(), project, actions);

        listener.onEnterWaiting(waitingItem);

        verifyRestClientCall(project);
    }

    @Test
    public void givenOnLeaveWaiting_thenPost() throws IOException {
        mockRestClientUtil();

        Queue.Task project = j.createFreeStyleProject("test");
        List<Action> actions = Collections.emptyList();
        Queue.WaitingItem waitingItem = new Queue.WaitingItem(Calendar.getInstance(), project, actions);

        listener.onLeaveWaiting(waitingItem);

        verifyRestClientCall(project);
    }

    @Test
    public void givenOnEnterBlocked_thenPost() throws IOException {
        mockRestClientUtil();

        Queue.Task project = j.createFreeStyleProject("test");
        List<Action> actions = Collections.emptyList();
        Queue.WaitingItem item = new Queue.WaitingItem(Calendar.getInstance(), project, actions);
        Queue.BlockedItem blockedItem = new Queue(LoadBalancer.CONSISTENT_HASH).new BlockedItem(item);

        listener.onEnterBlocked(blockedItem);

        verifyRestClientCall(project);
    }

    @Test
    public void givenOnLeaveBlocked_thenPost() throws IOException {
        mockRestClientUtil();

        Queue.Task project = j.createFreeStyleProject("test");
        List<Action> actions = Collections.emptyList();
        Queue.WaitingItem item = new Queue.WaitingItem(Calendar.getInstance(), project, actions);
        Queue.BlockedItem blockedItem = new Queue(LoadBalancer.CONSISTENT_HASH).new BlockedItem(item);

        listener.onLeaveBlocked(blockedItem);

        verifyRestClientCall(project);
    }

    @Test
    public void givenOnEnterBuildable_thenPost() throws IOException {
        mockRestClientUtil();

        Queue.Task project = j.createFreeStyleProject("test");
        List<Action> actions = Collections.emptyList();
        Queue.WaitingItem item = new Queue.WaitingItem(Calendar.getInstance(), project, actions);
        Queue.BuildableItem buildableItem = new Queue.BuildableItem(item);

        listener.onLeaveBuildable(buildableItem);

        verifyRestClientCall(project);
    }

    @Test
    public void givenOnLeaveBuildable_thenPost() throws IOException {
        mockRestClientUtil();

        Queue.Task project = j.createFreeStyleProject("test");
        List<Action> actions = Collections.emptyList();
        Queue.WaitingItem item = new Queue.WaitingItem(Calendar.getInstance(), project, actions);
        Queue.BuildableItem buildableItem = new Queue.BuildableItem(item);

        listener.onLeaveBuildable(buildableItem);

        verifyRestClientCall(project);
    }

    @Test
    public void givenOnLeft_thenPost() throws IOException {
        mockRestClientUtil();

        Queue.Task project = j.createFreeStyleProject("test");
        List<Action> actions = Collections.emptyList();
        Queue.Item item = new Queue.WaitingItem(Calendar.getInstance(), project, actions);
        Queue.LeftItem leftItem = new Queue.LeftItem(item);

        listener.onLeft(leftItem);

        verifyRestClientCall(project);
    }

    private void mockRestClientUtil() {
        mockStatic(RestClientUtil.class);
        when(PropertyLoader.getQueueInfo()).thenReturn(true);
        when(PropertyLoader.getQueueEndPoint()).thenReturn("http://localhost");
    }

    private void verifyRestClientCall(Queue.Task project) {
        verifyStatic(RestClientUtil.class, times(1));
        ArgumentCaptor<QueueStats> captor = ArgumentCaptor.forClass(QueueStats.class);
        RestClientUtil.postToService(anyString(), captor.capture());
        QueueStats queueStats = captor.getAllValues().get(0);

        assertThat(queueStats.getJobName(), is(equalTo(project.getName())));
    }
}
