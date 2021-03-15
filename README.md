
## DevSecOps full integration chain 

Jenkins Pipelines, Docker, k8s, Ansible, Clair, Nessus, Nmap NSE, OWASP Dependency-Check, OWASP ZAP, Nikto, Lynis, Bandit, Gauntlt, etc.

### Utils/Tools
- Cloud Production Infrastructure: AWS environment (IaC: Terraform/Ansible)
- Local Development Infrastructure: Vagrant environment (IaC: Packer + Vagrant + Virtualbox + Ansible)
- k8s Infrastructure: Development (minikube)/Production (AWS:KOPS) (IaC: k8s manifests/helm charts/k8s operators)
- Container Engine : Docker 
- Configuration Managment: Ansible 
- Source Code Managment : GitLab/GitHub
- Scheduling : Jenkins
- Security: Clair, Nessus, Nmap NSE, OWASP Dependency-Check, OWASP ZAP, Nikto, Lynis, Bandit, Gauntlt, etc.
- Performance Testing: JMeter
- Notification: Slack, Email

Note: k8s 
- Container Engine: CRI-O/containerd/Docker
- Configuration Managment: k8s manifests/helm charts/k8s operators

## Project 

### The context
                
The objective of this project is to deploy the application by creating a complete DevSecOps-like integration chain. For this application to be functional, it is necessary to deploy a MySQL database, a backend and a frontend.
In this project, the problems of businesses, related to storage, to the control of their data and processes were taken into account.

### Infrastructure

#### Description

Cloud Production Infrastructure: AWS environment (IaC: Terraform/Ansible) && Local Development Infrastructure: Vagrant environment (IaC: Packer + Vagrant + Virtualbox + Ansible) 
 
We wanted to reproduce an enterprise-type infrastructure with 4 servers/VMs:
- A master type server which will contain the main applications and tools (GitLab, Jenkins, Ansible, Docker, etc.).
- A build server to build our artifact, do unit tests and security scan of images.
- A staging/pre-production server in order to carry out tests of our artifacts in same conditions of production.
- A production server in order to deploy our application which can be consumed.

Note. Vagrant environment: master VM(Jenkins master server) && build/staging/production VMs (Jenkins slave servers)

Note. k8s environment (development: minikube/production: AWS-KOPS)

#### Infrastructure Diagrams

- Infrastructure: Vagrant (development)

<img src="https://github.com/adavarski/DevSecOps-full-integration-chain/blob/main/pictures/infrastructure-vagrant.png?raw=true" width="550">

- Infrastructure: AWS (production: EC2, etc.)

<img src="https://github.com/adavarski/DevSecOps-full-integration-chain/blob/main/pictures/infrastructure-aws.png?raw=true" width="550">

- Infrastructure: k8s (AWS-k8s-KOPS)

<img src="https://github.com/adavarski/DevSecOps-full-integration-chain/blob/main/pictures/infrastructure-k8s-simple.png?raw=true" width="550">


### Choice and description of tools

+ DevOps:
  + Production Infrastructure deployed on the AWS cloud provider thanks to Terraform/Ansible in order to favor IaC.
  + Development Infrastructure deployed on a laptop/local workstation thanks to Vagrant/Ansible in order to favor IaC.
  + Deployment of a containerized GitLab CE instance and activation of the container registry in order to maintain mastery and control of data.
  + Using Docker to containerize the database, application as well as the frontend & backend in two different containers to favor agility.
  + Use of Ansible to configure our infrastructure and provisione it.
  + Implementation of a Gitflow to respect good practices. Creation of two branches:
    + The “master” branch which will be used only to deploy our infrastructure and our application in production.
    + The “dev” branch which will be used to develop the functionalities and carry out the tests.
    + Pull request in order to merge the “dev” branch on the “master” branch.
  + Use of Jenkins to orchestrate all stages and set up several pipelines.
  + Use notification space on the Slack collaborative platform and email to notify us of the state of the pipeline.
  + Generation of badges to inform employees.

+ DevSecOps:
  + Clair Image Scanner:
    + Identify vulnerabilities in built images
  + Pentest Scans/Attacks with Nessus, Nmap NSE, OpenSCAP, OWASP Dependency-Check, OWASP ZAP
    + Execute attack/security scans scenarios to identify vulnerabilities in our application using Nessus, Nmap NSE, OpenSCAP, OWASP Dependency-Check, OWASP ZAP
  + Attack generation with Gauntlt:
    + Execute attack scenarios to identify vulnerabilities in our application (xss attack;curl attack;etc.)

### Workflow

#### Description

Continuous Delivery on the **“dev”** branch:
+ Development code update via git,
  + Continuous Integration
    + Triggering of the first pipeline thanks to the push trigger and the webhook sent to Jenkins:
    + Analysis / linter and tests of the syntax of Pyhton and Dockerfile
    + Notification on Slack and by email of the result of this pipeline

+ If the pipeline is successful, triggering and automatic execution of another pipeline thanks to the success of the first:
  + Continuous Integration
     + Analysis / linter and tests of the syntax of mardown, bash, yml files and also of the Ansible syntax
     + Configuration of the environment on the build server, then build and test our artifacts (docker images) on the server.
     + **Security** : Vulnerability scan of builder images with Clair
     + Push our images on our container registry GitLab. Cleaning up the build environment.
     
  + Continuous Deployment
     + On the staging server, configuration of the environment, recovery of the necessary sources and deployment of our application in an environment close to production,
     + **Security** : Generation of attacks with several scenarios:
       + xss attack
       + curl attack (curl and xss) 
       + etc.
     + Several tests of the proper functioning of the application (frontend and backend)
     + important several load test with JMeter (frontend and backend)
     + Notification on Slack and by email of the result of this pipeline

