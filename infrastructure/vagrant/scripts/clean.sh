#!/bin/bash
systemctl stop NetworkManager
systemctl disable NetworkManager
systemctl stop firewalld
systemctl disable firewalld
sed -i 's/enforcing/disabled/g' /etc/selinux/config /etc/selinux/config
yum install iptables-services net-tools -y
yum update -y
cat <<EOT >>/etc/environment
LANG=en_US.utf-8
LC_ALL=en_US.utf-8
EOT
