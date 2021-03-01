### Utils/Tools

+ [Shared-libray for Jenkins](https://github.com/adavarski/DevSecOps-full-integration-chain/tree/main/utils/0-jenkins-shared-library)

+ Ansible roles & playbooks for infrastructure: [ansible roles & playbooks](https://github.com/adavarski/DevSecOps-full-integration-chain/tree/main/utils/1-ansible-aws-infra) for AWS environment && [ansible roles & playbooks](https://github.com/adavarski/DevSecOps-full-integration-chain/tree/main/utils/2-ansible-vagrant-infra) for Vagrant environment
  + Deployment Infrastructure
  + Install Prerequiest
  + Set Environment Build
  + Scan and Push atrtifact
  + Deploy Application in preproduction
  + Deploy Application in production


+ [Ansible DevOps services/tools](https://github.com/adavarski/DevSecOps-full-integration-chain/tree/main/utils/3-ansible-devops-utils)
  + GitLab 
  + Jenkins
  + Ansible Tower

+ [Ansible DevSecOps general services/tools](https://github.com/adavarski/DevSecOps-full-integration-chain/tree/main/utils/4-ansible-devsecops-general-utils)

  + Automating Web Application Security Testing Using OWASP ZAP
  + Vulnerability Scanning with Nessus
  + Security Hardening for Applications and Networks

+ [Ansible DevSecOps continuous security scanning for docker containers services/tools](https://github.com/adavarski/DevSecOps-full-integration-chain/tree/main/utils/5-ansible-devsecops-docker-utils)


+ [Ansible DevSecOps AWS security audit/scanning services/tools](https://github.com/adavarski/DevSecOps-full-integration-chain/tree/main/utils/6-ansible-devsecops-aws-utils)


+ [Log Monitoring and Serverless Automated Defense (Elastic Stack in AWS)](https://github.com/adavarski/DevSecOps-full-integration-chain/tree/main/utils/7-ansible-log-monitoring-elk-aws-serverless-utils)


+ [DevSecOps Jenkins pipelines (docker based)](https://github.com/adavarski/DevSecOps-full-integration-chain/tree/main/utils/8-jenkins-docker-utils)
  + OWASP Dependency-Check script for DevSecOps pipelines (react, python, node, etc.)
  + Docker Bandit SAST (Static Application Security Testing) for Python projects and DevSecOps pipelines (python)
  + Docker container for clair-scanner and for integration into a DevSecOps pipelines (docker containers vulnerability scan)

+ [Example: A Jenkins end-to-end DevSecOps pipeline for Python web application (docker based)](https://github.com/adavarski/DevSecOps-full-integration-chain/tree/main/utils/9-jenkins-pipeline-python-end-to-end):
  Jenkins instance/environment hosted on AWS EC2 (Ubuntu 18.04) or local environment (on your laptop/workstation/on-prem ubuntu server).
  
<img src="https://github.com/adavarski/DevSecOps-full-integration-chain/blob/main/utils/9-jenkins-pipeline-python-end-to-end/pictures/DevSecOps-pipeline-full.png" width="900">

<img src="https://github.com/adavarski/DevSecOps-full-integration-chain/blob/main/utils/9-jenkins-pipeline-python-end-to-end/pictures/DevSecOps-pipeline-steps-UI.png" width="900">

<img src="https://github.com/adavarski/DevSecOps-full-integration-chain/blob/main/utils/9-jenkins-pipeline-python-end-to-end/pictures/DevSecOps-workspace.png" width="900">

  + Checkout project - check out python application project repository with XSS vulnerability (https://github.com/adavarski/Python-app-DevSecOps-XSS)
  + git secret check - check there is no password/token/keys/secrets accidently commited to project github (trufflehog
  + SCA - check external dependencies/libraries used by the project have no known vulnerabilities (safety)
  + SAST - static analysis of the application source code for exploits, bugs, vulnerabilites (Bandit)
  + Container audit - audit the container that is used to deploy the python application (Lynis)
  + DAST - deploy the application, register, login, attack & analyse it from the frontend as authenticated user (Nikto + Selenium + python custom script for DAST automation)
  + System security audit - analyse at the security posture of the system hosting the application (Lynis)
  + WAF - deploy application with WAF which will filter malicious requests according to OWASP core ruleset (owasp/modsecurity-crs)

















