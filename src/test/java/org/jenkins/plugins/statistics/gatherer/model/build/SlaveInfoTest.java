package org.jenkins.plugins.statistics.gatherer.model.build;

import org.jenkins.plugins.statistics.gatherer.model.build.SlaveInfo;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by mcharron on 2016-06-29.
 */
public class SlaveInfoTest {

    private static final String SLAVE_NAME = "slave";
    private static final String EXECUTOR = "executor";
    private static final String LABEL = "aLabel";
    private SlaveInfo slaveInfo;

    @Before
    public void initBaseObject() {
        slaveInfo = new SlaveInfo(SLAVE_NAME, EXECUTOR, LABEL);
    }

    @Test
    public void givenSlaveInfo_whenGetSlaveName_thenReturnSlaveName() {
        //given

        //when
        String slaveName = slaveInfo.getSlaveName();

        //then
        assertEquals(SLAVE_NAME, slaveName);
    }

    @Test
    public void givenSlaveInfo_whenSetSlaveName_thenSlaveNameIsSet() {
        //given
        String expectedSlaveName = "IHazSlaveName!";
        //when
        slaveInfo.setSlaveName(expectedSlaveName);

        //then
        String actualSlaveName = slaveInfo.getSlaveName();
        assertEquals(expectedSlaveName, actualSlaveName);
    }

    @Test
    public void givenSlaveInfo_whenGetExecutor_thenReturnExecutor() {
        //given

        //when
        String executor = slaveInfo.getExecutor();

        //then
        assertEquals(EXECUTOR, executor);
    }

    @Test
    public void givenSlaveInfo_whenSetExecutor_thenExecutorIsSet() {
        //given
        String expectedExecutor = "IHazExecutor!";
        //when
        slaveInfo.setExecutor(expectedExecutor);

        //then
        String actualExecutor = slaveInfo.getExecutor();
        assertEquals(expectedExecutor, actualExecutor);
    }

    @Test
    public void givenSlaveInfo_whenGetLabel_thenReturnLabel() {
        //given

        //when
        String label = slaveInfo.getLabel();

        //then
        assertEquals(LABEL, label);
    }

    @Test
    public void givenSlaveInfo_whenSetLabel_thenLabelIsSet() {
        //given
        String expectedLabel = "IHazLabel!";
        //when
        slaveInfo.setLabel(expectedLabel);

        //then
        String actualLabel = slaveInfo.getLabel();
        assertEquals(expectedLabel, actualLabel);
    }
}
