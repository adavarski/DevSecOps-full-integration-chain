TODO: make ansible playbook 

#### CI/CD : via Ansible Tower (AWX: docker-compose based install) 

```
pip3 install docker-compose
git clone https://github.com/ansible/awx; cd awx/installer
#Edit inventory:  admin_password=password
mkdir ~/.awx; chown -R $USER: ~/.awx
# Run the Ansible playbook to install AWX
ansible-playbook -i inventory -e docker_registry_password=password install.yml
```

Check:

```
#Check docker containers

$ docker ps -a
CONTAINER ID        IMAGE                COMMAND                  CREATED             STATUS              PORTS                  NAMES
92953d164acd        ansible/awx:17.0.0   "/usr/bin/tini -- /u…"   2 hours ago         Up 2 hours          8052/tcp               awx_task
314b0633a4e4        ansible/awx:17.0.0   "/usr/bin/tini -- /b…"   2 hours ago         Up 2 hours          0.0.0.0:80->8052/tcp   awx_web
e795ba75ab75        postgres:12          "docker-entrypoint.s…"   2 hours ago         Up 2 hours          5432/tcp               awx_postgres
1d86f1c58351        redis                "docker-entrypoint.s…"   2 hours ago         Up 2 hours          6379/tcp               awx_redis

#Check awx_task (look for PG migration), tail the awx_task log

$ docker logs -f awx_task
```

Setup Ansible Tower (AWX) with Your Ansible Project and templates.

Once Ansible AWX (Tower) is up and running first few things that we need to configure are:

- Set Up Credentials:

AWS Credentials to sync with AWS Services (AWS-credentials; Credential Type: Amazon Web Services)
GitHub (or Bitbucket, etc.) credentials to sync with our Ansible repos (Github-credentials:user&password; Credential Type:Source Control or Github Personal Access Token)
AWS Private Key File to connect to Instances (optional)

- Setup Ansible Project:

Ansible Tower needs to add templates/job to run playbooks for that we need to add our Ansible project repository in Ansible Tower.
Just like Credentials, go to Projects menu on left side.
Add new project (AWS-demo; Sync)
Fill required information to configure your project (Note here we are required to select credentials configured in the first step to add project).

- Setup Templates:

Lastly, add a template which is nothing but adding playbook as a template so that whenever you want to start/configure any AWS servcies you are only required to run this template as a job.
Just like Projects and Credentials, create a new template using left bar Menu and add a new template.
Add new template for create/destroy AWS resources using right playbooks.

Note: Make sure we add one template for each Role we have in our deployment stack create/destroy (or i.e., application, web, and database). Tower-sync utility triggers the template on the basis of Role tag configured in an instance. Make sure you tick the prompt on-launch checkbox for Inventory and extra variables section while creating Template. The reason behind this is, when tower-sync utility will trigger job it will run this template for dynamic inventory created for AWS ASG group and it will send some extra variables as a part of the trigger to identify which environment it’s running and which service its deploying.


- Lounch Templete(View Job): https://github.com/adavarski/DevSecOps-full-integration-chain/tree/main/utils/ansible-aws

- Lounch Templete(View Jobs): TBD


Stop/Start Ansible Tower:
```
$ cd ~/.awx/awxcompose/
$ docker-compose down 
Stopping awx_task     ... done
Stopping awx_web      ... done
Stopping awx_postgres ... done
Stopping awx_redis    ... done
Removing awx_task     ... done
Removing awx_web      ... done
Removing awx_postgres ... done
Removing awx_redis    ... done
Removing network awxcompose_default
$ docker ps -a
CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS               NAMES
$ docker-compose up -d
Creating network "awxcompose_default" with the default driver
Creating awx_redis    ... done
Creating awx_postgres ... done
Creating awx_web      ... done
Creating awx_task     ... done
$ docker ps -a
CONTAINER ID        IMAGE                COMMAND                  CREATED              STATUS              PORTS                  NAMES
a9ae5dfead6f        ansible/awx:17.0.0   "/usr/bin/tini -- /u…"   About a minute ago   Up About a minute   8052/tcp               awx_task
d41a3c782e67        ansible/awx:17.0.0   "/usr/bin/tini -- /b…"   About a minute ago   Up About a minute   0.0.0.0:80->8052/tcp   awx_web
e1ec438406d4        postgres:12          "docker-entrypoint.s…"   About a minute ago   Up About a minute   5432/tcp               awx_postgres
123e321b3b4d        redis                "docker-entrypoint.s…"   About a minute ago   Up About a minute   6379/tcp               awx_redis

```
