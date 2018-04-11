[![Build Status](https://travis-ci.org/jenkinsci/statistics-gatherer-plugin.svg?branch=master)](https://travis-ci.org/jenkinsci/statistics-gatherer-plugin)   [![Coverage Status](https://coveralls.io/repos/github/jenkinsci/statistics-gatherer-plugin/badge.svg?branch=master)](https://coveralls.io/github/jenkinsci/statistics-gatherer-plugin?branch=master)


Jenkins Statistics gatherer Plugin
======================================

This plugin gather information on specific events on jenkins and sends them to an external API. That way, you can get the statistics that matters for your needs.

Issues
==========

If you have an issue with this plugin, please open a ticket on [issues.jenkins-ci.org](https://issues.jenkins-ci.org/issues/?jql=component%20%3D%20statistics-gatherer-plugin) using `statistics-gatherer-plugin` component.

Documentation
==============
In this section, you'll see which data is sent when. Please note that we do not consider adding a field to a JSON as a breaking change. However, removing one is considered as a breaking change.

Build
-------
At the start of a build, the following data is sent
``` json
{
	"queueTime": 29,
	"result": "INPROGRESS",
	"ciUrl": "http://localhost:8080/jenkins/",
	"contextId": 102490963,
	"fullJobName": "jenkins_test",
	"parameters": {
	    "someParams": "someValue"
	},
	"buildUrl": "aUrl",
	"buildCause": "Started by anonymous",
	"startTime": 1469453903,
	"number": 252,
	"startedUsername": "anonymous",
	"jobName": "jenkins_test",
	"slaveInfo": {
		"slaveName": "optimusprime",
		"executor": "1",
		"label": "master, windows,"
	},
	"scmInfo": {
		"url": "aGithubUrl",
		"commit": "aCommitHash",
		"branch": "origin/master"
	},
	"startedUserId": "anonymous"
}
```

On the finalize part of a build, the following JSON is sent.
```json
{
	"result": "ABORTED",
	"endTime": 1469453903,
	"ciUrl": "http://localhost:8080/jenkins/",
	"fullJobName": "jenkins_test",
	"buildUrl": "aUrl",
	"number": 252,
	"jobName": "jenkins_test",
	"duration": 9452,
}
```
Results can either be "SUCCESS", "ABORTED", or "FAILURE"

If the Build Failure Cause plugin is installed and the build failed, it will return the following JSON:
```json
{
    "result" : "ABORTED",
    "endTime" : 1469453903,
    "ciUrl" : "http://localhost:8080/jenkins/",
    "fullJobName" : "jenkins_test",
    "buildFailureCauses" : [
        {
            "name" : "aborted",
            "categories" : [
                "aCategory"
            ],
            "id" : "ccff5d15-1003-4570-b38f-f844255c6be1",
            "description" : "Build was manually aborted"
        },
        {...}
    ],
    "buildUrl" : "job/jenkins_test/252/",
    "number": 252,
    "jobName": "jenkins_test",
    "duration": 9452
}
```

Queue
------
When a job enters in the queue, the following JSON will be sent:
```json
{
    "entryTime": 1469455391674,
    "jenkinsQueueId": 191277,
    "jobName": "Projects » AProject » Blop » Foo » OnCommit",
    "queueCauses": [
        {
            "reasonForWaiting": "In the quiet period. Expires in 4.9 sec",
            "exitTime": null,
            "entryTime": 1469455391674,
            "type": "waiting"
        }
    ],
    "ciUrl": "http://jenkins.localhost/",
}
```
When a job enters a new state in the queue, it will send the following JSON:
```json
{
    "entryTime": 1469455391674,
    "jenkinsQueueId": 191277,
    "jobName": "Projects » AProject » Blop » Foo » OnCommit",
    "queueCauses": [
        {
            "reasonForWaiting": "In the quiet period. Expires in 4.9 sec",
            "exitTime": 1469455391674,
            "entryTime": null,
            "type": "waiting"
        }
    ],
    "ciUrl": "http://jenkins.localhost/",
}
```

When a job leaves a state in the queue, it will send the following JSON:

```json
{
    "startedBy": "SCM",
    "status": "ENTERED",
    "entryTime": 1469455391674,
    "duration": 0,
    "jenkinsQueueId": 191277,
    "jobName": "Projects » AProject » Blop » Foo » OnCommit",
    "queueCauses": [
        {
            "reasonForWaiting": "In the quiet period. Expires in 4.9 sec",
            "exitTime": null,
            "entryTime": 1469455391674,
            "type": "waiting"
        }
    ],
    "ciUrl": "http://jenkins.localhost/",
}
```

When a job leaves the queue, the following JSON will be sent:
```json
{
    "startedBy": "SCM",
    "status": "LEFT",
    "duration": 15,
    "entryTime": 1469455391674,
    "jobName": "Projects » AProject » Blop » Foo » OnCommit",
    "jenkinsQueueId": 191283,
    "contextId": 12343,
    "ciUrl": "http://jenkins.localhost/",
    "exitTime": 1469455391674
}
```

Note that the contextId can be used to link job in the queues with the corresponding build.

Project/Job
------------

When a job is created, the following JSON will be sent:

```json
{
   "name": "aJob",
   "createdDate": 1469455391674,
   "userId": "anonymous",
   "userName": "anonymous",
   "ciUrl": "http://localhost:9090/jenkins/",
   "status": "ACTIVE",
   "configFile": "<?xml version='1.0' encoding='UTF-8'?>\n<project>\n  <keepDependencies>false<\/keepDependencies>\n  <properties/>\n  <scm class=\"hudson.scm.NullSCM\"/>\n  <canRoam>false<\/canRoam>\n  <disabled>false<\/disabled>\n  <blockBuildWhenDownstreamBuilding>false<\/blockBuildWhenDownstreamBuilding>\n  <blockBuildWhenUpstreamBuilding>false<\/blockBuildWhenUpstreamBuilding>\n  <triggers/>\n  <concurrentBuild>false<\/concurrentBuild>\n  <builders/>\n  <publishers/>\n  <buildWrappers/>\n<\/project>"
}
```

The configFile field is the XML corresponding to the job.

When a job is updated, the following JSON will be sent:
```json
{
   "name": "aJob",
   "updatedDate": 1469455391674,
   "userId": "anonymous",
   "userName": "anonymous",
   "ciUrl": "http://localhost:9090/jenkins/",
   "status": "ACTIVE",
   "configFile": "<?xml version='1.0' encoding='UTF-8'?>\n<project>\n  <keepDependencies>false<\/keepDependencies>\n  <properties/>\n  <scm class=\"hudson.scm.NullSCM\"/>\n  <canRoam>false<\/canRoam>\n  <disabled>false<\/disabled>\n  <blockBuildWhenDownstreamBuilding>false<\/blockBuildWhenDownstreamBuilding>\n  <blockBuildWhenUpstreamBuilding>false<\/blockBuildWhenUpstreamBuilding>\n  <triggers/>\n  <concurrentBuild>false<\/concurrentBuild>\n  <builders/>\n  <publishers/>\n  <buildWrappers/>\n<\/project>"
}
```

When a job is deleted, the following JSON will be sent:
```json
{
   "name": "aJob",
   "updatedDate": 1469455391674,
   "userId": "anonymous",
   "userName": "anonymous",
   "ciUrl": "http://localhost:9090/jenkins/",
   "status": "DELETED",
   "configFile": "<?xml version='1.0' encoding='UTF-8'?>\n<project>\n  <keepDependencies>false<\/keepDependencies>\n  <properties/>\n  <scm class=\"hudson.scm.NullSCM\"/>\n  <canRoam>false<\/canRoam>\n  <disabled>false<\/disabled>\n  <blockBuildWhenDownstreamBuilding>false<\/blockBuildWhenDownstreamBuilding>\n  <blockBuildWhenUpstreamBuilding>false<\/blockBuildWhenUpstreamBuilding>\n  <triggers/>\n  <concurrentBuild>false<\/concurrentBuild>\n  <builders/>\n  <publishers/>\n  <buildWrappers/>\n<\/project>"
}
```

Status can be "ACTIVE", "DISABLED" or "DELETED".


Build Steps
------------
On the start of a build step, the following JSON will be sent:

```json
{
    "startTime" : 1469453903,
    "endTime" : 0,
    "buildStepType" : "hudson.tasks.Shell",
    "buildStepId" : "hudson.tasks.Shell@1234",
    "buildUrl": "aUrl"
}
```

On the end of a build step, the following JSON will be sent:
```json
{
    "startTime" : 0,
    "endTime" : 1469453903,
    "buildStepType" : "hudson.tasks.Shell",
    "buildStepId" : "hudson.tasks.Shell@1234",
    "buildUrl": "aUrl"
}
```
The buildStepId is unique within a given build. So you can use the buildUrl and the buildStepId to merge the information from the start and the end of the build step.


Scm Checkout Information
-------------------------
At the start of the checkout, the following JSON will be sent:

```json
{
    "startTime" : 1469453903,
    "endTime" : 0,
    "buildUrl" : "aUrl"
}
```
At the end of the checkout, the following JSON will be sent:
```json
{
    "startTime" : 0,
    "endTime" : 1469453903,
    "buildUrl" : "aUrl"
}
```

If the checkout fails, there will be no second JSON.

Development
===========

Start the local Jenkins instance:

    mvn hpi:run


Jenkins Plugin Maven goals
--------------------------

	hpi:create  Creates a skeleton of a new plugin.

	hpi:hpi Builds the .hpi file

	hpi:hpl Generates the .hpl file

	hpi:run Runs Jenkins with the current plugin project

	hpi:upload Posts the hpi file to java.net. Used during the release.


How to install
--------------

Run

	mvn hpi:hpi

to create the plugin .hpi file.


To install:

1. copy the resulting ./target/statistics-gatherer.hpi file to the $JENKINS_HOME/plugins directory. Don't forget to restart Jenkins afterwards.

2. or use the plugin management console (http://example.com:8080/pluginManager/advanced) to upload the hpi file. You have to restart Jenkins in order to find the plugin in the installed plugins list.


Plugin releases
---------------

	mvn release:prepare release:perform -Dusername=user -Dpassword=******

Changelog
--------------
Please refer to the CHANGELOG.md file. 


License
-------

	(The MIT License)

	Permission is hereby granted, free of charge, to any person obtaining
	a copy of this software and associated documentation files (the
	'Software'), to deal in the Software without restriction, including
	without limitation the rights to use, copy, modify, merge, publish,
	distribute, sublicense, and/or sell copies of the Software, and to
	permit persons to whom the Software is furnished to do so, subject to
	the following conditions:

	The above copyright notice and this permission notice shall be
	included in all copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND,
	EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
	MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
	IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
	CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
	TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
	SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
