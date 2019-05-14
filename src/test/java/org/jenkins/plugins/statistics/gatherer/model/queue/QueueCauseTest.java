package org.jenkins.plugins.statistics.gatherer.model.queue;

import org.jenkins.plugins.statistics.gatherer.model.queue.QueueCause;
import org.junit.Test;
import org.junit.Before;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by mcharron on 2016-06-27.
 */
public class QueueCauseTest {

    private static final String REASON_FOR_WAITING = "aType";
    private static final String TYPE = "aType";
    private static final Date ENTRY_TIME = new Date(0);
    private static final Date EXIT_TIME = new Date(1000000);
    private QueueCause queueCause;

    @Before
    public void initBaseObject() {
        queueCause = new QueueCause(ENTRY_TIME,
                EXIT_TIME,
                REASON_FOR_WAITING,
                TYPE);
    }

    @Test
    public void givenNothing_whenConstruct_thenValuesAreSet() {
        //when
        QueueCause queueCause = new QueueCause();
        //then
        Date compareDate = new Date();
        assertTrue(queueCause.getEntryTime().before(compareDate) || queueCause.getEntryTime().equals(compareDate));
        assertTrue(queueCause.getExitTime().before(compareDate) || queueCause.getExitTime().equals(compareDate));
        assertEquals("", queueCause.getReasonForWaiting());
        assertEquals("", queueCause.getType());
    }

    @Test
    public void givenQueueCause_whenGetReasonForWaiting_thenReturnReasonForWaiting() {
        //given

        //when
        String reasonForWaiting = queueCause.getReasonForWaiting();

        //then
        assertEquals(REASON_FOR_WAITING, reasonForWaiting);
    }

    @Test
    public void givenQueueCause_whenSetReasonForWaiting_thenReasonForWaitingIsSet() {
        //given
        String expectedReasonForWaiting = "IHazReasons!";
        //when
        queueCause.setReasonForWaiting(expectedReasonForWaiting);

        //then
        String actualReasonForWaiting = queueCause.getReasonForWaiting();
        assertEquals(expectedReasonForWaiting, actualReasonForWaiting);
    }

    @Test
    public void givenQueueCause_whenGetType_thenReturnType() {
        //given

        //when
        String branch = queueCause.getType();

        //then
        assertEquals(TYPE, branch);
    }

    @Test
    public void givenQueueCause_whenSetType_thenTypeIsSet() {
        //given
        String expectedType = "IHazType!";
        //when
        queueCause.setType(expectedType);

        //then
        String actualType = queueCause.getType();
        assertEquals(expectedType, actualType);
    }

    @Test
    public void givenQueueCause_whenGetEntryTime_thenReturnEntryTime() {
        //given

        //when
        Date entryTime = queueCause.getEntryTime();

        //then
        assertEquals(ENTRY_TIME, entryTime);
    }

    @Test
    public void givenQueueCause_whenSetEntryTime_thenEntryTimeIsSet() {
        //given
        Date expectedEntryTime = new Date(123456789);
        //when
        queueCause.setEntryTime(expectedEntryTime);

        //then
        Date actualEntryTime = queueCause.getEntryTime();
        assertEquals(expectedEntryTime, actualEntryTime);
    }

    @Test
    public void givenQueueCause_whenGetExitTime_thenReturnExitTime() {
        //given

        //when
        Date exitTime = queueCause.getExitTime();

        //then
        assertEquals(EXIT_TIME, exitTime);
    }

    @Test
    public void givenQueueCause_whenSetExitTime_thenExitTimeIsSet() {
        //given
        Date expectedExitTime = new Date(123456789);
        //when
        queueCause.setExitTime(expectedExitTime);

        //then
        Date actualExitTime = queueCause.getExitTime();
        assertEquals(expectedExitTime, actualExitTime);
    }
}
