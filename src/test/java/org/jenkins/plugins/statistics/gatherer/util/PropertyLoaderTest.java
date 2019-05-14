package org.jenkins.plugins.statistics.gatherer.util;

import hudson.EnvVars;
import hudson.slaves.EnvironmentVariablesNodeProperty;
import hudson.slaves.NodeProperty;
import hudson.slaves.NodePropertyDescriptor;
import hudson.util.DescribableList;
import jenkins.model.Jenkins;
import org.jenkins.plugins.statistics.gatherer.StatisticsConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by mcharron on 2016-06-29.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({StatisticsConfiguration.class, ResourceBundle.class, Jenkins.class})
public class PropertyLoaderTest {

    @Test
    public void givenNoInstance_whenGetInstance_thenReturnInstance() {
        //given
        //when
        PropertyLoader propertyLoader = PropertyLoader.getInstance();

        //then
        assertNotNull(propertyLoader);
    }

    @Test
    public void givenInstance_whenGetInstance_thenReturnInstance() {
        //given
        //To create instance
        PropertyLoader propertyLoader = PropertyLoader.getInstance();

        //when
        PropertyLoader samePropertyLoader = PropertyLoader.getInstance();

        //then
        assertNotNull(propertyLoader);
        assertNotNull(samePropertyLoader);
        assertEquals(propertyLoader, samePropertyLoader);
    }

    @Test
    public void givenResourceBundleProperty_whenGetKey_thenReturnKeyString() {
        //given
        //when
        String property = PropertyLoader.getInstance().getResourceBundleProperty("statistics.endpoint.queueUrl");
        //then
        assertNotNull(property);
        assertEquals("http://cistats.mycompany.com/api/queues", property);
    }

    @Test
    public void givenResourceBundleWithoutProperty_whenGetKey_thenReturnNull() {
        //given
        //when
        String property = PropertyLoader.getInstance().getResourceBundleProperty("non.existent.property");
        //then
        assertNull("Non existent properties should return null values", property);
    }

