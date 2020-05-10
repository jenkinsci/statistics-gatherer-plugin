package org.jenkins.plugins.statistics.gatherer.util;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsyncClient;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by alexgandy on 1/12/17.
 */
public class SqsClientUtil {

    private static final Logger LOGGER = Logger.getLogger(AmazonSQSAsyncClient.class.getName());
    private static AmazonSQS sqsClient;

    public static AmazonSQS getSqsClient() {
        if (sqsClient == null) {
            sqsClient = AmazonSQSClientBuilder.defaultClient();
        }
        return sqsClient;
    }

    public static void publishToSqs(Object object) {
        if (PropertyLoader.getShouldPublishToAwsSqsQueue()) {
            String jsonToPublish = JSONUtil.convertToJson(object);

            String sqsQueueURL = PropertyLoader.getSqsQueueURL();

            if (sqsQueueURL == null || sqsQueueURL.isEmpty()) {
                LOGGER.log(Level.WARNING, "Missing required SQS Queue URL");
                return;
            }

            AmazonSQS client = getSqsClient();
            SendMessageRequest send_msg_request = new SendMessageRequest()
                    .withQueueUrl(sqsQueueURL)
                    .withMessageBody(jsonToPublish)
                    .withDelaySeconds(5);
            client.sendMessage(send_msg_request);
        }
    }
}
/*
public void onError(Exception e) {
    LOGGER.log(Level.WARNING, e.getMessage(), e);
}

@Override
public void onSuccess(PublishRequest request, PublishResult publishResult) {
    LOGGER.log(Level.INFO, "Message ID: " + publishResult.getMessageId() + " successfully published");
}
*/
