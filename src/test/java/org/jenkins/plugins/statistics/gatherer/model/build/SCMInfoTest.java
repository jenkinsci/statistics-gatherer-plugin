package org.jenkins.plugins.statistics.gatherer.model.build;

import org.jenkins.plugins.statistics.gatherer.model.build.SCMInfo;
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

/**
 * Created by mcharron on 2016-06-27.
 */
public class SCMInfoTest {

    private static final String URL = "http://url.com";
    private static final String BRANCH = "aBranch";
    private static final String COMMIT = "aCommit";
    private SCMInfo scmInfo;

    @Before
    public void initBaseObject() {
        scmInfo = new SCMInfo(URL, BRANCH, COMMIT);
    }

    @Test
    public void givenScmInfo_whenGetUrl_thenReturnUrl() {
        //given

        //when
        String url = scmInfo.getUrl();

        //then
        assertEquals(URL, url);
    }

    @Test
    public void givenScmInfo_whenSetUrl_thenUrlIsSet() {
        //given
        String expectedUrl = "IHazURL!";
        //when
        scmInfo.setUrl(expectedUrl);

        //then
        String actualUrl = scmInfo.getUrl();
        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    public void givenScmInfo_whenGetBranch_thenReturnBranch() {
        //given

        //when
        String branch = scmInfo.getBranch();

        //then
        assertEquals(BRANCH, branch);
    }

    @Test
    public void givenScmInfo_whenSetBranch_thenBranchIsSet() {
        //given
        String expectedBranch = "IHazBranch!";
        //when
        scmInfo.setBranch(expectedBranch);

        //then
        String actualBranch = scmInfo.getBranch();
        assertEquals(expectedBranch, actualBranch);
    }

    @Test
    public void givenScmInfo_whenGetCommit_thenReturnCommit() {
        //given

        //when
        String commit = scmInfo.getCommit();

        //then
        assertEquals(COMMIT, commit);
    }

    @Test
    public void givenScmInfo_whenSetCommit_thenCommitIsSet() {
        //given
        String expectedCommit = "IHazCommit!";
        //when
        scmInfo.setCommit(expectedCommit);

        //then
        String actualCommit = scmInfo.getCommit();
        assertEquals(expectedCommit, actualCommit);
    }
}
