#!/bin/bash
curl --silent --location http://pkg.jenkins-ci.org/redhat-stable/jenkins.repo | sudo tee /etc/yum.repos.d/jenkins.repo
rpm --import https://jenkins-ci.org/redhat/jenkins-ci.org.key
yum update; yum install jenkins -y; systemctl enable jenkins
usermod --shell /bin/bash jenkins
 usermod -a -G docker jenkins
 cat >> /etc/sudoers <<EOT
jenkins ALL=(ALL)       NOPASSWD: ALL
EOT
rsync .......from backup tar.gz
systemctl restart jenkins
