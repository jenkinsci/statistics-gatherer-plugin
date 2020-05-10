# Change Log
All notable changes to this project will be documented in this file.

## 2.0.5 - 2020-05-08

- Add functionnality to publish on AWS SQS.

## 2.0.4 - 2020-04-03

- Add functionnality to publish on AWS SNS.

## 2.0.3 - 2018-05-11

- [JENKINS-51266](https://issues.jenkins-ci.org/browse/JENKINS-51266) Flag the plugin as dynamically-loadable

## 2.0.2 - 2018-05-08

- [JENKINS-51085](https://issues.jenkins-ci.org/browse/JENKINS-51085) Auto-reload LOGBack config XML

## 2.0.1 - 2018-05-02

- Disable LOGBack when [LOGBack NATS Appender](https://plugins.jenkins.io/logback-nats-appender) is not installed
- Disable HTTP log notification by default, unless explicitly configured

## 2.0 - 2018-04-30

- Allow the send events to LOGBack appenders
- Upgrade plugin version and target to Jenkins 2.x

## 1.1.3 - 2018-04-26

- Luca Milanesio started as additional maintainer
- [JENKINS-43440](https://issues.jenkins-ci.org/browse/JENKINS-43440) Support for pipeline jobs
- Do not send events to HTTP endspoint by default
- Use blank URLs as defaults for endpoints
- Fails gracefully when HTTP notification fails
- docs: corrected link to component bugs
- add ScmInfo on build completion

## 1.1.2 - 2017-04-11

- Fix NullPointerException for not configured Booleans
- Fix bad configuration meaning and default values

## 1.1.1 - 2017-02-15

- Fix a bug that was preventing an event being send for a build in a pipeline

## 1.1.0 - 2017-01-12

- Add functionnality to publish on AWS SNS. 
- Update readme to expose Changelog.
- Better unit test coverage


## 1.0.1 - 2016-07-26

- First official release on Jenkins
