package org.jenkins.plugins.statistics.gatherer.util;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogbackFactory {

    private static final Logger logger = Logger.getLogger(LogbackFactory.class.getName());
    private static final int REFRESH_THREADS = 1;
    private static final int REFRESH_INTERVAL_SECS = 60;
    private static final ScheduledExecutorService refresher = Executors.newScheduledThreadPool(REFRESH_THREADS);
    private static final Set<Logback> activeLogbacks = new HashSet<>();
    private static ScheduledFuture<?> refresherTask;

    public static synchronized Logback create(String loggerName) throws Exception {
        Class<Logback> logback = (Class<Logback>) Class.forName(Logback.class.getName() + "Impl");
        Logback logbackInstance = logback.newInstance().setLoggerName(loggerName);

        activeLogbacks.add(logbackInstance);

        if (refresherTask != null && !refresherTask.isCancelled()) {
            refresherTask.cancel(true);
        }

        refresherTask = refresher.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                for (Logback logback : activeLogbacks) {
                    try {
                        logback.refresh();
                    } catch (Exception e) {
                        logger.log(Level.WARNING, "Unable to refresh LOGBack " + logback, e);
                    }
                }
            }
        }, REFRESH_INTERVAL_SECS, REFRESH_INTERVAL_SECS, TimeUnit.SECONDS);

        return logbackInstance;
    }
}
