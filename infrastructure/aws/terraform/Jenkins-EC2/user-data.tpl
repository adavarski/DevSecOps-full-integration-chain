#cloud-config
runcmd:
# get/setup instance name
 - hostnamectl set-hostname `curl -s http://169.254.169.254/latest/meta-data/public-hostname`
# Install jenkins
 - sudo apt update
 - sudo apt install -y openjdk-11-jre-headless
 - sudo sleep 60
 - wget -q -O - https://pkg.jenkins.io/debian/jenkins.io.key | sudo apt-key add -
 - sudo sh -c 'echo deb http://pkg.jenkins.io/debian-stable binary/ > /etc/apt/sources.list.d/jenkins.list'
 - sudo apt update
 - sudo apt install -y jenkins
 - sudo systemctl enable jenkins
 - sudo systemctl start jenkins
#Install docker
 - sudo apt install -y apt-transport-https ca-certificates curl software-properties-common
 - curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
 - sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu focal stable"
 - sudo apt update
 - sudo apt-cache policy docker-ce
 - sudo apt install -y docker-ce
 - sudo usermod -aG docker jenkins
 - sudo usermod -aG docker ubuntu
 - sudo systemctl enable docker
 - sudo systemctl start docker
# Install awscli
 - apt install -y python3-pip
 - pip3 install awscli
#Install terraform
 - curl https://releases.hashicorp.com/terraform/0.14.4/terraform_0.14.4_linux_amd64.zip -o /tmp/terraform_0.14.4_linux_amd64.zip
 - sudo apt install -y unzip
 - unzip /tmp/terraform_0.14.4_linux_amd64.zip
 - sudo mv terraform /usr/local/bin/
#Install pkgs (for J.docker pipeline plugin)
 - sudo apt install -y gnupg2 pass 
