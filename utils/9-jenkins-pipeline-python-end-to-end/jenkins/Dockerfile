FROM jenkins/jenkins:lts
MAINTAINER adavarski <davar@gmail.com>

#Install Jenkins plugin to make this pipeline work
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

#Copy script to auto create user to jenkins init folder
COPY setupJenkins.groovy /usr/share/jenkins/ref/init.groovy.d/

#setup the docker container for scanners
USER root
RUN apt-get update && apt-get install -y \
	python-pip \
	curl \
	maven \
	git \
	perl \
	wget \
	kbtin \
	libnet-ssleay-perl \
	software-properties-common

#add public key for the multiverse repo which has nikto
RUN apt-key adv --keyserver keyserver.ubuntu.com --recv-keys 3B4FE6ACC0B21F32
RUN add-apt-repository 'deb http://archive.ubuntu.com/ubuntu bionic multiverse'
RUN add-apt-repository 'deb http://archive.ubuntu.com/ubuntu bionic-security multiverse'
RUN add-apt-repository 'deb http://archive.ubuntu.com/ubuntu bionic-updates multiverse'

ENV PYTHON_VERSION 3.5

RUN apt-get update \
    && apt-get dist-upgrade -y \
    && apt-get -y install python${PYTHON_VERSION} \
       libffi-dev \
       libpq-dev \
       libssl-dev \
       python3-dev \
       python3-pip \
       python3-setuptools \
       python3-venv \
       python3-wheel \
       build-essential \
    && rm -rf /var/lib/apt/lists/* \
    && ln -nsf /usr/bin/python3.5 /usr/bin/python

RUN apt-get update -y
RUN apt-get install -y nikto	
	
#Install virtualenv to isolate each project dependencies
RUN pip3 install virtualenv
#Install python SCA tool/dependency checker, license check
RUN pip3 install safety
RUN pip3 install liccheck
#Install the git history checker for secrets
RUN pip3 install trufflehog
#Install python SAST tool
RUN pip3 install bandit

# Used for authenticated DAST scan
RUN pip3 install selenium

#install the orchestration tool, boto for ec2 module, some lib upgrade under requests
RUN pip3 install ansible
RUN pip3 install boto 
RUN pip3 install boto3

# configure ansible to use right keys and not check host auth for this ephemeral/temp aws host
# Not authenticating existing/long-term hosts requiring relogins may lead to mitm.. be careful
COPY ansible.cfg /etc/ansible/ansible.cfg

# drop back to the regular jenkins user - good practice
USER jenkins
