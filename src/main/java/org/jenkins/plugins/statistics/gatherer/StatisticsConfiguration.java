package org.jenkins.plugins.statistics.gatherer;

import com.amazonaws.regions.Region;
import hudson.Extension;
import hudson.util.FormValidation;
import jenkins.model.GlobalConfiguration;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import com.amazonaws.regions.RegionUtils;

/**
 * Created by hthakkallapally on 6/25/2015.
 */
@Extension
public class StatisticsConfiguration extends GlobalConfiguration {

    private static final String SLASH = "/";
    public static final String PROTOCOL_ERROR_MESSAGE = "Only http and https protocols are supported";

    private String queueUrl;
    private String buildUrl;
    private String projectUrl;
    private String scmCheckoutUrl;
    private String buildStepUrl;
    private String awsRegion;
    private String awsAccessKey;
    private String awsSecretKey;
    private String snsTopicArn;
    private String logbackConfigXmlUrl;

    private Boolean queueInfo;
    private Boolean buildInfo;
    private Boolean projectInfo;
    private Boolean scmCheckoutInfo;
    private Boolean buildStepInfo;
    private Boolean shouldSendApiHttpRequests;
    private Boolean shouldPublishToAwsSnsQueue;

    private Boolean shouldSendToLogback;

    public StatisticsConfiguration() {
        load();
    }

    public static StatisticsConfiguration get() {
        return GlobalConfiguration.all().get(StatisticsConfiguration.class);
    }

    public String getQueueUrl() {
        if (queueUrl != null && !queueUrl.isEmpty()) {
            if (queueUrl.endsWith(SLASH)) {
                return queueUrl;
            }
            return queueUrl + SLASH;
        }
        return queueUrl;
    }

    public Boolean getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(Boolean projectInfo) {
        this.projectInfo = projectInfo;
        save();
    }

    public Boolean getBuildInfo() {
        return buildInfo;
    }

    public void setBuildInfo(Boolean buildInfo) {
        this.buildInfo = buildInfo;
        save();
    }

    public Boolean getQueueInfo() {
        return queueInfo;
    }

    public void setQueueInfo(Boolean queueInfo) {
        this.queueInfo = queueInfo;
        save();
    }

    public Boolean getBuildStepInfo() {
        return buildStepInfo;
    }

    public void setBuildStepInfo(Boolean buildStepInfo) {
        this.buildStepInfo = buildStepInfo;
        save();
    }

    public Boolean getScmCheckoutInfo() {
        return scmCheckoutInfo;
    }

    public void setScmCheckoutInfo(Boolean scmCheckoutInfo) {
        this.scmCheckoutInfo = scmCheckoutInfo;
        save();
    }

    public void setQueueUrl(String queueUrl) {
        this.queueUrl = queueUrl;
        save();
    }

    public String getBuildUrl() {
        if (buildUrl != null && !buildUrl.isEmpty()) {
            if (buildUrl.endsWith(SLASH)) {
                return buildUrl;
            }
            return buildUrl + SLASH;
        }
        return buildUrl;
    }

    public void setBuildUrl(String buildUrl) {
        this.buildUrl = buildUrl;
        save();
    }

    public String getProjectUrl() {
        if (projectUrl != null && !projectUrl.isEmpty()) {
            if (projectUrl.endsWith(SLASH)) {
                return projectUrl;
            }
            return projectUrl + SLASH;
        }
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
        save();
    }

    public String getBuildStepUrl() {
        if (buildStepUrl != null && !buildStepUrl.isEmpty()) {
            if (buildStepUrl.endsWith(SLASH)) {
                return buildStepUrl;
            }
            return buildStepUrl + SLASH;
        }
        return buildStepUrl;
    }

    public void setBuildStepUrl(String buildStepUrl) {
        this.buildStepUrl = buildStepUrl;
        save();
    }

    public String getScmCheckoutUrl() {
        if (scmCheckoutUrl != null && !scmCheckoutUrl.isEmpty()) {
            if (scmCheckoutUrl.endsWith(SLASH)) {
                return scmCheckoutUrl;
            }
            return scmCheckoutUrl + SLASH;
        }
        return scmCheckoutUrl;
    }

