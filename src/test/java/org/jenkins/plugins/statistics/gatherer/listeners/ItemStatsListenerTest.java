package org.jenkins.plugins.statistics.gatherer.listeners;

import hudson.model.FreeStyleProject;
import org.jenkins.plugins.statistics.gatherer.model.job.JobStats;
import org.jenkins.plugins.statistics.gatherer.util.LogbackUtil;
import org.jenkins.plugins.statistics.gatherer.util.PropertyLoader;
import org.jenkins.plugins.statistics.gatherer.util.RestClientUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jvnet.hudson.test.JenkinsRule;
import org.mockito.ArgumentCaptor;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PropertyLoader.class, RestClientUtil.class, LogbackUtil.class})
@PowerMockIgnore({"javax.crypto.*"})
public class ItemStatsListenerTest {

    @Rule
    public JenkinsRule jenkinsRule = new JenkinsRule();

    private ItemStatsListener listener;

    @Before
    public void setup() {
        listener = new ItemStatsListener();
        mockStatic(PropertyLoader.class);
    }

    @Test
    public void whenItemCreated_thenPost() throws Exception {
        mockStatic(RestClientUtil.class);
        when(PropertyLoader.getProjectInfo()).thenReturn(true);
        when(PropertyLoader.getProjectEndPoint()).thenReturn("http://localhost");

        FreeStyleProject project = jenkinsRule.createFreeStyleProject();

        verifyStatic(RestClientUtil.class);
        ArgumentCaptor<JobStats> captor = ArgumentCaptor.forClass(JobStats.class);
        RestClientUtil.postToService(anyString(), captor.capture());
        JobStats jobStats = captor.getValue();

        assertThat(jobStats.getName(), startsWith("test"));
        assertThat(jobStats.getJobUrl(), startsWith("job/test"));
        assertThat(jobStats.getStatus(), equalTo("ACTIVE"));
    }

    @Test
    public void whenItemDeleted_thenPost() throws Exception {
        mockStatic(RestClientUtil.class);
        when(PropertyLoader.getProjectInfo()).thenReturn(true);
        when(PropertyLoader.getProjectEndPoint()).thenReturn("http://localhost");

        FreeStyleProject project = jenkinsRule.createFreeStyleProject();
        project.delete();

        verifyStatic(RestClientUtil.class, times(3));
        ArgumentCaptor<JobStats> captor = ArgumentCaptor.forClass(JobStats.class);
        RestClientUtil.postToService(anyString(), captor.capture());
        List<JobStats> jobStats = captor.getAllValues();

        JobStats disabled = jobStats.get(1);
        JobStats deleted = jobStats.get(2);

        assertThat(disabled.getName(), startsWith("test"));
        assertThat(disabled.getJobUrl(), startsWith("job/test"));
        assertThat(disabled.getStatus(), equalTo("DISABLED"));

        assertThat(deleted.getName(), startsWith("test"));
        assertThat(deleted.getJobUrl(), startsWith("job/test"));
        assertThat(deleted.getStatus(), equalTo("DELETED"));
    }
}
