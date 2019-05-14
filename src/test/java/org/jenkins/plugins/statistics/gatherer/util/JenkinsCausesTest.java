package org.jenkins.plugins.statistics.gatherer.util;

import org.junit.Test;

/**
 * Created by mcharron on 2016-07-27.
 */
public class JenkinsCausesTest {

    @Test(expected = IllegalAccessError.class)
    public void givenProtectedConstructor_whenNew_throwIllegalAccess() {
        new JenkinsCauses();
    }
}
