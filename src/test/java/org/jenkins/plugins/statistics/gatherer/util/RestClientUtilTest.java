package org.jenkins.plugins.statistics.gatherer.util;

import com.mashape.unirest.http.Unirest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Unirest.class, PropertyLoader.class})
public class RestClientUtilTest {

    @Before
    public void setup() {
        mockStatic(Unirest.class);
        mockStatic(PropertyLoader.class);
    }

    @Test
    public void whenRestApiFails_thenShouldContinue() throws Exception {
        when(Unirest.post(anyString())).thenThrow(new IllegalArgumentException("Some error occurred"));
        when(PropertyLoader.getShouldSendApiHttpRequests()).thenReturn(true);

        try {
            RestClientUtil.postToService("http://foo", new String("bar"));
        } catch (Throwable e) {
            fail("Should not have thrown any exception here");
        }
    }
}
