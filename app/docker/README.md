
### Application Overview

• A web frontend, implemented in React

• A REST API, implemented in Python using the Django framework

• A database, using MySQL

### Build docker images

```
$ docker login

$ cd visitor-service
$ docker build --tag visitors-service:1.0.0 . 
$ docker push davarski/visitors-service:1.0.0

$ cd visitor-webui
$ docker build --tag visitors-webui:1.0.0 .
$ docker push davarski/visitors-webui:1.0.0
```

### Runing app on Docker (using docker-compose) 
```
docker-compose up -d
```
Example:
```
$ docker-compose up -d
Building with native build. Learn about native build in Compose here: https://docs.docker.com/go/compose-native-build/
Creating network "docker_default" with the default driver
Creating docker_visitors-mysql_1 ... done
Creating docker_visitors-service_1 ... done
Creating docker_visitors-webui_1   ... done
$ docker-compose ps
          Name                        Command             State                 Ports              
---------------------------------------------------------------------------------------------------
docker_visitors-mysql_1     docker-entrypoint.sh mysqld   Up      0.0.0.0:3306->3306/tcp, 33060/tcp
docker_visitors-service_1   bash startup.sh               Up      0.0.0.0:8000->30685/tcp, 8000/tcp
docker_visitors-webui_1     npm start                     Up      0.0.0.0:3000->3000/tcp           

```

After executing, you will have 3 running cointainers on your Docker host: visitor-service, visitors-webui and visitors-mysql. For accessing the web application, open your browser and go to http://your-docker-host-ip-address:3000 (or http://localhost:3000/)

To destroy the containers, execute:
```
docker-compose down

or (remove docker images)

docker-compose down --rmi all
```
