package org.jenkins.plugins.statistics.gatherer.util;

import jenkins.model.Jenkins;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogbackUtil {

    public static final String STATISTICS_GATHERER_LOGGER = "statistics-gatherer";
    public static final String LOGBACK_PLUGIN_NAME = "logback-nats-appender";
    public static final LogbackUtil INSTANCE = new LogbackUtil();

    private static Logger logger = Logger.getLogger(LogbackUtil.class.getName());

    private Logback logback;

    public static void info(Object object) {
        INSTANCE.logInfo(object);
    }

    public void logInfo(Object object) {
        if (PropertyLoader.getShouldSendToLogback() && isLogbackAvailable()) {
            try {
                if (logback == null) {
                    logback = LogbackFactory.create(STATISTICS_GATHERER_LOGGER);
                }
                logback.log(JSONUtil.convertToJson(object));
            } catch (Exception e) {
                logger.log(Level.WARNING, "Unable to find a valid implementation of Logback", e);
            }
        }
    }

    public static boolean isLogbackAvailable() {
        return Jenkins.getInstance().getPlugin(LOGBACK_PLUGIN_NAME) != null;
    }

    public Logback getLogback() {
        return logback;
    }
}
