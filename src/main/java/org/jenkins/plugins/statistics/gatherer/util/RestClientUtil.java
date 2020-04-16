package org.jenkins.plugins.statistics.gatherer.util;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
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
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .addHeader(ACCEPT, APPLICATION_JSON)
                        .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .url(url)
                        .post(RequestBody.create(MediaType.parse(APPLICATION_JSON), JSONUtil.convertToJson(object)))
                        .build();

                client.newCall(request).enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LOGGER.log(Level.WARNING, "The request for url " + url + " has failed.", e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        int responseCode = response.code();
                        LOGGER.log(Level.INFO, "The request for url " + url + " completed with status " + responseCode);
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