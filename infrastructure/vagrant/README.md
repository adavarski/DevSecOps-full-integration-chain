# Packer CentOS template
Packer templates to bake VirtualBox image (vagrant)

### Purpose
This repository store sample Packer templates required to create a Vagrant virtualbox base CentOS 7 x86_64 boxes using Packer for jenkins (master and slave)

### Requirements
The following software must be installed/present on your local machine before you can use Packer to build the Vagrant box file:

* Packer
* VirtualBox (needed to build the VirtualBox box)

### CentOS Packer Template example (jenkins slave):

* [template-jenkins-slave.json](https://github.com/adavarski/packer-vagrant-vbox-centos-7.6-jenkins-POC/blob/master/template-jenkins-slave.json)

### Usage
Make sure all the required software is installed, then cd to the directory containing this repo files, and run:
```
$ packer build template-jenkins-master.json
$ packer build template-jenkins-slave.json
``` 
After a few minutes, Packer should tell you the box was generated successfully

```
vagrant box add jenkins-slave builds/virtualbox-centos7-minimal.box --force
vagrant up
```