    public void setScmCheckoutUrl(String scmCheckoutUrl) {
        this.scmCheckoutUrl = scmCheckoutUrl;
        save();
    }

    public String getAwsRegion() { return awsRegion; }

    public void setAwsRegion(String awsRegion) {
        this.awsRegion = awsRegion;
        save();
    }

    public String getAwsAccessKey() { return awsAccessKey; }

    public void setAwsAccessKey(String awsAccessKey) {
        this.awsAccessKey = awsAccessKey;
        save();
    }

    public String getAwsSecretKey() { return awsSecretKey; }

    public void setAwsSecretKey(String awsSecretKey) {
        this.awsSecretKey = awsSecretKey;
        save();
    }

    public String getSnsTopicArn() { return snsTopicArn; }

    public void setSnsTopicArn(String snsTopicArn) {
        this.snsTopicArn = snsTopicArn;
        save();
    }

    public Boolean getShouldSendApiHttpRequests() { return shouldSendApiHttpRequests; }

    public void setShouldSendApiHttpRequests(Boolean shouldSendApiHttpRequests) {
        this.shouldSendApiHttpRequests = shouldSendApiHttpRequests;
        save();
    }

    public Boolean getShouldPublishToAwsSnsQueue() { return shouldPublishToAwsSnsQueue; }

    public void setShouldPublishToAwsSnsQueue(Boolean shouldPublishToAwsSnsQueue) {
        this.shouldPublishToAwsSnsQueue = shouldPublishToAwsSnsQueue;
        save();
    }

    @Override
    public boolean configure(StaplerRequest request, JSONObject json) throws FormException {
        request.bindJSON(this, json);
        return true;
    }

    public FormValidation doCheckBuildUrl(
            @QueryParameter("buildUrl") final String buildUrl) {
        if (buildUrl == null || buildUrl.isEmpty()) {
            return FormValidation.error("Provide valid Build URL. " +
                    "For ex: \"http://ci.mycompany.com/api/builds\"");
        }
        if (validateProtocolUsed(buildUrl))
            return FormValidation.error(PROTOCOL_ERROR_MESSAGE);
        return FormValidation.ok();
    }

    public FormValidation doCheckQueueUrl(
            @QueryParameter("queueUrl") final String queueUrl) {
        if (queueUrl == null || queueUrl.isEmpty()) {
            return FormValidation.error("Provide valid Queue URL. " +
                    "For ex: \"http://ci.mycompany.com/api/queues\"");
        }
        if (validateProtocolUsed(queueUrl))
            return FormValidation.error(PROTOCOL_ERROR_MESSAGE);
        return FormValidation.ok();
    }

    public FormValidation doCheckProjectUrl(
            @QueryParameter("projectUrl") final String projectUrl) {
        if (projectUrl == null || projectUrl.isEmpty()) {
            return FormValidation.error("Provide valid Project URL. " +
                    "For ex: \"http://ci.mycompany.com/api/\"");
        }
        if (validateProtocolUsed(projectUrl))
            return FormValidation.error(PROTOCOL_ERROR_MESSAGE);
        return FormValidation.ok();
    }

    public FormValidation doCheckBuildStepUrl(
            @QueryParameter("buildStepUrl") final String buildStepUrl) {
        if (buildStepUrl == null || buildStepUrl.isEmpty()) {
            return FormValidation.error("Provide valid BuildStep URL. " +
                    "For ex: \"http://ci.mycompany.com/api/steps\"");
        }
        if (validateProtocolUsed(buildStepUrl))
            return FormValidation.error(PROTOCOL_ERROR_MESSAGE);
        return FormValidation.ok();
    }

    public FormValidation doCheckScmCheckoutUrl(
            @QueryParameter("scmCheckoutUrl") final String scmCheckoutUrl) {
        if (scmCheckoutUrl == null || scmCheckoutUrl.isEmpty()) {
            return FormValidation.error("Provide valid BuildStep URL. " +
                    "For ex: \"http://ci.mycompany.com/api/checkout\"");
        }
        if (validateProtocolUsed(scmCheckoutUrl))
            return FormValidation.error(PROTOCOL_ERROR_MESSAGE);
        return FormValidation.ok();
    }