    @Test
    public void givenQueueUrl_whenGetQueueUrlEndpoint_thenReturnQueueUrl() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getQueueUrl()).thenReturn("statistics.endpoint.queueUrl");
        //when
        String url = PropertyLoader.getQueueEndPoint();

        //then
        assertEquals("statistics.endpoint.queueUrl", url);
    }

    @Test
    public void givenNoQueueUrl_whenGetQueueUrlEndpoint_thenReturnQueueUrl() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getQueueUrl()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(new ArrayList<>());
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        String url = PropertyLoader.getQueueEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/queues", url);
    }

    @Test
    public void givenEnvVars_whenGetQueueUrlEndpoint_thenReturnQueueUrl() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getQueueUrl()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        List<EnvironmentVariablesNodeProperty> environmentVariablesNodePropertyList = new ArrayList<>();
        EnvironmentVariablesNodeProperty envVar = mock(EnvironmentVariablesNodeProperty.class);
        Map<String, String> envVarMap = new HashMap<>();
        envVarMap.put("statistics.endpoint.queueUrl", "http://cistats.mycompany.com/api/");
        Mockito.when(envVar.getEnvVars()).thenReturn(new EnvVars(envVarMap));
        environmentVariablesNodePropertyList.add(envVar);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(environmentVariablesNodePropertyList);
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        String url = PropertyLoader.getQueueEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/", url);
    }

    @Test
    public void givenEmptyEnvVars_whenGetQueueUrlEndpoint_thenReturnQueueUrl() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getQueueUrl()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        List<EnvironmentVariablesNodeProperty> environmentVariablesNodePropertyList = new ArrayList<>();
        EnvironmentVariablesNodeProperty envVar = mock(EnvironmentVariablesNodeProperty.class);
        Map<String, String> envVarMap = new HashMap<>();
        envVarMap.put("statistics.endpoint.queueUrl", "");
        Mockito.when(envVar.getEnvVars()).thenReturn(new EnvVars(envVarMap));
        environmentVariablesNodePropertyList.add(envVar);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(environmentVariablesNodePropertyList);
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        String url = PropertyLoader.getQueueEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/queues", url);
    }

    @Test
    public void givenEmptyQueueUrl_whenGetQueueUrlEndpoint_thenReturnQueueUrl() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getQueueUrl()).thenReturn("");
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(new ArrayList<>());
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        String url = PropertyLoader.getQueueEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/queues", url);
    }

    @Test
    public void givenBuildUrl_whenGetBuildUrlEndpoint_thenReturnBuildUrl() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getBuildUrl()).thenReturn("statistics.endpoint.buildUrl");
        //when
        String url = PropertyLoader.getBuildEndPoint();

        //then
        assertEquals("statistics.endpoint.buildUrl", url);
    }


    @Test
    public void givenNoBuildUrl_whenGetBuildUrlEndpoint_thenReturnBuildUrl() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getBuildUrl()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(new ArrayList<>());
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        String url = PropertyLoader.getBuildEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/builds", url);
    }

    @Test
    public void givenEnvVars_whenGetBuildUrlEndpoint_thenReturnBuildUrl() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getBuildUrl()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        List<EnvironmentVariablesNodeProperty> environmentVariablesNodePropertyList = new ArrayList<>();
        EnvironmentVariablesNodeProperty envVar = mock(EnvironmentVariablesNodeProperty.class);
        Map<String, String> envVarMap = new HashMap<>();
        envVarMap.put("statistics.endpoint.buildUrl", "http://cistats.mycompany.com/api/");
        Mockito.when(envVar.getEnvVars()).thenReturn(new EnvVars(envVarMap));
        environmentVariablesNodePropertyList.add(envVar);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(environmentVariablesNodePropertyList);
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        String url = PropertyLoader.getBuildEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/", url);
    }

    @Test
    public void givenEmptyEnvVars_whenGetBuildUrlEndpoint_thenReturnBuildUrl() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getBuildUrl()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        List<EnvironmentVariablesNodeProperty> environmentVariablesNodePropertyList = new ArrayList<>();
        EnvironmentVariablesNodeProperty envVar = mock(EnvironmentVariablesNodeProperty.class);
        Map<String, String> envVarMap = new HashMap<>();
        envVarMap.put("statistics.endpoint.buildUrl", "");
        Mockito.when(envVar.getEnvVars()).thenReturn(new EnvVars(envVarMap));
        environmentVariablesNodePropertyList.add(envVar);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(environmentVariablesNodePropertyList);
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        String url = PropertyLoader.getBuildEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/builds", url);
    }

    @Test
    public void givenEmptyBuildUrl_whenGetBuildUrlEndpoint_thenReturnBuildUrl() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getBuildUrl()).thenReturn("");
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(new ArrayList<>());
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        String url = PropertyLoader.getBuildEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/builds", url);
    }

    @Test
    public void givenProjectUrl_whenGetProjectUrlEndpoint_thenReturnProjectUrl() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getProjectUrl()).thenReturn("statistics.endpoint.projectUrl");
        //when
        String url = PropertyLoader.getProjectEndPoint();

        //then
        assertEquals("statistics.endpoint.projectUrl", url);
    }

    @Test
    public void givenNoProjectUrl_whenGetProjectUrlEndpoint_thenReturnProjectUrl() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getProjectUrl()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(new ArrayList<>());
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        String url = PropertyLoader.getProjectEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/projects", url);
    }

    @Test
    public void givenEnvVars_whenGetProjectUrlEndpoint_thenReturnProjectUrl() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getProjectUrl()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        List<EnvironmentVariablesNodeProperty> environmentVariablesNodePropertyList = new ArrayList<>();
        EnvironmentVariablesNodeProperty envVar = mock(EnvironmentVariablesNodeProperty.class);
        Map<String, String> envVarMap = new HashMap<>();
        envVarMap.put("statistics.endpoint.projectUrl", "http://cistats.mycompany.com/api/");
        Mockito.when(envVar.getEnvVars()).thenReturn(new EnvVars(envVarMap));
        environmentVariablesNodePropertyList.add(envVar);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(environmentVariablesNodePropertyList);
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        String url = PropertyLoader.getProjectEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/", url);
    }

    @Test
    public void givenEmptyEnvVars_whenGetProjectUrlEndpoint_thenReturnProjectUrl() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getProjectUrl()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        List<EnvironmentVariablesNodeProperty> environmentVariablesNodePropertyList = new ArrayList<>();
        EnvironmentVariablesNodeProperty envVar = mock(EnvironmentVariablesNodeProperty.class);
        Map<String, String> envVarMap = new HashMap<>();
        envVarMap.put("statistics.endpoint.projectUrl", "");
        Mockito.when(envVar.getEnvVars()).thenReturn(new EnvVars(envVarMap));
        environmentVariablesNodePropertyList.add(envVar);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(environmentVariablesNodePropertyList);
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        String url = PropertyLoader.getProjectEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/projects", url);
    }

    @Test
    public void givenEmptyProjectUrl_whenGetProjectUrlEndpoint_thenReturnProjectUrl() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getProjectUrl()).thenReturn("");
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(new ArrayList<>());
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        String url = PropertyLoader.getProjectEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/projects", url);
    }

    @Test
    public void givenNoKey_whenGetProperty_thenReturnNull() {
        //given
        //when
        String property = PropertyLoader.getInstance().getProperty(null);
        //then
        assertNull(property);
    }

    @Test
    public void givenEmptyKey_whenGetProperty_thenReturnNull() {
        //given
        //when
        String property = PropertyLoader.getInstance().getProperty("");
        //then
        assertNull(property);
    }

    @Test
    public void givenBuildStepUrl_whenGetBuildStepUrlEndpoint_thenReturnBuildStepUrl() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getBuildStepUrl()).thenReturn("statistics.endpoint.buildStepUrl");
        //when
        String url = PropertyLoader.getBuildStepEndPoint();

        //then
        assertEquals("statistics.endpoint.buildStepUrl", url);
    }

    @Test
    public void givenNoBuildStepUrl_whenGetBuildStepUrlEndpoint_thenReturnBuildStepUrl() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getBuildStepUrl()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList describableList = mock(DescribableList.class);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(new ArrayList<EnvironmentVariablesNodeProperty>());
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        String url = PropertyLoader.getBuildStepEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/buildSteps", url);
    }

    @Test
    public void givenEnvVars_whenGetBuildStepUrlEndpoint_thenReturnBuildStepUrl() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getBuildStepUrl()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        List<EnvironmentVariablesNodeProperty> environmentVariablesNodePropertyList = new ArrayList<>();
        EnvironmentVariablesNodeProperty envVar = mock(EnvironmentVariablesNodeProperty.class);
        Map<String, String> envVarMap = new HashMap<>();
        envVarMap.put("statistics.endpoint.buildStepUrl", "http://cistats.mycompany.com/api/");
        Mockito.when(envVar.getEnvVars()).thenReturn(new EnvVars(envVarMap));
        environmentVariablesNodePropertyList.add(envVar);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(environmentVariablesNodePropertyList);
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        String url = PropertyLoader.getBuildStepEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/", url);
    }

    @Test
    public void givenEmptyEnvVars_whenGetBuildStepUrlEndpoint_thenReturnBuildStepUrl() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getBuildStepUrl()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        List<EnvironmentVariablesNodeProperty> environmentVariablesNodePropertyList = new ArrayList<>();
        EnvironmentVariablesNodeProperty envVar = mock(EnvironmentVariablesNodeProperty.class);
        Map<String, String> envVarMap = new HashMap<>();
        envVarMap.put("statistics.endpoint.buildStepUrl", "");
        Mockito.when(envVar.getEnvVars()).thenReturn(new EnvVars(envVarMap));
        environmentVariablesNodePropertyList.add(envVar);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(environmentVariablesNodePropertyList);
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        String url = PropertyLoader.getBuildStepEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/buildSteps", url);
    }

    @Test
    public void givenEmptyBuildStepUrl_whenGetBuildStepUrlEndpoint_thenReturnBuildStepUrl() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getBuildStepUrl()).thenReturn("");
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(new ArrayList<>());
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        String url = PropertyLoader.getBuildStepEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/buildSteps", url);
    }

    @Test
    public void givenScmCheckoutUrl_whenGetScmCheckoutUrlEndpoint_thenReturnScmCheckoutUrl() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getScmCheckoutUrl()).thenReturn("statistics.endpoint.scmCheckoutUrl");
        //when
        String url = PropertyLoader.getScmCheckoutEndPoint();

        //then
        assertEquals("statistics.endpoint.scmCheckoutUrl", url);
    }

    @Test
    public void givenNoScmCheckoutUrl_whenGetScmCheckoutUrlEndpoint_thenReturnScmCheckoutUrl() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getScmCheckoutUrl()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList describableList = mock(DescribableList.class);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(new ArrayList<EnvironmentVariablesNodeProperty>());
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        String url = PropertyLoader.getScmCheckoutEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/scmCheckouts", url);
    }

    @Test
    public void givenEnvVars_whenGetScmCheckoutUrlEndpoint_thenReturnScmCheckoutUrl() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getScmCheckoutUrl()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        List<EnvironmentVariablesNodeProperty> environmentVariablesNodePropertyList = new ArrayList<>();
        EnvironmentVariablesNodeProperty envVar = mock(EnvironmentVariablesNodeProperty.class);
        Map<String, String> envVarMap = new HashMap<>();
        envVarMap.put("statistics.endpoint.scmCheckoutUrl", "http://cistats.mycompany.com/api/");
        Mockito.when(envVar.getEnvVars()).thenReturn(new EnvVars(envVarMap));
        environmentVariablesNodePropertyList.add(envVar);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(environmentVariablesNodePropertyList);
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        String url = PropertyLoader.getScmCheckoutEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/", url);
    }

    @Test
    public void givenEmptyEnvVars_whenGetScmCheckoutUrlEndpoint_thenReturnScmCheckoutUrl() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getScmCheckoutUrl()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        List<EnvironmentVariablesNodeProperty> environmentVariablesNodePropertyList = new ArrayList<>();
        EnvironmentVariablesNodeProperty envVar = mock(EnvironmentVariablesNodeProperty.class);
        Map<String, String> envVarMap = new HashMap<>();
        envVarMap.put("statistics.endpoint.scmCheckoutUrl", "");
        Mockito.when(envVar.getEnvVars()).thenReturn(new EnvVars(envVarMap));
        environmentVariablesNodePropertyList.add(envVar);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(environmentVariablesNodePropertyList);
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        String url = PropertyLoader.getScmCheckoutEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/scmCheckouts", url);
    }

    @Test
    public void givenEmptyScmCheckoutUrl_whenGetScmCheckoutUrlEndpoint_thenReturnScmCheckoutUrl() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getScmCheckoutUrl()).thenReturn("");
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(new ArrayList<>());
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        String url = PropertyLoader.getScmCheckoutEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/scmCheckouts", url);
    }

    @Test
    public void givenScmCheckoutInfo_whenGetScmCheckoutInfo_thenReturnScmCheckoutInfo() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getScmCheckoutInfo()).thenReturn(false);
        //when
        Boolean info = PropertyLoader.getScmCheckoutInfo();

        //then
        assertEquals(false, info);
    }

    @Test
    public void givenNoScmCheckoutInfo_whenGetScmCheckoutInfoEndpoint_thenReturnScmCheckoutInfo() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getScmCheckoutInfo()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList describableList = mock(DescribableList.class);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(new ArrayList<EnvironmentVariablesNodeProperty>());
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        Boolean info = PropertyLoader.getScmCheckoutInfo();

        //then
        assertEquals(true, info);
    }

    @Test
    public void givenEnvVars_whenGetScmCheckoutInfoEndpoint_thenReturnScmCheckoutInfo() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getScmCheckoutInfo()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        List<EnvironmentVariablesNodeProperty> environmentVariablesNodePropertyList = new ArrayList<>();
        EnvironmentVariablesNodeProperty envVar = mock(EnvironmentVariablesNodeProperty.class);
        Map<String, String> envVarMap = new HashMap<>();
        envVarMap.put("statistics.endpoint.scmCheckoutInfo", "true");
        Mockito.when(envVar.getEnvVars()).thenReturn(new EnvVars(envVarMap));
        environmentVariablesNodePropertyList.add(envVar);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(environmentVariablesNodePropertyList);
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        Boolean info = PropertyLoader.getScmCheckoutInfo();

        //then
        assertEquals(true, info);
    }

    @Test
    public void givenEmptyEnvVars_whenGetScmCheckoutInfoEndpoint_thenReturnScmCheckoutInfo() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getScmCheckoutInfo()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        List<EnvironmentVariablesNodeProperty> environmentVariablesNodePropertyList = new ArrayList<>();
        EnvironmentVariablesNodeProperty envVar = mock(EnvironmentVariablesNodeProperty.class);
        Map<String, String> envVarMap = new HashMap<>();
        envVarMap.put("statistics.endpoint.scmCheckoutInfo", "");
        Mockito.when(envVar.getEnvVars()).thenReturn(new EnvVars(envVarMap));
        environmentVariablesNodePropertyList.add(envVar);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(environmentVariablesNodePropertyList);
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        Boolean info = PropertyLoader.getScmCheckoutInfo();

        //then
        assertEquals(true, info);
    }

    @Test
    public void givenEmptyScmCheckoutInfo_whenGetScmCheckoutInfoEndpoint_thenReturnScmCheckoutInfo() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getScmCheckoutInfo()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(new ArrayList<>());
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        Boolean info = PropertyLoader.getScmCheckoutInfo();

        //then
        assertEquals(true, info);
    }

    @Test
    public void givenBuildStepInfo_whenGetBuildStepInfo_thenReturnBuildStepInfo() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getBuildStepInfo()).thenReturn(false);
        //when
        Boolean info = PropertyLoader.getBuildStepInfo();

        //then
        assertEquals(false, info);
    }

    @Test
    public void givenNoBuildStepInfo_whenGetBuildStepInfoEndpoint_thenReturnBuildStepInfo() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getBuildStepInfo()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList describableList = mock(DescribableList.class);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(new ArrayList<EnvironmentVariablesNodeProperty>());
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        Boolean info = PropertyLoader.getBuildStepInfo();

        //then
        assertEquals(true, info);
    }

    @Test
    public void givenEnvVars_whenGetBuildStepInfoEndpoint_thenReturnBuildStepInfo() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getBuildStepInfo()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        List<EnvironmentVariablesNodeProperty> environmentVariablesNodePropertyList = new ArrayList<>();
        EnvironmentVariablesNodeProperty envVar = mock(EnvironmentVariablesNodeProperty.class);
        Map<String, String> envVarMap = new HashMap<>();
        envVarMap.put("statistics.endpoint.buildStepInfo", "true");
        Mockito.when(envVar.getEnvVars()).thenReturn(new EnvVars(envVarMap));
        environmentVariablesNodePropertyList.add(envVar);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(environmentVariablesNodePropertyList);
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        Boolean info = PropertyLoader.getBuildStepInfo();

        //then
        assertEquals(true, info);
    }

    @Test
    public void givenEmptyEnvVars_whenGetBuildStepInfoEndpoint_thenReturnBuildStepInfo() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getBuildStepInfo()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        List<EnvironmentVariablesNodeProperty> environmentVariablesNodePropertyList = new ArrayList<>();
        EnvironmentVariablesNodeProperty envVar = mock(EnvironmentVariablesNodeProperty.class);
        Map<String, String> envVarMap = new HashMap<>();
        envVarMap.put("statistics.endpoint.buildStepInfo", "");
        Mockito.when(envVar.getEnvVars()).thenReturn(new EnvVars(envVarMap));
        environmentVariablesNodePropertyList.add(envVar);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(environmentVariablesNodePropertyList);
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        Boolean info = PropertyLoader.getBuildStepInfo();

        //then
        assertEquals(true, info);
    }

    @Test
    public void givenEmptyBuildStepInfo_whenGetBuildStepInfoEndpoint_thenReturnBuildStepInfo() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getBuildStepInfo()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(new ArrayList<>());
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        Boolean info = PropertyLoader.getBuildStepInfo();

        //then
        assertEquals(true, info);
    }

    @Test
    public void givenBuildInfo_whenGetBuildInfo_thenReturnBuildInfo() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getBuildInfo()).thenReturn(false);
        //when
        Boolean info = PropertyLoader.getBuildInfo();

        //then
        assertEquals(false, info);
    }

    @Test
    public void givenNoBuildInfo_whenGetBuildInfoEndpoint_thenReturnBuildInfo() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getBuildInfo()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList describableList = mock(DescribableList.class);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(new ArrayList<EnvironmentVariablesNodeProperty>());
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        Boolean info = PropertyLoader.getBuildInfo();

        //then
        assertEquals(true, info);
    }

    @Test
    public void givenEnvVars_whenGetBuildInfoEndpoint_thenReturnBuildInfo() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getBuildInfo()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        List<EnvironmentVariablesNodeProperty> environmentVariablesNodePropertyList = new ArrayList<>();
        EnvironmentVariablesNodeProperty envVar = mock(EnvironmentVariablesNodeProperty.class);
        Map<String, String> envVarMap = new HashMap<>();
        envVarMap.put("statistics.endpoint.buildInfo", "true");
        Mockito.when(envVar.getEnvVars()).thenReturn(new EnvVars(envVarMap));
        environmentVariablesNodePropertyList.add(envVar);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(environmentVariablesNodePropertyList);
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        Boolean info = PropertyLoader.getBuildInfo();

        //then
        assertEquals(true, info);
    }

    @Test
    public void givenEmptyEnvVars_whenGetBuildInfoEndpoint_thenReturnBuildInfo() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getBuildInfo()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        List<EnvironmentVariablesNodeProperty> environmentVariablesNodePropertyList = new ArrayList<>();
        EnvironmentVariablesNodeProperty envVar = mock(EnvironmentVariablesNodeProperty.class);
        Map<String, String> envVarMap = new HashMap<>();
        envVarMap.put("statistics.endpoint.buildInfo", "");
        Mockito.when(envVar.getEnvVars()).thenReturn(new EnvVars(envVarMap));
        environmentVariablesNodePropertyList.add(envVar);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(environmentVariablesNodePropertyList);
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        Boolean info = PropertyLoader.getBuildInfo();

        //then
        assertEquals(true, info);
    }

    @Test
    public void givenEmptyBuildInfo_whenGetBuildInfoEndpoint_thenReturnBuildInfo() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getBuildInfo()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(new ArrayList<>());
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        Boolean info = PropertyLoader.getBuildInfo();

        //then
        assertEquals(true, info);
    }

    @Test
    public void givenQueueInfo_whenGetQueueInfo_thenReturnQueueInfo() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getQueueInfo()).thenReturn(false);
        //when
        Boolean info = PropertyLoader.getQueueInfo();

        //then
        assertEquals(false, info);
    }

    @Test
    public void givenNoQueueInfo_whenGetQueueInfoEndpoint_thenReturnQueueInfo() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getQueueInfo()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList describableList = mock(DescribableList.class);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(new ArrayList<EnvironmentVariablesNodeProperty>());
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        Boolean info = PropertyLoader.getQueueInfo();

        //then
        assertEquals(true, info);
    }

    @Test
    public void givenEnvVars_whenGetQueueInfoEndpoint_thenReturnQueueInfo() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getQueueInfo()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        List<EnvironmentVariablesNodeProperty> environmentVariablesNodePropertyList = new ArrayList<>();
        EnvironmentVariablesNodeProperty envVar = mock(EnvironmentVariablesNodeProperty.class);
        Map<String, String> envVarMap = new HashMap<>();
        envVarMap.put("statistics.endpoint.queueInfo", "true");
        Mockito.when(envVar.getEnvVars()).thenReturn(new EnvVars(envVarMap));
        environmentVariablesNodePropertyList.add(envVar);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(environmentVariablesNodePropertyList);
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        Boolean info = PropertyLoader.getQueueInfo();

        //then
        assertEquals(true, info);
    }

    @Test
    public void givenEmptyEnvVars_whenGetQueueInfoEndpoint_thenReturnQueueInfo() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getQueueInfo()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        List<EnvironmentVariablesNodeProperty> environmentVariablesNodePropertyList = new ArrayList<>();
        EnvironmentVariablesNodeProperty envVar = mock(EnvironmentVariablesNodeProperty.class);
        Map<String, String> envVarMap = new HashMap<>();
        envVarMap.put("statistics.endpoint.queueInfo", "");
        Mockito.when(envVar.getEnvVars()).thenReturn(new EnvVars(envVarMap));
        environmentVariablesNodePropertyList.add(envVar);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(environmentVariablesNodePropertyList);
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        Boolean info = PropertyLoader.getQueueInfo();

        //then
        assertEquals(true, info);
    }

    @Test
    public void givenEmptyQueueInfo_whenGetQueueInfoEndpoint_thenReturnQueueInfo() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getQueueInfo()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(new ArrayList<>());
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        Boolean info = PropertyLoader.getQueueInfo();

        //then
        assertEquals(true, info);
    }

    @Test
    public void givenProjectInfo_whenGetProjectInfo_thenReturnProjectInfo() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getProjectInfo()).thenReturn(false);
        //when
        Boolean info = PropertyLoader.getProjectInfo();

        //then
        assertEquals(false, info);
    }

    @Test
    public void givenNoProjectInfo_whenGetProjectInfoEndpoint_thenReturnProjectInfo() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getProjectInfo()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList describableList = mock(DescribableList.class);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(new ArrayList<EnvironmentVariablesNodeProperty>());
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        Boolean info = PropertyLoader.getProjectInfo();

        //then
        assertEquals(true, info);
    }

    @Test
    public void givenEnvVars_whenGetProjectInfoEndpoint_thenReturnProjectInfo() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getProjectInfo()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        List<EnvironmentVariablesNodeProperty> environmentVariablesNodePropertyList = new ArrayList<>();
        EnvironmentVariablesNodeProperty envVar = mock(EnvironmentVariablesNodeProperty.class);
        Map<String, String> envVarMap = new HashMap<>();
        envVarMap.put("statistics.endpoint.projectInfo", "true");
        Mockito.when(envVar.getEnvVars()).thenReturn(new EnvVars(envVarMap));
        environmentVariablesNodePropertyList.add(envVar);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(environmentVariablesNodePropertyList);
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        Boolean info = PropertyLoader.getProjectInfo();

        //then
        assertEquals(true, info);
    }

    @Test
    public void givenEmptyEnvVars_whenGetProjectInfoEndpoint_thenReturnProjectInfo() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getProjectInfo()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        List<EnvironmentVariablesNodeProperty> environmentVariablesNodePropertyList = new ArrayList<>();
        EnvironmentVariablesNodeProperty envVar = mock(EnvironmentVariablesNodeProperty.class);
        Map<String, String> envVarMap = new HashMap<>();
        envVarMap.put("statistics.endpoint.projectInfo", "");
        Mockito.when(envVar.getEnvVars()).thenReturn(new EnvVars(envVarMap));
        environmentVariablesNodePropertyList.add(envVar);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(environmentVariablesNodePropertyList);
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        Boolean info = PropertyLoader.getProjectInfo();

        //then
        assertEquals(true, info);
    }

    @Test
    public void givenEmptyProjectInfo_whenGetProjectInfoEndpoint_thenReturnProjectInfo() {
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getProjectInfo()).thenReturn(null);
        Jenkins jenkinsMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(new ArrayList<>());
        Mockito.when(jenkinsMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        //when
        Boolean info = PropertyLoader.getProjectInfo();

        //then
        assertEquals(true, info);
    }
}