+ If the pipeline is successful, set up a Pull Request for a manager to check all the pipelines and that they are working properly.
The manager decides to accept the Pull Request and therefore merge the "dev" branch on the "master" branch to deploy the application in production.

Continuous Delivery on the **”Master”** branch:
+ A new pipeline is triggered and executed automatically after the Merge Request:
  + Analysis / linter and tests of the syntax of mardown, bash, yml files and also of the Ansible syntax
  + The build and staging steps are voluntarily forgotten,
  + On the production server, configuration of the environment, recovery of the necessary sources and deployment of our application in the production environment
  + Several tests of the proper functioning (functional and load tests) of the application in production (frontend and backend)
  + Notification on Slack and by email of the result of this pipeline.

#### Workflow Diagram
------------
- Workflow DevSecOps (production: AWS-EC2) :

<img src="https://github.com/adavarski/DevSecOps-full-integration-chain/blob/main/pictures/workflow.png?raw=true" width="900">

- Workflow DevSecOps (production: AWS-k8s-KOPS) :

<img src="https://github.com/adavarski/DevSecOps-full-integration-chain/blob/main/pictures/workflow-k8s.png?raw=true" width="900">

------------


### Reference repository: 


+ [App source code development](https://github.com/adavarski/DevSecOps-full-integration-chain/tree/main/app "App source code development")


+ [Shared-libray for Jenkins](https://github.com/adavarski/DevSecOps-full-integration-chain/tree/main/utils/0-jenkins-shared-library)

+ Ansible roles & playbooks for infrastructure: [ansible roles & playbooks](https://github.com/adavarski/DevSecOps-full-integration-chain/tree/main/utils/1-ansible-aws-infra) for AWS environment && [ansible roles & playbooks](https://github.com/adavarski/DevSecOps-full-integration-chain/tree/main/utils/2-ansible-vagrant-infra) for Vagrant environment
  + Deployment Infrastructure
  + Install Prerequiest
  + Set Environment Build
  + Scan and Push atrtifact
  + Deploy Application in preproduction
  + Deploy Application in production


+ [Ansible DevOps CI/CD services/tools](https://github.com/adavarski/DevSecOps-full-integration-chain/tree/main/utils/3-ansible-devops-utils)
  + GitLab 
  + Jenkins
  + Ansible Tower

+ [Ansible DevSecOps general services/tools](https://github.com/adavarski/DevSecOps-full-integration-chain/tree/main/utils/4-ansible-devsecops-general-utils)

  + Automating Web Application Security Testing Using OWASP ZAP
  + Vulnerability Scanning with Nessus
  + Web Application Security Scanning (Nikto, etc.)
  + Security Hardening for Applications and Networks (Lynis, etc.) 

+ [Ansible DevSecOps continuous security scanning for docker containers services/tools](https://github.com/adavarski/DevSecOps-full-integration-chain/tree/main/utils/5-ansible-devsecops-docker-utils)


+ [Ansible DevSecOps AWS security audit/scanning services/tools](https://github.com/adavarski/DevSecOps-full-integration-chain/tree/main/utils/6-ansible-devsecops-aws-utils)


+ [Log Monitoring and Serverless Automated Defense (Elastic Stack in AWS)](https://github.com/adavarski/DevSecOps-full-integration-chain/tree/main/utils/7-ansible-log-monitoring-elk-aws-serverless-utils)


+ [DevSecOps Jenkins pipelines (docker based)](https://github.com/adavarski/DevSecOps-full-integration-chain/tree/main/utils/8-jenkins-docker-utils)
  + OWASP Dependency-Check script for DevSecOps pipelines (react, python, node, etc.)
  + Docker Bandit SAST (Static Application Security Testing) for Python projects and DevSecOps pipelines (python)
  + Docker container for clair-scanner and for integration into a DevSecOps pipelines (docker containers vulnerability scan)

+ [Example: A Jenkins end-to-end DevSecOps pipeline for Python web application (docker based)](https://github.com/adavarski/DevSecOps-full-integration-chain/tree/main/utils/9-jenkins-pipeline-python-end-to-end):
  Jenkins instance/environment hosted on AWS EC2 (Ubuntu 18.04) or local environment (on your laptop/workstation/on-prem ubuntu server).
  
  + Checkout project - check out python application project repository with XSS vulnerability (https://github.com/adavarski/Python-app-DevSecOps-XSS)
  + git secret check - check there is no password/token/keys/secrets accidently commited to project github (trufflehog)
  + SCA - check external dependencies/libraries used by the project have no known vulnerabilities (safety)
  + SAST - static analysis of the application source code for exploits, bugs, vulnerabilites (Bandit)
  + Container audit - audit the container that is used to deploy the python application (Lynis)
  + DAST - deploy the application, register, login, attack & analyse it from the frontend as authenticated user (Nikto + Selenium + python custom script for DAST automation)
  + System security audit - analyse at the security posture of the system hosting the application (Lynis)
  + WAF - deploy application with WAF which will filter malicious requests according to OWASP core ruleset (owasp/modsecurity-crs)

<img src="https://github.com/adavarski/DevSecOps-full-integration-chain/blob/main/utils/9-jenkins-pipeline-python-end-to-end/pictures/DevSecOps-pipeline-full.png" width="900">

<img src="https://github.com/adavarski/DevSecOps-full-integration-chain/blob/main/utils/9-jenkins-pipeline-python-end-to-end/pictures/DevSecOps-pipeline-steps-UI.png" width="900">



