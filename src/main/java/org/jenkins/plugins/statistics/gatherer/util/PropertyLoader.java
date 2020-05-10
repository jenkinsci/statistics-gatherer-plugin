package org.jenkins.plugins.statistics.gatherer.util;

import com.google.common.base.Strings;
import hudson.EnvVars;
import hudson.slaves.EnvironmentVariablesNodeProperty;
import hudson.slaves.NodeProperty;
import hudson.slaves.NodePropertyDescriptor;
import hudson.util.DescribableList;
import jenkins.model.Jenkins;
import org.jenkins.plugins.statistics.gatherer.StatisticsConfiguration;

import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class PropertyLoader {
    public static final String DEFAULT_PROPERTY_FILE_NAME = "statistics";
    private static PropertyLoader instance = null;
    private final ResourceBundle resourceBundle;

    private PropertyLoader() {
        resourceBundle = ResourceBundle.getBundle(DEFAULT_PROPERTY_FILE_NAME);
    }

    public static final synchronized PropertyLoader getInstance() {
        if (instance == null) {
            setInstance(new PropertyLoader());
        }
        return instance;
    }

    public static final synchronized void setInstance(
            final PropertyLoader propertyLoader) {
        instance = propertyLoader;
    }

    protected String getResourceBundleProperty(String keyProperty) {
        try {
            return resourceBundle.getString(keyProperty);
        } catch (MissingResourceException e) {
            return null;
        }
    }

    public String getProperty(
            final String inKey) {
        if (inKey == null) {
            return null;
        }
        final String key = inKey.trim();
        if (key.length() <= 0) {
            return null;
        }
        final EnvVars environmentVariables = new EnvVars();
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> globalNodeProperties =
                Jenkins.getInstance().getGlobalNodeProperties();
        List<EnvironmentVariablesNodeProperty> properties =
                globalNodeProperties.getAll(EnvironmentVariablesNodeProperty.class);
        for (EnvironmentVariablesNodeProperty environmentVariablesNodeProperty : properties) {
            environmentVariables.putAll(environmentVariablesNodeProperty.getEnvVars());
        }
        String value = environmentVariables.get(key);
        if (!Strings.isNullOrEmpty(value)) {
            return value;
        }
        value = environmentVariables.get(key.toLowerCase());
        if (!Strings.isNullOrEmpty(value)) {
            return value;
        }

        value = getResourceBundleProperty(key);
        if (!Strings.isNullOrEmpty(value)) {
            return value;
        }
        return getResourceBundleProperty(key.toLowerCase());
    }

    public static String getEnvironmentProperty(
            final String key) {
        return getInstance().getProperty(key);
    }

    public static boolean isTrue(String value) {
        return value != null ? value.toLowerCase().equals("true") : false;
    }

    public static String getQueueEndPoint() {
        String endPoint = StatisticsConfiguration.get().getQueueUrl();
        if (endPoint != null && !endPoint.isEmpty()) {
            return endPoint;
        }
        endPoint = getEnvironmentProperty("statistics.endpoint.queueUrl");
        return endPoint == null ? "" : endPoint;
    }

    public static String getBuildEndPoint() {
        String endPoint = StatisticsConfiguration.get().getBuildUrl();
        if (endPoint != null && !endPoint.isEmpty()) {
            return endPoint;
        }
        endPoint = getEnvironmentProperty("statistics.endpoint.buildUrl");
        return endPoint == null ? "" : endPoint;
    }

    public static String getProjectEndPoint() {
        String endPoint = StatisticsConfiguration.get().getProjectUrl();
        if (endPoint != null && !endPoint.isEmpty()) {
            return endPoint;
        }
        endPoint = getEnvironmentProperty("statistics.endpoint.projectUrl");
        return endPoint == null ? "" : endPoint;
    }

    public static String getBuildStepEndPoint() {
        String endPoint = StatisticsConfiguration.get().getBuildStepUrl();
        if (endPoint != null && !endPoint.isEmpty()) {
            return endPoint;
        }
        endPoint = getEnvironmentProperty("statistics.endpoint.buildStepUrl");
        return endPoint == null ? "" : endPoint;
    }

    public static String getScmCheckoutEndPoint() {
        String endPoint = StatisticsConfiguration.get().getScmCheckoutUrl();
        if (endPoint != null && !endPoint.isEmpty()) {
            return endPoint;
        }
        endPoint = getEnvironmentProperty("statistics.endpoint.scmCheckoutUrl");
        return endPoint == null ? "" : endPoint;
    }

    public static String getAwsRegion() {
        String awsRegion = StatisticsConfiguration.get().getAwsRegion();
        if (awsRegion != null && !awsRegion.isEmpty()) {
            return awsRegion;
        }
        awsRegion = getEnvironmentProperty("statistics.endpoint.awsRegion");
        return awsRegion == null ? "" : awsRegion;
    }

    public static String getAwsAccessKey() {
        String awsAccessKey = StatisticsConfiguration.get().getAwsAccessKey();
        if (awsAccessKey != null && !awsAccessKey.isEmpty()) {
            return awsAccessKey;
        }
        awsAccessKey = getEnvironmentProperty("statistics.endpoint.awsAccessKey");
        return awsAccessKey == null ? "" : awsAccessKey;
    }

    public static String getAwsSecretKey() {
        String awsSecretKey = StatisticsConfiguration.get().getAwsSecretKey();
        if (awsSecretKey != null && !awsSecretKey.isEmpty()) {
            return awsSecretKey;
        }
        awsSecretKey = getEnvironmentProperty("statistics.endpoint.awsSecretKey");
        return awsSecretKey == null ? "" : awsSecretKey;
    }

    public static String getSnsTopicArn() {
        String snsTopicArn = StatisticsConfiguration.get().getSnsTopicArn();
        if (snsTopicArn != null && !snsTopicArn.isEmpty()) {
            return snsTopicArn;
        }
        snsTopicArn = getEnvironmentProperty("statistics.endpoint.snsTopicArn");
        return snsTopicArn == null ? "" : snsTopicArn;
    }

    public static String getSqsQueueURL() {
        String sqsURL = StatisticsConfiguration.get().getSqsURL();
        if (sqsURL != null && !sqsURL.isEmpty()) {
            return sqsURL;
        }
        sqsURL = getEnvironmentProperty("statistics.endpoint.snsQueueURL");
        return sqsURL == null ? "" : sqsURL;
    }

    public static Boolean getQueueInfo() {
        Boolean queueInfo = StatisticsConfiguration.get().getQueueInfo();
        if (queueInfo != null) {
            return queueInfo;
        }
        String queueInfoEnv = getEnvironmentProperty("statistics.endpoint.queueInfo");
        return isTrue(queueInfoEnv);
    }

    public static Boolean getBuildInfo() {
        Boolean buildInfo = StatisticsConfiguration.get().getBuildInfo();
        if (buildInfo != null) {
            return buildInfo;
        }
        return isTrue(getEnvironmentProperty("statistics.endpoint.buildInfo"));
    }

    public static Boolean getProjectInfo() {
        Boolean projectInfo = StatisticsConfiguration.get().getProjectInfo();
        if (projectInfo != null) {
            return projectInfo;
        }
        return isTrue(getEnvironmentProperty("statistics.endpoint.projectInfo"));
    }

    public static Boolean getBuildStepInfo() {
        Boolean buildStepInfo = StatisticsConfiguration.get().getBuildStepInfo();
        if (buildStepInfo != null) {
            return buildStepInfo;
        }
        return isTrue(getEnvironmentProperty("statistics.endpoint.buildStepInfo"));
    }

    public static Boolean getScmCheckoutInfo() {
        Boolean scmCheckoutInfo = StatisticsConfiguration.get().getScmCheckoutInfo();
        if (scmCheckoutInfo != null) {
            return scmCheckoutInfo;
        }
        return isTrue(getEnvironmentProperty("statistics.endpoint.scmCheckoutInfo"));
    }

    public static Boolean getShouldSendApiHttpRequests() {
        Boolean shouldSendApiHttpRequests = StatisticsConfiguration.get().getShouldSendApiHttpRequests();
        if (shouldSendApiHttpRequests != null) {
            return shouldSendApiHttpRequests;
        }
        return isTrue(getEnvironmentProperty("statistics.endpoint.shouldSendApiHttpRequests"));
    }

    public static Boolean getShouldPublishToAwsSnsQueue() {
        Boolean shouldPublishToAwsSnsQueue = StatisticsConfiguration.get().getShouldPublishToAwsSnsQueue();
        if (shouldPublishToAwsSnsQueue != null) {
            return shouldPublishToAwsSnsQueue;
        }
        return isTrue(getEnvironmentProperty("statistics.endpoint.shouldPublishToAwsSnsQueue"));
    }

    public static Boolean getShouldPublishToAwsSqsQueue() {
        Boolean shouldPublishToAwsSqsQueue = StatisticsConfiguration.get().getShouldPublishToAwsSqsQueue();
        if (shouldPublishToAwsSqsQueue != null) {
            return shouldPublishToAwsSqsQueue;
        }
        return isTrue(getEnvironmentProperty("statistics.endpoint.shouldPublishToAwsSqsQueue"));
    }

    public static boolean getShouldSendToLogback() {
        Boolean shouldSendToLogback = StatisticsConfiguration.get().getShouldSendToLogback();
        if (shouldSendToLogback != null) {
            return shouldSendToLogback;
        }
        return isTrue(getEnvironmentProperty("statistics.endpoint.shouldSendToLogback"));
    }

    public static String getLogbackConfigXmlUrl() {
        String logbackConfigXmlUrlString = StatisticsConfiguration.get().getLogbackConfigXmlUrl();
        if (logbackConfigXmlUrlString == null) {
            logbackConfigXmlUrlString = getEnvironmentProperty("statistics.endpoint.logbackConfigXmlUrl");
        }
        return logbackConfigXmlUrlString;
    }
}