package org.jenkins.plugins.statistics.gatherer.util;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.util.ContextInitializer;
import ch.qos.logback.core.joran.spi.JoranException;

import java.net.MalformedURLException;
import java.net.URL;

public class LogbackUtil {
    private static final String STATISTICS_GATHERER_LOGGER = "statistics-gatherer";
    private static Logger log;

    private static Logger getLogger(String loggerName) {
        LoggerContext loggerContext = new LoggerContext();
        ContextInitializer contextInitializer = new ContextInitializer(loggerContext);
        try {
            String configurationUrlString = PropertyLoader.getLogbackConfigXmlUrl();
            if (configurationUrlString == null) {
                throw new IllegalStateException("LOGBack XML configuration file not specified");
            }

            URL configurationUrl = new URL(configurationUrlString);
            contextInitializer.configureByResource(configurationUrl);
            return loggerContext.getLogger(loggerName);
        } catch (JoranException e) {
            throw new RuntimeException("Unable to configure logger", e);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Unable to find LOGBack XML configuration", e);
        }
    }

    public static void info(Object object) {
        if (PropertyLoader.getShouldSendToLogback()) {
            if(log == null) {
                synchronized (LogbackUtil.class) {
                    log = getLogger(STATISTICS_GATHERER_LOGGER);
                }
            }
            log.info(JSONUtil.convertToJson(object));
        }
    }
}
