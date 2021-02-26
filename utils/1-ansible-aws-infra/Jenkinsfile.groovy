
/* import shared library */
@Library('shared-library')_
// Define your secret project token here
def project_token = 'abcdefghijklmnopqrstuvwxyz0123456789ABCDEF'
def buildNum = env.BUILD_NUMBER
def branchName = env.BRANCH_NAME

// Reference the GitLab connection name from your Jenkins Global configuration (https://JENKINS_URL/configure, GitLab section)
properties([
    gitLabConnection('gitlab_connection'),
    pipelineTriggers([
        [
            $class: 'GitLabPushTrigger',
            branchFilterType: 'All',
            triggerOnPush: true,
            triggerOnMergeRequest: true,
            triggerOpenMergeRequestOnPush: "never",
            triggerOnNoteRequest: true,
            noteRegex: "Jenkins please retry a build",
            skipWorkInProgressMergeRequest: true,
            secretToken: project_token,
            ciSkip: false,
            setBuildDescription: true,
            addNoteOnMergeRequest: true,
            addCiMessage: true,
            addVoteOnMergeRequest: true,
            acceptMergeRequestOnSuccess: true,
            branchFilterType: "NameBasedFilter",
            includeBranchesSpec: "${branchName}",
            excludeBranchesSpec: "",
        ]

    ])
])

properties([
    pipelineTriggers([upstream(
        threshold: hudson.model.Result.SUCCESS,
        upstreamProjects: "MyPipelineSourceCode/${branchName}")
    ])
])


node(){
  try{

    stage('Clone Project'){

      git branch: branchName,
          credentialsId: 'gitlab_connection_adavarski',
          url: 'http://ec2-18-211-180-13.compute-1.amazonaws.com/adavarski/mypipeline.git'
    }

    stage('Check Syntax'){

      stage('Check Syntax - bash'){
        docker.image("koalaman/shellcheck-alpine:stable").inside("-v ${WORKSPACE}:${WORKSPACE}/project") { c ->
          script { bashCheck }
        }
      }

      stage('Check Syntax - yaml'){
        docker.image("sdesbure/yamllint").inside("-v ${WORKSPACE}:${WORKSPACE}/project") { c ->
          sh 'yamllint --version'
          //sh 'ls ${WORKSPACE}/project'
          sh "yamllint ${WORKSPACE}/project"
        }
      }

      stage('Check Syntax - markdown'){
        docker.image("ruby:alpine").inside("-v ${WORKSPACE}:${WORKSPACE}/project") { c ->
          sh 'apk --no-cache add git'
          sh 'gem install mdl'
          sh 'mdl --version'
          sh 'mdl --style all --warnings --git-recurse ${WORKSPACE}/project'
        }
      }
    }

    if (branchName == "dev"){

      stage('Development Environment'){

        stage('Development Environment - Deployment'){
          ansiblePlaybook (
            colorized: true,
            playbook: "${WORKSPACE}/deployment.yml",
            inventory: "${WORKSPACE}/hosts",
            credentialsId: "key_ssh_aws_ssh",
            vaultCredentialsId: "vault_key",
            hostKeyChecking: false,
            tags: branchName
          )

        }

        stage('Development Environment - Find vulnerability'){
          git branch: branchName,
              credentialsId: 'gitlab_connection_adavarski',
              url: 'http://ec2-18-211-180-13.compute-1.amazonaws.com/adavarski/mypipeline.git'

          stage('Development Environment - Find curl vulnerability'){
            docker.image("gauntlt/gauntlt").inside("-v /tmp/attack:${WORKSPACE}/attack --entrypoint=") { c ->
              sh 'gauntlt --version'
              sh 'ls ${WORKSPACE}/attack'
              sh 'gauntlt ${WORKSPACE}/attack/verbs.attack'
            }
          }

          stage('Development Environment - Find XSS vulnerability'){
            docker.image("gauntlt/gauntlt").inside("-v /tmp/attack:${WORKSPACE}/attack --entrypoint=") { c ->
              sh 'gauntlt --version'
              sh 'ls ${WORKSPACE}/attack'
              sh 'gauntlt ${WORKSPACE}/attack/xss.attack'
            }
          }

        }

      }
    }

    if (branchName == "master"){

      stage('Production Environment'){

        stage('Production Environment - Deployment'){
          ansiblePlaybook (
            colorized: true,
            playbook: "${WORKSPACE}/deployment.yml",
            inventory: "${WORKSPACE}/hosts",
            credentialsId: "key_ssh_aws_ssh",
            vaultCredentialsId: "vault_key",
            hostKeyChecking: false,
            tags: branchName
          )
        }

      }
    }

  } finally {
      cleanWs()
      Notifier currentBuild.result

    }
}