    public FormValidation doCheckBuildInfo(
            @QueryParameter("buildInfo") final Boolean buildInfo) {
        if (buildInfo == null) {
            return FormValidation.error("Provide valid Build Info. ");
        }
        return FormValidation.ok();
    }

    public FormValidation doCheckQueueInfo(
            @QueryParameter("queueInfo") final Boolean queueInfo) {
        if (queueInfo == null) {
            return FormValidation.error("Provide valid Queue Info. ");
        }
        return FormValidation.ok();
    }

    public FormValidation doCheckProjectInfo(
            @QueryParameter("projectInfo") final Boolean projectInfo) {
        if (projectInfo == null) {
            return FormValidation.error("Provide valid Project Info. ");
        }
        return FormValidation.ok();
    }

    public FormValidation doCheckBuildStepInfo(
            @QueryParameter("buildStepInfo") final Boolean buildStepInfo) {
        if (buildStepInfo == null) {
            return FormValidation.error("Provide valid BuildStepInfo. ");
        }
        return FormValidation.ok();
    }

    public FormValidation doCheckScmCheckoutInfo(
            @QueryParameter("scmCheckoutInfo") final Boolean scmCheckoutInfo) {
        if (scmCheckoutInfo == null) {
            return FormValidation.error("Provide valid CcmCheckoutInfo. ");
        }
        return FormValidation.ok();
    }

    public FormValidation doCheckAwsRegion(
        @QueryParameter("awsRegion") final String awsRegion) {
        if (shouldPublishToAwsSnsQueue == null ||shouldPublishToAwsSnsQueue ) {
            if (awsRegion == null) {
                return FormValidation.error("AWS Region required. ");
            }

            Region r = RegionUtils.getRegion(awsRegion);
            if (r == null || !r.isServiceSupported("sns")) {
                return FormValidation.error("Please enter a valid SNS AWS region. ");
            }
        }

        return FormValidation.ok();
    }

    public FormValidation doCheckSnsTopicArn(
            @QueryParameter("snsTopicArn") final String snsTopicArn) {
        if (shouldPublishToAwsSnsQueue == null ||shouldPublishToAwsSnsQueue ) {
            if (snsTopicArn == null || snsTopicArn.isEmpty()) {
                return FormValidation.error("SNS ARN required. ");
            }
        }

        return FormValidation.ok();
    }

    public FormValidation doCheckAwsAccessKey(
            @QueryParameter("awsAccessKey") final String awsAccessKey) {
        if (shouldPublishToAwsSnsQueue == null ||shouldPublishToAwsSnsQueue ) {
            if (awsAccessKey == null || awsAccessKey.isEmpty()) {
                return FormValidation.error("AWS Access Key required. ");
            }
        }

        return FormValidation.ok();
    }

    public FormValidation doCheckAwsSecretKey(
            @QueryParameter("awsSecretKey") final String awsSecretKey) {
        if (shouldPublishToAwsSnsQueue == null ||shouldPublishToAwsSnsQueue ) {
            if (awsSecretKey == null || awsSecretKey.isEmpty()) {
                return FormValidation.error("AWS Secret Key required. ");
            }
        }

        return FormValidation.ok();
    }

    private boolean validateProtocolUsed(String url) {
        return !(url.startsWith("http://") || url.startsWith("https://"));
    }

    public Boolean getShouldSendToLogback() {
        return shouldSendToLogback;
    }

    public void setShouldSendToLogback(Boolean shouldSendToLogback) {
        this.shouldSendToLogback = shouldSendToLogback;
    }

    public String getLogbackConfigXmlUrl() {
        return logbackConfigXmlUrl;
    }

    public void setLogbackConfigXmlUrl(String logbackConfigXmlUrl) {
        this.logbackConfigXmlUrl = logbackConfigXmlUrl;
    }
}
