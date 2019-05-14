package org.jenkins.plugins.statistics.gatherer.util;

/**
 * Created by hthakkallapally on 4/6/2015.
 */
public final class JenkinsCauses {

    public static final String UPSTREAM = "UPSTREAM";
    public static final String SCM = "SCM";
    public static final String TIMER = "TIMER";

    protected JenkinsCauses() {
        throw new IllegalAccessError("Utility class");
    }
}
