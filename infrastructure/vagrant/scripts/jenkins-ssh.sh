#!/bin/bash

mkdir -p /var/lib/jenkins/.ssh
chmod 700 /var/lib/jenkins/.ssh
touch /var/lib/jenkins/.ssh/authorized_keys
chmod 600 /var/lib/jenkins/.ssh/authorized_keys

cat >> /var/lib/jenkins/.ssh/authorized_keys <<EOT
ssh-rsa KYR 
EOT

chown -R jenkins /var/lib/jenkins/.ssh
