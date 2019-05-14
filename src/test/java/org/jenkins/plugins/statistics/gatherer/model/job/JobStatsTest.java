package org.jenkins.plugins.statistics.gatherer.model.job;

import org.jenkins.plugins.statistics.gatherer.model.job.JobStats;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by mcharron on 2016-06-29.
 */
public class JobStatsTest {

    private static final String NAME = "Job";
    private static final String USER_ID = "user";
    private static final String USERNAME = "username";
    private static final String CI_URL = "http://url.com";
    private static final String STATUS = "STATUS";
    private static final String CONFIG_FILE = "aConfigFile";
    private static final Date CREATED_DATE = new Date(0);
    private static final Date UPDATED_DATE = new Date(1000000);
    private static final String JOB_URL = "http://url.com/job";
    private JobStats jobStats;

    @Before
    public void initBaseObject() {
        jobStats = new JobStats(NAME,
                CREATED_DATE,
                USER_ID,
                USERNAME,
                CI_URL,
                UPDATED_DATE,
                STATUS,
                CONFIG_FILE,
                JOB_URL);
    }

    @Test
    public void givenNothing_whenConstruct_thenValuesAreSet() {
        //when
        JobStats jobStats = new JobStats();
        //then
        Date compareDate = new Date();
        assertEquals("", jobStats.getName());
        assertTrue(jobStats.getCreatedDate().before(compareDate) || jobStats.getCreatedDate().equals(compareDate));
        assertEquals("", jobStats.getUserId());
        assertEquals("", jobStats.getUserName());
        assertEquals("", jobStats.getCiUrl());
        assertTrue(jobStats.getUpdatedDate().before(compareDate) || jobStats.getUpdatedDate().equals(compareDate));
        assertEquals("", jobStats.getStatus());
        assertEquals("", jobStats.getConfigFile());
        assertEquals("", jobStats.getJobUrl());
    }

    @Test
    public void givenStatsJob_whenGetCiUrl_thenReturnCiUrl() {
        //given

        //when
        String ciUrl = jobStats.getCiUrl();

        //then
        assertEquals(CI_URL, ciUrl);
    }

    @Test
    public void givenStatsJob_whenSetCiUrl_thenCiUrlIsSet() {
        //given
        String expectedCiUrl = "IHazCiUrl!";
        //when
        jobStats.setCiUrl(expectedCiUrl);

        //then
        String actualCiUrl = jobStats.getCiUrl();
        assertEquals(expectedCiUrl, actualCiUrl);
    }

    @Test
    public void givenStatsJob_whenGetName_thenReturnName() {
        //given

        //when
        String name = jobStats.getName();

        //then
        assertEquals(NAME, name);
    }

    @Test
    public void givenStatsJob_whenSetName_thenNameIsSet() {
        //given
        String expectedName = "IHazName!";
        //when
        jobStats.setName(expectedName);

        //then
        String actualName = jobStats.getName();
        assertEquals(expectedName, actualName);
    }

    @Test
    public void givenStatsJob_whenGetUsername_thenReturnUsername() {
        //given

        //when
        String userName = jobStats.getUserName();

        //then
        assertEquals(USERNAME, userName);
    }

    @Test
    public void givenStatsJob_whenSetUserName_thenUserNameIsSet() {
        //given
        String expectedFullJobName = "IHazUserName!";
        //when
        jobStats.setUserName(expectedFullJobName);

        //then
        String actualUserName = jobStats.getUserName();
        assertEquals(expectedFullJobName, actualUserName);
    }

    @Test
    public void givenStatsJob_whenGetUserId_thenReturnUserId() {
        //given

        //when
        String userId = jobStats.getUserId();

        //then
        assertEquals(USER_ID, userId);
    }

    @Test
    public void givenStatsJob_whenSetUserId_thenUserIdIsSet() {
        //given
        String expectedUserId = "IHazUserId!";
        //when
        jobStats.setUserId(expectedUserId);

        //then
        String actualUserId = jobStats.getUserId();
        assertEquals(expectedUserId, actualUserId);
    }

    @Test
    public void givenStatsJob_whenGetStatus_thenReturnStatus() {
        //given

        //when
        String status = jobStats.getStatus();

        //then
        assertEquals(STATUS, status);
    }

    @Test
    public void givenStatsJob_whenSetStatus_thenStatusIsSet() {
        //given
        String expectedStatus = "IHazStatus!";
        //when
        jobStats.setStatus(expectedStatus);

        //then
        String actualStatus = jobStats.getStatus();
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void givenStatsJob_whenGetConfigFile_thenReturnConfigFile() {
        //given

        //when
        String configFile = jobStats.getConfigFile();

        //then
        assertEquals(CONFIG_FILE, configFile);
    }

    @Test
    public void givenStatsJob_whenSetConfigFile_thenConfigFileIsSet() {
        //given
        String expectedConfigFile = "IHazConfigFile!";
        //when
        jobStats.setConfigFile(expectedConfigFile);

        //then
        String actualConfigFile = jobStats.getConfigFile();
        assertEquals(expectedConfigFile, actualConfigFile);
    }

    @Test
    public void givenStatsJob_whenGetCreatedDate_thenReturnCreateDate() {
        //given

        //when
        Date createdDate = jobStats.getCreatedDate();

        //then
        assertEquals(CREATED_DATE, createdDate);
    }

    @Test
    public void givenStatsJob_whenSetCreatedDate_thenCreatedDateIsSet() {
        //given
        Date expectedCreatedDate = new Date();
        //when
        jobStats.setCreatedDate(expectedCreatedDate);

        //then
        Date actualCreatedDate = jobStats.getCreatedDate();
        assertEquals(expectedCreatedDate, actualCreatedDate);
    }

    @Test
    public void givenStatsJob_whenGetUpdatedDate_thenReturnUpdatedDate() {
        //given

        //when
        Date updatedDate = jobStats.getUpdatedDate();

        //then
        assertEquals(UPDATED_DATE, updatedDate);
    }

    @Test
    public void givenStatsJob_whenSetUpdatedDate_thenUpdatedDateIsSet() {
        //given
        Date expectedUpdatedDate = new Date();
        //when
        jobStats.setUpdatedDate(expectedUpdatedDate);

        //then
        Date actualUpdatedDate = jobStats.getUpdatedDate();
        assertEquals(expectedUpdatedDate, actualUpdatedDate);
    }

    @Test
    public void givenNullEntryTime_whenSetEntryTime_thenEntryTimeIsNull() {
        //given

        //when
        jobStats.setCreatedDate(null);

        //then
        Date createdDate = jobStats.getCreatedDate();
        assertNull(createdDate);
    }

    @Test
    public void givenNullExitTime_whenSetExitTime_thenExitTimeIsNull() {
        //given

        //when
        jobStats.setUpdatedDate(null);

        //then
        Date updatedDate = jobStats.getUpdatedDate();
        assertNull(updatedDate);
    }

    @Test
    public void givenStatsJob_whenGetJobUrl_thenReturnJobUrl() {
        //given

        //when
        String jobUrl = jobStats.getJobUrl();

        //then
        assertEquals(JOB_URL, jobUrl);
    }

    @Test
    public void givenStatsJob_whenSetJobUrl_thenJobUrlIsSet() {
        //given
        String expectedJobUrl = "IHazJobUrl!";
        //when
        jobStats.setJobUrl(expectedJobUrl);

        //then
        String actualJobUrl = jobStats.getJobUrl();
        assertEquals(expectedJobUrl, actualJobUrl);
    }
}
