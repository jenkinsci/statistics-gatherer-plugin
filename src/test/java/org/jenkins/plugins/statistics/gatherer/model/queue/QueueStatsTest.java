package org.jenkins.plugins.statistics.gatherer.model.queue;

import org.jenkins.plugins.statistics.gatherer.model.queue.QueueCause;
import org.jenkins.plugins.statistics.gatherer.model.queue.QueueStats;
import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by mcharron on 2016-06-27.
 */
public class QueueStatsTest {

    private static final String CI_URL = "http://url.com";
    private static final String JOB_NAME = "aName";
    private static final Date ENTRY_TIME = new Date(0);
    private static final Date EXIT_TIME = new Date(1000000);
    private static final String STARTED_BY = "aUser";
    private static final int JENKINS_QUEUE_ID = 1;
    private static final String STATUS = "COMPLETED";
    private static final int DURATION = 2000;
    private static final int CONTEXT_ID = 444444444;
    private static final ArrayList<QueueCause> QUEUE_CAUSES = new ArrayList<>();
    private QueueStats queueStats;

    @Before
    public void initBaseObject() {
        queueStats = new QueueStats(CI_URL,
                JOB_NAME,
                ENTRY_TIME,
                EXIT_TIME,
                STARTED_BY,
                JENKINS_QUEUE_ID,
                STATUS,
                DURATION,
                QUEUE_CAUSES,
                CONTEXT_ID);
    }

    @Test
    public void givenStatsQueue_whenGetCiUrl_thenReturnCiUrl() {
        //given

        //when
        String url = queueStats.getCiUrl();

        //then
        assertEquals(CI_URL, url);
    }

    @Test
    public void givenNothing_whenConstructEmpty_thenAllValuesAreSet() {
        //when
        QueueStats queueStats = new QueueStats();

        //then
        assertEquals("", queueStats.getCiUrl());
        assertEquals("", queueStats.getJobName());
        Date compareDate = new Date();
        assertTrue(queueStats.getEntryTime().before(compareDate) || queueStats.getEntryTime().equals(compareDate));
        assertTrue(queueStats.getExitTime().before(compareDate) || queueStats.getExitTime().equals(compareDate));
        assertEquals("", queueStats.getStartedBy());
        assertEquals(0, queueStats.getJenkinsQueueId());
        assertEquals("", queueStats.getStatus());
        assertEquals(0, queueStats.getDuration());
        assertEquals(new ArrayList<QueueCause>(), queueStats.getQueueCauses());
        assertEquals(0, queueStats.getContextId());
    }

    @Test
    public void givenStatsQueue_whenGetJobName_thenReturnJobName() {
        //given

        //when
        String jobName = queueStats.getJobName();

        //then
        assertEquals(JOB_NAME, jobName);
    }

    @Test
    public void givenStatsQueue_whenGetEntryTime_thenReturnEntryTime() {
        //given

        //when
        Date entryTime = queueStats.getEntryTime();

        //then
        assertEquals(ENTRY_TIME, entryTime);
    }

    @Test
    public void givenStatsQueue_whenGetExitTime_thenReturnExitTime() {
        //given

        //when
        Date exitTime = queueStats.getExitTime();

        //then
        assertEquals(EXIT_TIME, exitTime);
    }

    @Test
    public void givenStatsQueue_whenGetStartedBy_thenReturnStartedBy() {
        //given

        //when
        String startedBy = queueStats.getStartedBy();

        //then
        assertEquals(STARTED_BY, startedBy);
    }

    @Test
    public void givenStatsQueue_whenGetJenkinsQueueId_thenReturnJenkinsQueueId() {
        //given

        //when
        int jenkinsQueueId = queueStats.getJenkinsQueueId();

        //then
        assertEquals(JENKINS_QUEUE_ID, jenkinsQueueId);
    }

    @Test
    public void givenStatsQueue_whenGetStatus_thenReturnStatus() {
        //given

        //when
        String status = queueStats.getStatus();

        //then
        assertEquals(STATUS, status);
    }

    @Test
    public void givenStatsQueue_whenGetDuration_thenReturnDuration() {
        //given

        //when
        long duration = queueStats.getDuration();

        //then
        assertEquals(DURATION, duration);
    }

    @Test
    public void givenStatsQueue_whenGetContextId_thenReturnContextId() {
        //given

        //when
        int contextId = queueStats.getContextId();

        //then
        assertEquals(CONTEXT_ID, contextId);
    }

    @Test
    public void givenStatsQueue_whenGetQueueCauses_thenReturnQueueCauses() {
        //given

        //when
        List<QueueCause> queueCauses = queueStats.getQueueCauses();

        //then
        assertEquals(QUEUE_CAUSES, queueCauses);
    }

