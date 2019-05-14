package org.jenkins.plugins.statistics.gatherer.util;

import hudson.Plugin;
import jenkins.model.Jenkins;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.net.MalformedURLException;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PropertyLoader.class, Jenkins.class})
public class LogbackImplTest extends LogbackAbstractTest {

    @Mock
    Jenkins jenkinsMock;

    String logbackXml;

    @Before
    public void setup() throws IOException {
        mockStatic(PropertyLoader.class);
        mockStatic(Jenkins.class);

        logbackXml = newSimpleLogbackXmlUrl();
    }

    @Test
    public void givenLogbackAppender_whenLogbackIsConfigured_thenLoggerIsCreated() throws MalformedURLException {
        when(PropertyLoader.getLogbackConfigXmlUrl()).thenReturn(logbackXml);
        when(PropertyLoader.getShouldSendToLogback()).thenReturn(true);
        when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        Plugin pluginMock = mock(Plugin.class);
        when(jenkinsMock.getPlugin(LogbackUtil.LOGBACK_PLUGIN_NAME)).thenReturn(pluginMock);


        LogbackUtil logbackUtil = new LogbackUtil();
        logbackUtil.logInfo("something");

        Logback logback = logbackUtil.getLogback();
        assertThat(logback, is(notNullValue()));
        assertThat(logback.getClass().getName(), is(LogbackImpl.class.getName()));

        LogbackImpl logbackImpl = ((LogbackImpl) logback);
        assertThat(logbackImpl.getLogger(), is(notNullValue()));
    }

    @Test
    public void givenLogbackAppender_whenLogbackIsEnabledButNotConfigured_thenLoggerIsNotCreated() throws MalformedURLException {
        when(PropertyLoader.getLogbackConfigXmlUrl()).thenReturn("");
        when(PropertyLoader.getShouldSendToLogback()).thenReturn(true);
        when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        Plugin pluginMock = mock(Plugin.class);
        when(jenkinsMock.getPlugin(LogbackUtil.LOGBACK_PLUGIN_NAME)).thenReturn(pluginMock);


        LogbackUtil logbackUtil = new LogbackUtil();
        logbackUtil.logInfo("something");

        Logback logback = logbackUtil.getLogback();
        assertThat(logback, is(nullValue()));
    }
}
