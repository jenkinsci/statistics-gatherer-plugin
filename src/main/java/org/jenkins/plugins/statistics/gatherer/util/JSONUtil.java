package org.jenkins.plugins.statistics.gatherer.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by hthakkallapally on 3/17/2015.
 */
public class JSONUtil {

    private static final Logger LOGGER = Logger.getLogger(JSONUtil.class.getName());

    protected JSONUtil() {
        throw new IllegalAccessError("Utility class");
    }

    public static String convertToJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        String convertedJson = "";
        try {
            convertedJson = mapper.writeValueAsString(object);
            return convertedJson;
        } catch (JsonProcessingException e) {
            LOGGER.log(Level.WARNING, "Json conversion failed for object " + object, e);
        }
        return convertedJson;
    }

    public static Map<String, Object> convertBuildFailureToMap(JSONObject jObject) {
        Map<String, Object> map = new HashMap<>();
        Iterator<?> keys = jObject.keys();

        while (keys.hasNext()) {
            String key = (String) keys.next();
            if ("categories".equals(key)) {
                List<String> value = convertJsonArrayToList(jObject.getJSONArray(key));
                map.put(key, value);
            } else {
                String value = jObject.getString(key);
                map.put(key, value);
            }
        }
        return map;
    }

    public static List<String> convertJsonArrayToList(JSONArray jsonArray) {
        List<String> listdata = new ArrayList<>();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                listdata.add(jsonArray.get(i).toString());
            }
        }
        return listdata;
    }
}
