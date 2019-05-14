package org.jenkins.plugins.statistics.gatherer.util;

/**
 * Subsystem to send logs through LOGBack
 */
public interface Logback {

    /**
     * Define the LOGBack logger name to use
     *
     * @param loggerName the logger name to use
     * @return this instance
     */
    Logback setLoggerName(String loggerName);

    /**
     * Get the current LOGBack logger name
     *
     * @return logger name
     */
    String getLoggerName();

    /**
     * Send the message to LOGBack
     *
     * @param msg message to send to LOGBack
     */
    void log(String msg);

    /**
     * Check for LOGBack configuration and reload when changed.
     *
     * @return true if the configuration has been reloaded
     * @throws Exception if the configuration refresh failed
     */
    boolean refresh() throws Exception;

    /**
     * Return the SHA of the latest LOGBack configuration loaded.
     *
     * @return the URLSha of the last loaded configuration
     */
    URLSha getLastSha();
}
