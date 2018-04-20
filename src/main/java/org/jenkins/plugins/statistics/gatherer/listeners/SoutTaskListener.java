package org.jenkins.plugins.statistics.gatherer.listeners;

import hudson.console.ConsoleNote;
import hudson.model.TaskListener;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

public class SoutTaskListener implements TaskListener {
    private static PrintWriter soutWriter = new PrintWriter(System.out);

    public static final SoutTaskListener INSTANCE = new SoutTaskListener();

    @Override
    public PrintStream getLogger() {
        return System.out;
    }

    @Override
    public void annotate(ConsoleNote ann) throws IOException {

    }

    @Override
    public void hyperlink(String url, String text) throws IOException {

    }

    @Override
    public PrintWriter error(String msg) {
        return soutWriter;
    }

    @Override
    public PrintWriter error(String format, Object... args) {
        return soutWriter;
    }

    @Override
    public PrintWriter fatalError(String msg) {
        return soutWriter;
    }

    @Override
    public PrintWriter fatalError(String format, Object... args) {
        return soutWriter;
    }
}
