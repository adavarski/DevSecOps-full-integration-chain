#!/usr/bin/env groovy

def call() {
  git branch: branchName,
  credentialsId: 'gitlab_connection_davar',
  url: 'http://ec2-52-87-161-2.compute-1.amazonaws.com/adavarski/docker-jmeter.git'
  sh "./run.sh -n -t jmeter_${branchName}.jmx -l results_${branchName}.jtl"
  sh "cat results_${branchName}.jtl"
}
