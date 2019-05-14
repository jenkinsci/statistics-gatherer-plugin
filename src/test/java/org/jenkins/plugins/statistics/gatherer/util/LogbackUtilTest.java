package org.jenkins.plugins.statistics.gatherer.util;

import hudson.Plugin;
import jenkins.model.Jenkins;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Jenkins.class, LogbackFactory.class, PropertyLoader.class})
public class LogbackUtilTest {

    @Mock
    Jenkins jenkinsMock;

    @Mock
    Logback logbackMock;

    @Before
    public void setup() throws Exception {
        mockStatic(Jenkins.class);
        mockStatic(LogbackFactory.class);
        mockStatic(PropertyLoader.class);
        when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        when(PropertyLoader.getShouldSendToLogback()).thenReturn(true);
        when(LogbackFactory.create(anyString())).thenReturn(logbackMock);
    }

    @Test
    public void givenJenkinsWithoutLogback_whenLogging_thenDoNotCreateLogback() throws Exception {
        when(jenkinsMock.getPlugin(LogbackUtil.LOGBACK_PLUGIN_NAME)).thenReturn(null);

        new LogbackUtil().logInfo("foo");

        verifyZeroInteractions(LogbackFactory.class);
    }

    @Test
    public void givenJenkinsWithLogback_whenLogging_thenCreateLogback() throws Exception {
        Plugin pluginMock = mock(Plugin.class);
        when(jenkinsMock.getPlugin(LogbackUtil.LOGBACK_PLUGIN_NAME)).thenReturn(pluginMock);

        new LogbackUtil().logInfo("foo");

        verifyStatic(LogbackFactory.class);
        LogbackFactory.create(anyString());
    }
}
