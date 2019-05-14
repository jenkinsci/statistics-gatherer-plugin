package org.jenkins.plugins.statistics.gatherer.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PropertyLoader.class})
public class LogbackFactoryTest extends LogbackAbstractTest {

    Path logbackXmlPath;

    @Before
    public void setup() throws IOException {
        mockStatic(PropertyLoader.class);
        logbackXmlPath = newSimpleLogbackXmlPath();
        Files.write(logbackXmlPath, SIMPLE_LOGBACK_XML.getBytes());
        when(PropertyLoader.getLogbackConfigXmlUrl()).thenReturn(logbackXmlPath.toUri().toURL().toString());
        when(PropertyLoader.getShouldSendToLogback()).thenReturn(true);
    }

    @Test
    public void givenFactory_whenCreating_thenNewLogbackIsCreated() throws Exception {
        Logback logback = LogbackFactory.create("fooLogger");

        assertThat(logback, is(notNullValue()));
        assertThat(logback.getLoggerName(), is("fooLogger"));
    }

    @Test
    public void givenFactory_whenChangingLogbackXML_thenShouldReload() throws Exception {
        Logback logback = LogbackFactory.create("foo");
        URLSha initialSha = logback.getLastSha();

        Files.write(logbackXmlPath, (SIMPLE_LOGBACK_XML + "\n\t").getBytes());
        logback.refresh();

        assertThat(logback.getLastSha(), is(not(equalTo(initialSha))));
    }

    @Test
    public void givenFactory_whenRefreshing_thenShouldNotReload() throws Exception {
        Logback logback = LogbackFactory.create("foo");
        URLSha initialSha = logback.getLastSha();

        logback.refresh();

        assertThat(logback.getLastSha(), is(equalTo(initialSha)));
    }
}
