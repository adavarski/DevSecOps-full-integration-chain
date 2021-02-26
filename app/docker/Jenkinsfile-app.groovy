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

node(){
  try{

    stage('Clone Project'){

      git branch: branchName,
          credentialsId: 'gitlab_connection_adavarski',
          url: 'http://ec2-18-211-180-13.compute-1.amazonaws.com/adavarski/app.git'

    stage('Check Syntax'){

      stage('Check Syntax - Dockerfile'){
        docker.image("hadolint/hadolint").inside("-v ${WORKSPACE}:${WORKSPACE}/project") { c ->
          sh "ls ${WORKSPACE}/project " 	        

          echo 'DockerFile backend'
          sh "hadolint ${WORKSPACE}/project/visitors-service/Dockerfile"

	  echo "DockerFile fronten"
          sh "hadolint ${WORKSPACE}/project/visitors-webui/Dockerfile"
        }
      }


      stage('Check Syntax - Python'){
        docker.image("cytopia/pylint").inside("-v ${WORKSPACE}:/data --entrypoint=") { c ->
          echo "# pylint: disable=E0401"
          echo "# pylint: disable=C0116"
          echo "# pylint: disable=R1705"
          echo "# pylint: disable=W0622"
          echo "# pylint: disable=W0613"
          echo "# pylint: disable=C0103"

          sh "ls /data"
          sh "pylint /data/visitors-service/manage.py"
          sh "pylint /data/visitors-service/visitors/service/views.py"        
        }
      }
    }
  }



  } finally {
      cleanWs()
      Notifier currentBuild.result 
    }
}