    @Test
    public void givenStatsQueue_whenSetQueueCauses_thenQueueCausesIsSet() {
        //given
        QueueCause queueCause = new QueueCause(ENTRY_TIME, EXIT_TIME, "aReason", "type");
        List<QueueCause> queueCausesToBeSet = new ArrayList<>();
        queueCausesToBeSet.add(queueCause);
        //when
        queueStats.setQueueCauses(queueCausesToBeSet);

        //then
        List<QueueCause> queueCauses = queueStats.getQueueCauses();
        assertEquals(queueCausesToBeSet, queueCauses);
    }

    @Test
    public void givenStatsQueue_whenSetCiUrl_thenCiUrlIsSet() {
        //given
        String ciUrl = "aUrl";
        //when
        queueStats.setCiUrl(ciUrl);

        //then
        String url = queueStats.getCiUrl();
        assertEquals(ciUrl, url);
    }

    @Test
    public void givenStatsQueue_whenSetEntryTime_thenEntryTimeIsSet() {
        //given
        Date expectedEntryTime = new Date(123456789);
        //when
        queueStats.setEntryTime(expectedEntryTime);

        //then
        Date actualEntryTime = queueStats.getEntryTime();
        assertEquals(expectedEntryTime, actualEntryTime);
    }

    @Test
    public void givenStatsQueue_whenSetExitTime_thenExitTimeIsSet() {
        //given
        Date expectedExitTime = new Date(123456789);
        //when
        queueStats.setExitTime(expectedExitTime);

        //then
        Date actualExitTime = queueStats.getExitTime();
        assertEquals(expectedExitTime, actualExitTime);
    }

    @Test
    public void givenStatsQueue_whenSetStartedBy_thenStartedByIsSet() {
        //given
        String expectedStartedBy = "IHazStartedIt!";
        //when
        queueStats.setStartedBy(expectedStartedBy);

        //then
        String actualStartedBy = queueStats.getStartedBy();
        assertEquals(expectedStartedBy, actualStartedBy);
    }

    @Test
    public void givenStatsQueue_whenSetJenkinsQueueId_thenJenkinsQueueIdIsSet() {
        //given
        int expectedQueueId = 123456;
        //when
        queueStats.setJenkinsQueueId(expectedQueueId);

        //then
        int actualQueueId = queueStats.getJenkinsQueueId();
        assertEquals(expectedQueueId, actualQueueId);
    }

    @Test
    public void givenStatsQueue_whenSetContextId_thenContextIdIsSet() {
        //given
        int expectedContextId = 123456;
        //when
        queueStats.setContextId(expectedContextId);

        //then
        int actualContextId = queueStats.getContextId();
        assertEquals(expectedContextId, actualContextId);
    }

    @Test
    public void givenStatsQueue_whenSetStatus_thenStatusIsSet() {
        //given
        String expectedStatus = "WOOHOO";
        //when
        queueStats.setStatus(expectedStatus);

        //then
        String actualStatus = queueStats.getStatus();
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void givenStatsQueue_whenSetDuration_thenDurationIsSet() {
        //given
        long expectedDuration = 123456;
        //when
        queueStats.setDuration(expectedDuration);

        //then
        long actualDuration = queueStats.getDuration();
        assertEquals(expectedDuration, actualDuration);
    }

    @Test
    public void givenStatsQueue_whenSetJobName_thenJobNameIsSet() {
        //given
        String expectedJobName = "ThisIsAName";
        //when
        queueStats.setJobName(expectedJobName);

        //then
        String actualJobName = queueStats.getJobName();
        assertEquals(expectedJobName, actualJobName);
    }

    @Test
    public void givenNullEntryTime_whenSetEntryTime_thenEntryTimeIsNull() {
        //given

        //when
        queueStats.setEntryTime(null);

        //then
        Date actualEntryTime = queueStats.getEntryTime();
        assertNull(actualEntryTime);
    }

    @Test
    public void givenNullExitTime_whenSetExitTime_thenExitTimeIsNull() {
        //given

        //when
        queueStats.setExitTime(null);

        //then
        Date actualExitTime = queueStats.getExitTime();
        assertNull(actualExitTime);
    }

    @Test
    public void givenStatsQueue_whenAddQueueCause_thenQueueCauseIsAdded() {
        //given
        QueueCause queueCause = new QueueCause(ENTRY_TIME, EXIT_TIME, "aReason", "type");
        List<QueueCause> expectedQueueCauses = new ArrayList<>();
        expectedQueueCauses.add(queueCause);
        //when
        queueStats.addQueueCause(queueCause);

        //then
        List<QueueCause> queueCauses = queueStats.getQueueCauses();
        assertEquals(expectedQueueCauses, queueCauses);
    }
}
