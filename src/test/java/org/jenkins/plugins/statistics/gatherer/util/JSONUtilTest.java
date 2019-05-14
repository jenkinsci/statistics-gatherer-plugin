package org.jenkins.plugins.statistics.gatherer.util;

import org.jenkins.plugins.statistics.gatherer.model.build.SCMInfo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by mcharron on 2016-06-27.
 */
public class JSONUtilTest {

    private SCMInfo testObject = new SCMInfo();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void givenObject_whenToJson_thenReturnJson() {
        testObject.setUrl("http://test.com");
        testObject.setBranch("blop");
        testObject.setCommit("blopie");
        String expectedJson = "{\"url\":\"http://test.com\",\"branch\":\"blop\",\"commit\":\"blopie\"}";

        String jsonString = JSONUtil.convertToJson(testObject);

        assertEquals(expectedJson, jsonString);
    }

    @Test
    public void givenInvalidObject_whenToJson_thenReturnEmptyJson() {
        JSONObject object = new JSONObject();
        String json = JSONUtil.convertToJson(object);
        assertEquals("", json);
    }

    @Test
    public void givenJsonArray_whenConvertToList_thenReturnList() {
        JSONArray array = new JSONArray();
        array.put("test");
        List<String> result = JSONUtil.convertJsonArrayToList(array);
        assertEquals(1, result.size());
        assertEquals("test", result.get(0));
    }

    @Test
    public void givenNull_whenConvertToList_thenReturnEmptyList() {
        List<String> result = JSONUtil.convertJsonArrayToList(null);
        assertEquals(0, result.size());
    }

    @Test
    public void givenJsonObjectWithCategories_whenConvertBuildFailureToMap_thenReturnValidMap() {
        JSONArray array = new JSONArray();
        array.put("test");
        JSONObject object = new JSONObject();
        object.put("categories", array);
        Map<String, Object> result = JSONUtil.convertBuildFailureToMap(object);
        assertEquals(1, result.size());
        assertEquals(1, ((List) result.get("categories")).size());
        assertEquals("test", ((List) result.get("categories")).get(0));
    }

    @Test
    public void givenJsonObjectWithoutCategories_whenConvertBuildFailureToMap_thenReturnValidMap() {
        JSONObject object = new JSONObject();
        object.put("stuff", "testString");
        Map<String, Object> result = JSONUtil.convertBuildFailureToMap(object);
        assertEquals(1, result.size());
        assertEquals("testString", result.get("stuff"));
    }

    @Test(expected = IllegalAccessError.class)
    public void givenProtectedConstructor_whenNew_throwIllegalAccess() {
        new JSONUtil();
    }
}
