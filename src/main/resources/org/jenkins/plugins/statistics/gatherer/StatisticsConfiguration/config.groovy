package org.jenkins.plugins.statistics.gatherer.StatisticsConfiguration

def f=namespace(lib.FormTagLib)

f.section(title:_("Statistics Gatherer")) {
    f.entry(title:_("Queue URL"), field:"queueUrl") {
        f.textbox(default: "http://ci.mycompany.com/api/queue")
    }
    f.entry(title:_("Build URL"), field:"buildUrl") {
        f.textbox(default: "http://ci.mycompany.com/api/build")
    }
    f.entry(title:_("Project URL"), field:"projectUrl") {
        f.textbox(default: "http://ci.mycompany.com/api/projects")
    }
    f.entry(title:_("BuildSteps URL"), field:"buildStepUrl") {
        f.textbox(default: "http://ci.mycompany.com/api/step")
    }
    f.entry(title:_("ScmCheckoutInfo URL"), field:"scmCheckoutUrl") {
        f.textbox(default: "http://ci.mycompany.com/api/scm")
    }

    f.entry(title:_("Send Queue Info"), field:"queueInfo") {
        f.checkbox(default: true)
    }
    f.entry(title:_("Send Build Info"), field:"buildInfo") {
        f.checkbox(default: true)
    }
    f.entry(title:_("Send Job Info"), field:"projectInfo") {
        f.checkbox(default: true)
    }
    f.entry(title:_("Send BuildSteps Info"), field:"buildStepInfo") {
        f.checkbox(default: true)
    }
    f.entry(title:_("Send Scm Checkout Info"), field:"scmCheckoutInfo") {
        f.checkbox(default: true)
    }

    f.advanced(title: _("Advanced Settings")) {

        f.entry(title:_("Publish to Amazon SNS Queue"), field:"shouldPublishToAwsSnsQueue") {
            f.checkbox(default: instance.shouldPublishToAwsSnsQueue == null ? false : instance.shouldPublishToAwsSnsQueue.equals(true));
        }

        f.entry(title:_("AWS Access Key"), field:"awsAccessKey") {
            f.textbox()
        }

        f.entry(title:_("AWS Secret Key"), field:"awsSecretKey") {
            f.textbox()
        }

        f.entry(title:_("SNS Topic ARN"), field:"snsTopicArn") {
            f.textbox()
        }

        f.entry(title:_("AWS Region"), field:"awsRegion") {
            f.textbox()
        }

        f.entry(title:_("Enable HTTP publishing?"), field:"shouldSendApiHttpRequests") {
            f.checkbox(default: instance.shouldSendApiHttpRequests == null ? true : instance.shouldSendApiHttpRequests.equals(true));
        }

        f.entry(title:_("Enable publish of events to LOGBack"), field:"shouldSendToLogback") {
            f.checkbox(default: false)
        }

        f.entry(title:_("LOGBack XML configuration URL"), field:"logbackConfigXmlUrl") {
            f.textbox()
        }
    }
}
