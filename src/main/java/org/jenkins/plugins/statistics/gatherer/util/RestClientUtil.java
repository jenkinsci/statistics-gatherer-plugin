package org.jenkins.plugins.statistics.gatherer.util;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RestClientUtil {

    private static final Logger LOGGER = Logger.getLogger(RestClientUtil.class.getName());
    public static final String APPLICATION_JSON = "application/json";
    public static final String ACCEPT = "accept";
    public static final String CONTENT_TYPE = "Content-Type";

    protected RestClientUtil() {
        throw new IllegalAccessError("Utility class");
    }

    public static void postToService(final String url, Object object) {
        if (PropertyLoader.getShouldSendApiHttpRequests()) {
            try {
                String jsonToPost = JSONUtil.convertToJson(object);
                Unirest.post(url)
                        .header(ACCEPT, APPLICATION_JSON)
                        .header(CONTENT_TYPE, APPLICATION_JSON)
                        .body(jsonToPost)
                        .asJsonAsync(new Callback<JsonNode>() {

                            public void failed(UnirestException e) {
                                LOGGER.log(Level.WARNING, "The request for url " + url + " has failed.", e);
                            }

                            public void completed(HttpResponse<JsonNode> response) {
                                int responseCode = response.getStatus();
                                LOGGER.log(Level.INFO, "The request for url " + url + " completed with status " + responseCode);
                            }

                            public void cancelled() {
                                LOGGER.log(Level.INFO, "The request for url " + url + " has been cancelled");
                            }

                        });
            } catch (Throwable e) {
                LOGGER.log(Level.WARNING, "Unable to post event to url " + url, e);
            }
        }
    }

    public static JSONObject getJson(final String url) {
        try{
            HttpResponse<JsonNode> response = Unirest.get(url)
                .header(ACCEPT, APPLICATION_JSON)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .asJson();
            return response.getBody().getObject();
        }
        catch (UnirestException e){
            LOGGER.log(Level.WARNING, "Json call have failed in unirest.", e);
        }
        return null;
    }
}