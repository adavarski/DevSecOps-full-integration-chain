#!/bin/bash

# Install minikube and kubectl the same k8s minor version : v1.16.2
curl -Lo minikube https://github.com/kubernetes/minikube/releases/download/v1.5.2/minikube-linux-amd64 && chmod +x minikube && sudo mv ./minikube /usr/local/bin/
curl -LO https://storage.googleapis.com/kubernetes-release/release/v1.16.2/bin/linux/amd64/kubectl && chmod +x ./kubectl && sudo mv ./kubectl /usr/local/bin/

# Run minikube and wait 
minikube start --cpus 2 --memory 4096
minikube addons enable ingress
sleep 300

