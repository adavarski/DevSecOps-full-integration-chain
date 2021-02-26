### Setup k8s minikube-based development environment
```
$ ./setup_environment.sh
```

Check:

```
$ kubectl version
Client Version: version.Info{Major:"1", Minor:"16", GitVersion:"v1.16.2", GitCommit:"c97fe5036ef3df2967d086711e6c0c405941e14b", GitTreeState:"clean", BuildDate:"2019-10-15T19:18:23Z", GoVersion:"go1.12.10", Compiler:"gc", Platform:"linux/amd64"}
Server Version: version.Info{Major:"1", Minor:"16", GitVersion:"v1.16.2", GitCommit:"c97fe5036ef3df2967d086711e6c0c405941e14b", GitTreeState:"clean", BuildDate:"2019-10-15T19:09:08Z", GoVersion:"go1.12.10", Compiler:"gc", Platform:"linux/amd64"}
```

Note: Server & Client has to be the same Minor versions.

### Manifest-based installation

```
$ kubectl apply -f manifests/database.yaml
$ kubectl apply -f manifests/backend.yaml
$ kubectl apply -f manifests/frontend.yaml
$ minikube ip
192.168.99.100
```

You can access the Visitors Site by opening a browser and
going to http://192.168.99.100:30686.

<img src="https://github.com/adavarski/DevSecOps-full-integration-chain/blob/main/pictures/visitors-dashboard.png?raw=true" width="650">


Cleaning up:

```
$ kubectl delete -f manifests/frontend.yaml
$ kubectl delete -f manifests/backend.yaml
$ kubectl delete -f manifests/database.yaml

```
### Ref1: minikube + GitLab example:

https://github.com/adavarski/minikube-gitlab-development

### Ref2: Kubernetes Operators (Helm, Ansible, Go) example:

https://github.com/adavarski/k8s-operators-playground


