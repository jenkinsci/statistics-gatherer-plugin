package org.jenkins.plugins.statistics.gatherer.model.step;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by mcharron on 2016-07-19.
 */
public class BuildStepStatsTest {

    private static final String BUILD_URL = "anUrl";
    private static final String BUILD_STEP_TYPE = "aType";
    private static final String BUILD_STEP_ID = "anId";
    private static final Date START_TIME = new Date(1000);
    private static final Date END_TIME = new Date(1000000);
    private BuildStepStats buildStepStats;

    @Before
    public void initBaseObject() {
        buildStepStats = new BuildStepStats(START_TIME, END_TIME, BUILD_URL,
                BUILD_STEP_TYPE,
                BUILD_STEP_ID);
    }

    @Test
    public void givenNothing_whenCreateBuildStepStats_thenItIsInitialized() {
        BuildStepStats buildStep = new BuildStepStats();
        assertEquals("", buildStep.getBuildUrl());
        assertEquals("", buildStep.getBuildStepType());
        assertEquals("", buildStep.getBuildStepId());
        assertEquals(new Date(0), buildStep.getStartTime());
        assertEquals(new Date(0), buildStep.getEndTime());
    }

    @Test
    public void givenBuildStepStats_whenGetBuildUrl_thenReturnBuildUrl() {
        //given

        //when
        String buildUrl = buildStepStats.getBuildUrl();

        //then
        assertEquals(BUILD_URL, buildUrl);
    }

    @Test
    public void givenBuildStepStats_whenSetBuildUrl_thenBuildUrlIsSet() {
        //given
        String expectedBuildUrl = "IHazBuildUrl!";
        //when
        buildStepStats.setBuildUrl(expectedBuildUrl);

        //then
        String actualBuildUrl = buildStepStats.getBuildUrl();
        assertEquals(expectedBuildUrl, actualBuildUrl);
    }

    @Test
    public void givenBuildStepStats_whenGetBuildStepType_thenReturnBuildStepType() {
        //given

        //when
        String buildStepType = buildStepStats.getBuildStepType();

        //then
        assertEquals(BUILD_STEP_TYPE, buildStepType);
    }

    @Test
    public void givenBuildStepStats_whenSetBuildStepType_thenBuildStepTypeIsSet() {
        //given
        String expectedBuildStepType = "IHazBuildStepType!";
        //when
        buildStepStats.setBuildStepType(expectedBuildStepType);

        //then
        String actualBuildStepType = buildStepStats.getBuildStepType();
        assertEquals(expectedBuildStepType, actualBuildStepType);
    }

    @Test
    public void givenBuildStepStats_whenGetBuildStepId_thenReturnBuildStepId() {
        //given

        //when
        String buildStepId = buildStepStats.getBuildStepId();

        //then
        assertEquals(BUILD_STEP_ID, buildStepId);
    }

    @Test
    public void givenBuildStepStats_whenSetBuildStepId_thenBuildStepIdIsSet() {
        //given
        String expectedBuildStepId = "IHazBuildStepId!";
        //when
        buildStepStats.setBuildStepId(expectedBuildStepId);

        //then
        String actualBuildStepId = buildStepStats.getBuildStepId();
        assertEquals(expectedBuildStepId, actualBuildStepId);
    }

    @Test
    public void givenBuildStepStats_whenGetStartTime_thenReturnStartTime() {
        //given

        //when
        Date startTime = buildStepStats.getStartTime();

        //then
        assertEquals(START_TIME, startTime);
    }

    @Test
    public void givenBuildStepStats_whenSetStartTime_thenStartTimeIsSet() {
        //given
        Date expectedStartTime = new Date(123456);
        //when
        buildStepStats.setStartTime(expectedStartTime);

        //then
        Date actualStartTime = buildStepStats.getStartTime();
        assertEquals(expectedStartTime, actualStartTime);
    }

    @Test
    public void givenBuildStepStats_whenGetEndTime_thenReturnEndTime() {
        //given

        //when
        Date endTime = buildStepStats.getEndTime();

        //then
        assertEquals(END_TIME, endTime);
    }

    @Test
    public void givenBuildStepStats_whenSetEndTime_thenEndTimeIsSet() {
        //given
        Date expectedEndTime = new Date(123456);
        //when
        buildStepStats.setEndTime(expectedEndTime);

        //then
        Date actualEndTime = buildStepStats.getEndTime();
        assertEquals(expectedEndTime, actualEndTime);
    }
}
