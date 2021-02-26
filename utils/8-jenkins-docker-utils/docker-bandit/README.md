### Docker Bandit SAST (Static Application Security Testing) for python projects and DevSecOps pipelines


#### Bandit
Bandit: SAST (Static Application Security Testing) for python projects

#### Docker-Bandit

Simple Bandit docker image/container to run static security tests on python project in stand-alone mode, ideal for integration into a DevSecOps pipelines: 

#### Build
```
   docker build -t davarski/bandit -f Dockerfile .
   docker login
   docker push davarski/bandit
```

#### Use
```
    docker run -u root --rm -v YOUR_PYTHON_PROJECT_PATH:/app davarski/bandit bandit -r ./
    //help
    docker run -u root --rm -v YOUR_PYTHON_PROJECT_PATH:/app davarski/bandit bandit -h
```
Example: 

```
cd app/docker/visitors-service
docker run -u root --rm -v GITHUB-CLONE-FOLDER/app/docker/visitors-service:/app davarski/bandit bandit -r ./
[main]	INFO	profile include tests: None
[main]	INFO	profile exclude tests: None
[main]	INFO	cli include tests: None
[main]	INFO	cli exclude tests: None
[main]	INFO	running on Python 2.7.16
Run started:2021-02-25 15:59:54.354842

Test results:
>> Issue: [B105:hardcoded_password_string] Possible hardcoded password: '*#!sx=&xmrhjj82clg4kyk6rl+ybm_$1ngxs@kp@g&!k(xle64'
   Severity: Low   Confidence: Medium
   Location: ./visitors/settings.py:23
   More Info: https://bandit.readthedocs.io/en/latest/plugins/b105_hardcoded_password_string.html
22	# SECURITY WARNING: keep the secret key used in production secret!
23	SECRET_KEY = '*#!sx=&xmrhjj82clg4kyk6rl+ybm_$1ngxs@kp@g&!k(xle64'
24	
25	# SECURITY WARNING: don't run with debug turned on in production!
26	DEBUG = True

--------------------------------------------------

Code scanned:
	Total lines of code: 200
	Total lines skipped (#nosec): 0

Run metrics:
	Total issues (by severity):
		Undefined: 0
		Low: 1
		Medium: 0
		High: 0
	Total issues (by confidence):
		Undefined: 0
		Low: 0
		Medium: 1
		High: 0
Files skipped (1):
	./manage.py (syntax error while parsing AST from file)

```

#### Example DevSecOps J.Pipeline: [Jenkinsfile](https://github.com/adavarski/DevSecOps-pipelines/blob/main/docker-bandit/Jenkinsfile-SAST-Bandit-PYTHON_PROJECT-example)
