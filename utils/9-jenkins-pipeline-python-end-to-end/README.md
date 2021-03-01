# DevSecOps pipeline for Python project

A Jenkins end-to-end DevSecOps pipeline for Python web application.

<img src="https://github.com/adavarski/DevSecOps-pipeline-python/blob/main/pictures/DevSecOps-pipeline-full.png" width="900">

Jenkins instance/environment hosted on AWS EC2 (Ubuntu 18.04) or local environment (on your laptop/workstation/on-prem ubuntu server).

Features:

- [x] Select appropriate security tools and sample python project
- [x] Set up Jenkins server using docker (Dockerfile) and pipeline as code (Jenkinsfile) to run the checks
- [x] Use ansible to create AWS ec2 test instance, configure the environment, and interact with it
- [x] Hook up the web-app with modsecurity providing WAF, reverse proxy capabilities
- [x] Bootstrap with Jenkins API/configfile to setup and automatically create the pipeline job
- [x] Carry out authenticated DAST scan on the python web app 



*Disclaimer: This project is for demonstration purpose with surface level checks only, do not use it as-is for production*

> **Checkout project** - check out python application project repository with XSS vulnerability (https://github.com/adavarski/Python-app-DevSecOps-XSS)

> **git secret check** - check there is no password/token/keys/secrets accidently commited to project github (trufflehog)

> **SCA** - check external dependencies/libraries used by the project have no known vulnerabilities (safety)

> **SAST** - static analysis of the application source code for exploits, bugs, vulnerabilites (Bandit)

> **Container audit** - audit the container that is used to deploy the python application (Lynis)

> **DAST** - deploy the application, register, login, attack & analyse it from the frontend as authenticated user (Nikto + Selenium + python custom script for DAST automation)

> **System security audit** - analyse at the security posture of the system hosting the application (Lynis)

> **WAF** - deploy application with WAF which will filter malicious requests according to OWASP core ruleset (owasp/modsecurity-crs)


## Installation steps

1. Clone this repository to your Ubuntu Server (AWS EC2 t2-medium recommended) or to your laptop/local workstation.
```
git clone https://github.com/adavarski/DevSecOps-pipeline-python
```

2. Edit the code to make it work on your AWS
   - Change to your AWS subnet [vpc_subnet_id](jenkins_home/createAwsEc2.yml#L30) 
   - Change to your AWS [security_group](jenkins_home/createAwsEc2.yml#L10) (allow inbound ssh(22), WAF(80), *Optional* web-app(10007) from your IP ONLY)
   - AWS IAM: Create account, give full-ec2-access to this account. Create AWS Access keys for this account and get access key ID and secret access key.
   - (optional: if you use AWS EC2 to host jenkins instance): Create an IAM role which gives full-ec2-access and assign it to your ubuntu server (AWS EC2 jenkins instance)

3. Run the setup script to create CI/CD server with Jenkins+pipeline ready to go

   Uncomment all needed lines @setup-ubuntu.sh & @Jenkinsfile for local or AWS EC2 jenkins environment:

- Using jenkins local (your laptop) environment:
```
cd DevSecOps-pipeline-python
./setup-ubuntu.sh
```

- Using AWS EC2 jenkins environment (jenkins instance on AWS EC2):

Edit [Jenkinsfile](https://github.com/adavarski/DevSecOps-pipeline-python/blob/main/Jenkinsfile): uncomment lines
```			
// def seleniumIp = env.SeleniumPrivateIp
// sh "python3 ~/authDAST.py $seleniumIp ${testenv} $WORKSPACE/$BUILD_TAG/DAST_results.html"
```
Edit [setup-ubuntu.sh](https://github.com/adavarski/DevSecOps-pipeline-python/blob/main/setup-ubuntu.sh): uncomment lines
```
#apt-get update
#apt install docker.io -y
#apt-get install -y docker-compose
#apt install default-jre -y
#usermod -aG docker ubuntu
#newgrp docker
#systemctl enable docker
#export JenkinsPublicHostname=$(curl -s http://169.254.169.254/latest/meta-data/public-hostname)
#export SeleniumPrivateIp=$(curl -s http://169.254.169.254/latest/meta-data/local-ipv4)
```
```
cd DevSecOps-pipeline-python
git pull
sudo sh setup-ubuntu.sh 
```


4. Make sure your firewall allows incoming traffic to port 8080 (if using AWS EC2 to host jenkins). Then, go to your jenkins server URL 

```
http://your-jenkins-server:8080/
```

For local jenkins environment:
```
https://localhost:8080
```

5. Use the temporary credentials provided on the logs to login. Change your password!
6. Go to the python pipeline project dashboard, click on "Build Now" button to start it off -> "Build with parameters"-> Enter AWS credentials (aws_access_key_id & aws_secret_access_key)


## Setting up a Jenkins Pipeline project manually
 
**A sample pipeline is already provided through automation**

1. Click on New Item, input name for your project and select Pipeline as the option and click OK.
2. Scroll down to Pipeline section - Definition, select "Pipeline script from SCM" from drop down menu.
3. Select Git under SCM, and input Repository URL.
4. (Optional) Create and Add your credentials for the Git repo if your repo is private, and click Save.
5. You will be brought to the Dashboard of your Pipeline project, click on "Build Now" button to start off the pipeline-> -> "Build with parameters"-> Enter AWS credentials (aws_access_key_id & aws_secret_access_key)


**To do list:**


## Demo

<img src="https://github.com/adavarski/DevSecOps-pipeline-python/blob/main/pictures/DevSecOps-pipeline-steps-UI.png" width="900">

<img src="https://github.com/adavarski/DevSecOps-pipeline-python/blob/main/pictures/DevSecOps-ec2.png" width="900">

Debugging (authDAST.py missing html report):
```
#J.pipeline console output 

[Pipeline] sh
+ python3 /var/jenkins_home/authDAST.py selenium-chrome 18.130.68.209 /var/jenkins_home/workspace/DevSecOps-pipeline-python/jenkins-DevSecOps-pipeline-python-2/DAST_results.html
we're at: http://18.130.68.209:10007/register
creating a user..
created user
we're at: http://18.130.68.209:10007/login
logged in successfully.. getting cookie
added cookie to nikto config file to carry out authenticated scan..
- Nikto v2.1.5
---------------------------------------------------------------------------
[Pipeline] }

Shoud be:

[Pipeline] sh
+ python3 /var/jenkins_home/authDAST.py selenium-chrome 18.134.137.141 /var/jenkins_home/workspace/devSecops/jenkins-devSecops-40/DAST_results.html
we're at: http://18.134.137.141:10007/register
creating a user..
created user
we're at: http://18.134.137.141:10007/login
logged in successfully.. getting cookie
added cookie to nikto config file to carry out authenticated scan..
- Nikto v2.1.5
---------------------------------------------------------------------------
+ Target IP:          18.134.137.141
+ Target Hostname:    ec2-18-134-137-141.eu-west-2.compute.amazonaws.com
+ Target Port:        10007
+ Start Time:         2021-02-28 13:01:34 (GMT0)
---------------------------------------------------------------------------
+ Server: Werkzeug/0.14.1 Python/3.9.2
+ The anti-clickjacking X-Frame-Options header is not present.
+ Cookie session created without the httponly flag
[Pipeline] }

# Debugging
$ docker exec -it jenkins-master bash
jenkins@36407a340a57:/$ python3 /var/jenkins_home/authDAST.py selenium-chrome 18.130.68.209 /var/jenkins_home/workspace/DevSecOps-pipeline-python/jenkins-DevSecOps-pipeline-python-3/DAST_results.html
we're at: http://18.130.68.209:10007/register
creating a user..
created user
we're at: http://18.130.68.209:10007/login
logged in successfully.. getting cookie
added cookie to nikto config file to carry out authenticated scan..
jenkins@36407a340a57:/$ - Nikto v2.1.5
---------------------------------------------------------------------------
+ Target IP:          18.130.68.209
+ Target Hostname:    ec2-18-130-68-209.eu-west-2.compute.amazonaws.com
+ Target Port:        10007
+ Start Time:         2021-03-01 11:55:36 (GMT0)
---------------------------------------------------------------------------
+ Server: Werkzeug/0.14.1 Python/3.9.2
+ The anti-clickjacking X-Frame-Options header is not present.
+ Cookie session created without the httponly flag
+ No CGI Directories found (use '-C all' to force check all possible dirs)
+ OSVDB-630: IIS may reveal its internal or real IP in the Location header via a request to the /images directory. The value is "http://0.0.0.0:10007/login".
+ OSVDB-28260: /gossip/_vti_bin/shtml.dll/_vti_rpc?method=server+version%3a4%2e0%2e2%2e2611: Gives info about server settings. CVE-2000-0413, CVE-2000-0709, CVE-2000-0710, http://www.securityfocus.com/bid/1608, http://www.securityfocus.com/bid/1174.
+ OSVDB-28260: /gossip/_vti_bin/shtml.exe/_vti_rpc?method=server+version%3a4%2e0%2e2%2e2611: Gives info about server settings.
+ OSVDB-3092: /gossip/_vti_bin/_vti_aut/author.dll?method=list+documents%3a3%2e0%2e2%2e1706&service%5fname=&listHiddenDocs=true&listExplorerDocs=true&listRecurse=false&listFiles=true&listFolders=true&listLinkInfo=true&listIncludeParent=true&listDerivedT=false&listBorders=fals: We seem to have authoring access to the FrontPage web.
+ OSVDB-3092: /gossip/_vti_bin/_vti_aut/author.exe?method=list+documents%3a3%2e0%2e2%2e1706&service%5fname=&listHiddenDocs=true&listExplorerDocs=true&listRecurse=false&listFiles=true&listFolders=true&listLinkInfo=true&listIncludeParent=true&listDerivedT=false&listBorders=fals: We seem to have authoring access to the FrontPage web.
+ 6544 items checked: 0 error(s) and 7 item(s) reported on remote host
+ End Time:           2021-03-01 12:05:56 (GMT0) (620 seconds)
---------------------------------------------------------------------------
+ 1 host(s) tested

$ docker logs selenium-chrome
2021-03-01 11:43:37,715 INFO Included extra file "/etc/supervisor/conf.d/selenium.conf" during parsing
2021-03-01 11:43:37,717 INFO supervisord started with pid 7
2021-03-01 11:43:38,720 INFO spawned: 'xvfb' with pid 9
2021-03-01 11:43:38,722 INFO spawned: 'selenium-standalone' with pid 10
11:43:38.952 INFO [GridLauncherV3.parse] - Selenium server version: 3.141.59, revision: e82be7d358
2021-03-01 11:43:38,953 INFO success: xvfb entered RUNNING state, process has stayed up for > than 0 seconds (startsecs)
2021-03-01 11:43:38,953 INFO success: selenium-standalone entered RUNNING state, process has stayed up for > than 0 seconds (startsecs)
11:43:39.089 INFO [GridLauncherV3.lambda$buildLaunchers$3] - Launching a standalone Selenium Server on port 4444
2021-03-01 11:43:39.136:INFO::main: Logging initialized @381ms to org.seleniumhq.jetty9.util.log.StdErrLog
11:43:39.428 INFO [WebDriverServlet.<init>] - Initialising WebDriverServlet
11:43:39.518 INFO [SeleniumServer.boot] - Selenium Server is up and running on port 4444
11:51:08.796 INFO [ActiveSessionFactory.apply] - Capabilities are: {
  "browserName": "chrome",
  "version": ""
}
11:51:08.797 INFO [ActiveSessionFactory.lambda$apply$11] - Matched factory org.openqa.selenium.grid.session.remote.ServicedSession$Factory (provider: org.openqa.selenium.chrome.ChromeDriverService)
Starting ChromeDriver 88.0.4324.96 (68dba2d8a0b149a1d3afac56fa74648032bcf46b-refs/branch-heads/4324@{#1784}) on port 1088
Only local connections are allowed.
Please see https://chromedriver.chromium.org/security-considerations for suggestions on keeping ChromeDriver safe.
[161459946C8h.r8o2m8e]D[rSiEvVeErR Ew]a:s  bsitnadr(t)e df asiulcecde:s sCfaunlnloyt. 
assign requested address (99)
11:51:11.227 INFO [ProtocolHandshake.createSession] - Detected dialect: W3C
11:51:11.257 INFO [RemoteSession$Factory.lambda$performHandshake$0] - Started new session 070b5328c3f2e5280fc169f47bf69fe1 (org.openqa.selenium.chrome.ChromeDriverService)
11:55:33.205 INFO [ActiveSessionFactory.apply] - Capabilities are: {
  "browserName": "chrome",
  "version": ""
}
11:55:33.205 INFO [ActiveSessionFactory.lambda$apply$11] - Matched factory org.openqa.selenium.grid.session.remote.ServicedSession$Factory (provider: org.openqa.selenium.chrome.ChromeDriverService)
Starting ChromeDriver 88.0.4324.96 (68dba2d8a0b149a1d3afac56fa74648032bcf46b-refs/branch-heads/4324@{#1784}) on port 24136
Only local connections are allowed.
Please see https://chromedriver.chromium.org/security-considerations for suggestions on keeping ChromeDriver safe.
[1614599733.212][SEVERE]: bind() fCailed: Cannot assign requested address (99)
hromeDriver was started successfully.
11:55:34.028 INFO [ProtocolHandshake.createSession] - Detected dialect: W3C
11:55:34.029 INFO [RemoteSession$Factory.lambda$performHandshake$0] - Started new session 33a0e398f65136597a0a9247eb97c9af (org.openqa.selenium.chrome.ChromeDriverService)
12:21:14.536 INFO [ActiveSessions$1.onStop] - Removing session 070b5328c3f2e5280fc169f47bf69fe1 (org.openqa.selenium.chrome.ChromeDriverService)
12:25:39.638 INFO [ActiveSessions$1.onStop] - Removing session 33a0e398f65136597a0a9247eb97c9af (org.openqa.selenium.chrome.ChromeDriverService)


```
### Reports

<img src="https://github.com/adavarski/DevSecOps-pipeline-python/blob/main/pictures/DevSecOps-workspace.png" width="500">

[Example J.pipeline & Reports output](https://github.com/adavarski/DevSecOps-pipeline-python/tree/main/reports)


