#!/usr/bin/env groovy

def call(String buildResult) {
  if ( buildResult == "SUCCESS" ) {
    mail bcc: '',
    body: "${currentBuild.result}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}",
    cc: '',
    from: 'Jenkins',
    replyTo: '',
    subject: "Jenkins Build ${currentBuild.result}: Job ${env.JOB_NAME} was succeded, on branch ${env.BRANCH_NAME}",
    to: 'davar@gmail.com'

    slackSend color: "good", message: "CONGRATULATION: Job ${env.JOB_NAME} with buildnumber ${env.BUILD_NUMBER} was successful ! more info ${env.BUILD_URL}"
  }

  else if( buildResult == "FAILURE" ) {
    mail bcc: '',
    body: "${currentBuild.result}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}",
    cc: '',
    from: 'Jenkins',
    replyTo: '',
    subject: "Jenkins Build ${currentBuild.result}: Job ${env.JOB_NAME} was failed, on branch ${env.BRANCH_NAME}",
    to: 'davar@gmail.com'

    slackSend color: "danger", message: "BAD NEWS:Job ${env.JOB_NAME} with buildnumber ${env.BUILD_NUMBER} was failed ! more info ${env.BUILD_URL}"
  }

  else if( buildResult == "UNSTABLE" ) {
    mail bcc: '',
    body: "${currentBuild.result}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}",
    cc: '',
    from: 'Jenkins',
    replyTo: '',
    subject: "Jenkins Build ${currentBuild.result}: Job ${env.JOB_NAME} was unstable, on branch ${env.BRANCH_NAME}",
    to: 'davar@gmail.com'

    slackSend color: "warning", message: "BAD NEWS:Job ${env.JOB_NAME} with buildnumber ${env.BUILD_NUMBER} was unstable ! more info ${env.BUILD_URL}"

  }

  else {
    mail bcc: '',
    body: "${currentBuild.result}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}",
    cc: '',
    from: 'Jenkins',
    replyTo: '',
    subject: "Jenkins Build ${currentBuild.result}: Job ${env.JOB_NAME} was succeded, on branch ${env.BRANCH_NAME}",
    to: 'davar@gmail.com'
    slackSend color: "danger", message: "BAD NEWS:Job ${env.JOB_NAME} with buildnumber ${env.BUILD_NUMBER} its result was unclear ! more info ${env.BUILD_URL}"
  }
}
