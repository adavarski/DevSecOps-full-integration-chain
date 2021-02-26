#!/usr/bin/env groovy

def call() {
  ansiblePlaybook (
    colorized: true,
    playbook: "${WORKSPACE}/deploy_application.yml",
    inventory: "${WORKSPACE}/hosts",
    credentialsId: "key_ssh_aws_ssh",
    vaultCredentialsId: "vault_key",
    hostKeyChecking: false,
    tags: branchName
  )
}
