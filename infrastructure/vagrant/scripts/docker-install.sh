#!/bin/bash
     yum install -y yum-utils device-mapper-persistent-data lvm2 git 
     yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
     yum install -y docker-ce docker-ce-cli containerd.io
     systemctl start docker
     curl -L "https://github.com/docker/compose/releases/download/1.23.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
     chmod +x /usr/local/bin/docker-compose
     ln -sf /usr/local/bin/docker-compose /usr/bin/docker-compose
     cat > /etc/docker/daemon.json <<EOT
{
    "insecure-registries" : ["KYR"]
}
EOT
     usermod -aG docker $USER
     systemctl restart docker

