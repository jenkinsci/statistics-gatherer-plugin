package org.jenkins.plugins.statistics.gatherer.util;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.sns.AmazonSNSAsyncClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by alexgandy on 1/12/17.
 */
public class SnsClientUtil {

    private static final Logger LOGGER = Logger.getLogger(SnsClientUtil.class.getName());
    private static AmazonSNSAsyncClient snsClient;

    public static AmazonSNSAsyncClient getSnsClient() {
        if (snsClient == null) {
            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(PropertyLoader.getAwsAccessKey(), PropertyLoader.getAwsSecretKey());
            AmazonSNSAsyncClient snsClient = new AmazonSNSAsyncClient(awsCredentials);

            Region snsRegion = RegionUtils.getRegion(PropertyLoader.getAwsRegion());
            snsClient.setRegion(snsRegion);

            SnsClientUtil.snsClient = snsClient;
        }

        return snsClient;
    }

    public static void publishToSns(Object object) {
        if (PropertyLoader.getShouldPublishToAwsSnsQueue()) {
            String jsonToPublish = JSONUtil.convertToJson(object);

            PublishRequest pr = new PublishRequest();
            String snsTopicArn = PropertyLoader.getSnsTopicArn();

            if (snsTopicArn == null || snsTopicArn.isEmpty()) {
                LOGGER.log(Level.WARNING, "Missing required SNS Topic ARN");
                return;
            }

            pr.setTopicArn(PropertyLoader.getSnsTopicArn());
            pr.setMessage(jsonToPublish);

            AmazonSNSAsyncClient client = getSnsClient();
            client.publishAsync(pr, new AsyncHandler<PublishRequest, PublishResult>() {
                @Override
                public void onError(Exception e) {
                    LOGGER.log(Level.WARNING, e.getMessage(), e);
                }

                @Override
                public void onSuccess(PublishRequest request, PublishResult publishResult) {
                    LOGGER.log(Level.INFO, "Message ID: " + publishResult.getMessageId() + " successfully published");
                }
            });
        }
    }
}
