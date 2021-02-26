#!/usr/bin/env groovy

def call() {

    mail bcc: '',
    body: "${currentBuild.result}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}",
    cc: '',
    from: 'Jenkins',
    replyTo: '',
    subject: "Jenkins Build ${currentBuild.result}: Job ${env.JOB_NAME}, on branch ${env.BRANCH_NAME}",
    to: 'davar@gmail.com'
}
