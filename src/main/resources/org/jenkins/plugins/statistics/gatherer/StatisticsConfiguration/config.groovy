package org.jenkins.plugins.statistics.gatherer.StatisticsConfiguration

def f=namespace(lib.FormTagLib)

f.section(title:_("Statistics Gatherer")) {
    f.entry(title:_("Queue URL"), field:"queueUrl") {
        f.textbox()
    }
    f.entry(title:_("Build URL"), field:"buildUrl") {
        f.textbox()
    }
    f.entry(title:_("Project URL"), field:"projectUrl") {
        f.textbox()
    }
    f.entry(title:_("Build Step URL"), field:"buildStepUrl") {
        f.textbox()
    }
    f.entry(title:_("SCM Checkout Info URL"), field:"scmCheckoutUrl") {
        f.textbox()
    }

    f.entry(title:_("Send Queue Info"), field:"queueInfo") {
        f.checkbox(default: true)
    }
    f.entry(title:_("Send Build Info"), field:"buildInfo") {
        f.checkbox(default: true)
    }
    f.entry(title:_("Send Project Info"), field:"projectInfo") {
        f.checkbox(default: true)
    }
    f.entry(title:_("Send Build Step Info"), field:"buildStepInfo") {
        f.checkbox(default: true)
    }
    f.entry(title:_("Send SCM Checkout Info"), field:"scmCheckoutInfo") {
        f.checkbox(default: true)
    }

    f.advanced(title: _("Advanced Settings")) {

        f.entry(title:_("Publish to Amazon SNS Queue"), field:"shouldPublishToAwsSnsQueue") {
            f.checkbox(default: instance.shouldPublishToAwsSnsQueue == true)
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
            f.checkbox(default: instance.shouldSendApiHttpRequests == true)
        }

        f.entry(title:_("Enable publish of events to LOGBack"), field:"shouldSendToLogback") {
            f.checkbox()
        }

        f.entry(title:_("LOGBack XML configuration URL"), field:"logbackConfigXmlUrl") {
            f.textbox()
        }
    }
}
