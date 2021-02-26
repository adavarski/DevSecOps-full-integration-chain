Pre: Create "demo" AWS keypair (used by Terraform)

Note: (Optional) Create new AMI (with all needed: jenkins, docker, etc.) with Packer (BASE_IMAGE="XXXXX”, region": "us-east-2”, subnet_id": "subnet-XXXX”)

```
$ aws ec2 describe-images --owners XXXXX --query 'Images[*].[CreationDate,Name,ImageId]' --filters "Name=name,Values=demo*2021*" --region us-east-2 --output table | sort -r
```
Edit variables.tf for new AMI builded with Packer :
```
variable "ami" {
  description = "AWS AMI builded with packer"

  default = "XXXX"
}
```


Note: All needed software is installed into EC2 VM via cloud-init and template https://github.com/adavarski/DevSecOps-full-integration-chain/blob/main/infrastructure/aws/terraform/Jenkins-EC2/user-data.tpl during `terraform apply`.

Configure Jenkins:

```
$ aws ec2 describe-instances   --filter 'Name=instance-state-name,Values=running' |   jq -r '.Reservations[].Instances[] | [.InstanceId, .PublicDnsName, .Tags[].Value] | @json'
["i-02015fceae96e0349","ec2-13-59-245-11.us-east-2.compute.amazonaws.com","Instance for DevOps demo"]
$ chmod 600 ../demo.pem
$ ssh -i ../demo.pem ubuntu@ec2-13-59-245-11.us-east-2.compute.amazonaws.com
$ sudo cat /var/lib/jenkins/secrets/initialAdminPassword
$ su - jenkins
$ ssh-keygen 

```
J.URL: http://ec2-13-59-245-11.us-east-2.compute.amazonaws.com:8080 ---> Unlock Jenkins, Create admin/devops user, Install Sugested plugins + Docker + Docker pipelines + CloudBees AWS Credentials, Configure J.Credentials for Github (ID:devops) and DockerHub (ID: docker-hub-credentials) and AWS (ID: AWS-demo).

Note: Add global credentials "ID: devops" with "SSH Username with private key" using J.id_rsa private key and using J.plugin "CloudBees AWS Credentials" add "AWS Credentials" (ID: AWS-demo)

Setup github account (SSH keys) --> Add J.id_rsa.pub key.

Create J.pipelines (@ Jenkins AWS EC2 VM):

#### Jenkinsfile-OWASP pipelines (react, node, python)
Example: https://github.com/adavarski/DevSecOps-full-integration-chain/blob/main/utils/docker-owasp-depcheck/Jenkinsfile-PROJECT-OWASP

#### Jenkinsfile-bandit pipelines (python)
Example: https://github.com/adavarski/DevSecOps-full-integration-chain/blob/main/utils/docker-bandit/Jenkinsfile-SAST-Bandit-PYTHON_PROJECT-example

#### Jenkinsfile-clair pipelines (docker images)
Example: https://github.com/adavarski/DevSecOps-full-integration-chain/blob/main/utils/docker-clair-scanner/Jenkinsfile-example

#### Jenkinsfile-s3-backup-scans (backup scans to AWS S3): 
Example: https://github.com/adavarski/DevSecOps-full-integration-chain/blob/main/utils/aws-s3/Jenkinsfile-s3-backup-scans

#### etc.

### TF Usage/Tests
```
export AWS_ACCESS_KEY_ID=
export AWS_SECRET_ACCESS_KEY=
export AWS_DEFAULT_REGION=us-east-2
```

Initialize the Terraform:

```
terraform init
```

View the changes:

```
terraform plan 
```

Launch the resources:

```
terraform apply 
```

Show resource details:

```
terraform show
```
Destroy 
```
terraform destroy 
```

Example TF with workspaces: 

```
terraform init
terraform workspace [new|select] production
terraform plan 
terraform apply 

terraform workspace [new|select] development
terraform plan 
terraform apply 

Check that both environments are up and running, then destroy them:

terraform workspace select production
terraform destroy
terraform workspace select development
terraform destroy 
```

