### Simple script ideal for integration into a DevSecOps pipelines

#### OWASP Dependency-Check

OWASP dependency-check is a software composition analysis utility that detects publicly disclosed vulnerabilities in application dependencies.

#### OWASP Dependency-Check script for DevSecOps pipelines

Script is based on [Docker dependency-check image](https://hub.docker.com/r/owasp/dependency-check)

#### Usage

In the following example it is assumed that the source to be checked is in the current working directory. Persistent data and report directories are used, allowing you to destroy the container after running.

```
PROJECT_NAME=foo owasp-check.sh
```
Example:
```
$ cp -a ../../app/docker/visitors-service/visitors/ .
$ PROJECT_NAME=visitors-servcie ./owasp-check.sh
Using default tag: latest
latest: Pulling from owasp/dependency-check
801bfaa63ef2: Already exists 
5baca56f7803: Pull complete 
342030e49d46: Pull complete 
200cc20823e4: Pull complete 
64d76c50a319: Pull complete 
962575b66c37: Pull complete 
23e85bd29ebb: Pull complete 
15789cbfcd70: Pull complete 
Digest: sha256:4ebc087d5682f351b5a963e8d110b749b2a9945f3102821823d5a81b909932d0
Status: Downloaded newer image for owasp/dependency-check:latest
docker.io/owasp/dependency-check:latest
[INFO] Checking for updates
[INFO] NVD CVE requires several updates; this could take a couple of minutes.
[INFO] Download Started for NVD CVE - 2002
[INFO] Download Started for NVD CVE - 2003
[INFO] Download Complete for NVD CVE - 2003  (1689 ms)
[INFO] Download Started for NVD CVE - 2004
[INFO] Processing Started for NVD CVE - 2003
WARNING: An illegal reflective access operation has occurred
WARNING: Illegal reflective access by com.fasterxml.jackson.module.afterburner.util.MyClassLoader (file:/usr/share/dependency-check/lib/jackson-module-afterburner-2.12.1.jar) to method java.lang.ClassLoader.findLoadedClass(java.lang.String)
WARNING: Please consider reporting this to the maintainers of com.fasterxml.jackson.module.afterburner.util.MyClassLoader
WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
WARNING: All illegal access operations will be denied in a future release
[INFO] Download Complete for NVD CVE - 2002  (1839 ms)
[INFO] Download Started for NVD CVE - 2005
[INFO] Processing Started for NVD CVE - 2002
[INFO] Download Complete for NVD CVE - 2004  (1895 ms)
[INFO] Download Started for NVD CVE - 2006
[INFO] Processing Started for NVD CVE - 2004
[INFO] Download Complete for NVD CVE - 2005  (2365 ms)
[INFO] Download Started for NVD CVE - 2007
[INFO] Processing Started for NVD CVE - 2005
[INFO] Download Complete for NVD CVE - 2006  (7459 ms)
[INFO] Download Started for NVD CVE - 2008
[INFO] Processing Complete for NVD CVE - 2003  (12383 ms)
[INFO] Processing Started for NVD CVE - 2006
[INFO] Download Complete for NVD CVE - 2008  (3534 ms)
[INFO] Download Started for NVD CVE - 2009
[INFO] Download Complete for NVD CVE - 2009  (2501 ms)
[INFO] Download Started for NVD CVE - 2010
[INFO] Download Complete for NVD CVE - 2007  (16703 ms)
[INFO] Download Started for NVD CVE - 2011
[INFO] Download Complete for NVD CVE - 2010  (4338 ms)
[INFO] Download Started for NVD CVE - 2012
[INFO] Processing Complete for NVD CVE - 2004  (19928 ms)
[INFO] Processing Started for NVD CVE - 2008
[INFO] Download Complete for NVD CVE - 2011  (3389 ms)
[INFO] Download Started for NVD CVE - 2013
[INFO] Download Complete for NVD CVE - 2012  (3528 ms)
[INFO] Download Started for NVD CVE - 2014
[INFO] Download Complete for NVD CVE - 2013  (2589 ms)
[INFO] Download Started for NVD CVE - 2015
[INFO] Processing Complete for NVD CVE - 2002  (28322 ms)
[INFO] Processing Started for NVD CVE - 2009
[INFO] Processing Complete for NVD CVE - 2005  (26147 ms)
[INFO] Processing Started for NVD CVE - 2007
[INFO] Download Complete for NVD CVE - 2014  (5542 ms)
[INFO] Download Started for NVD CVE - 2016
[INFO] Download Complete for NVD CVE - 2015  (4190 ms)
[INFO] Download Started for NVD CVE - 2017
[INFO] Download Complete for NVD CVE - 2017  (8671 ms)
[INFO] Download Started for NVD CVE - 2018
[INFO] Download Complete for NVD CVE - 2016  (9540 ms)
[INFO] Download Started for NVD CVE - 2019
[INFO] Processing Complete for NVD CVE - 2006  (28172 ms)
[INFO] Processing Started for NVD CVE - 2010
[INFO] Download Complete for NVD CVE - 2018  (3727 ms)
[INFO] Download Started for NVD CVE - 2020
[INFO] Processing Complete for NVD CVE - 2009  (16873 ms)
[INFO] Processing Started for NVD CVE - 2011
[INFO] Processing Complete for NVD CVE - 2008  (25800 ms)
[INFO] Processing Started for NVD CVE - 2012
[INFO] Download Complete for NVD CVE - 2019  (12223 ms)
[INFO] Download Started for NVD CVE - 2021
[INFO] Processing Complete for NVD CVE - 2007  (22525 ms)
[INFO] Processing Started for NVD CVE - 2013
[INFO] Download Complete for NVD CVE - 2021  (1054 ms)
[INFO] Download Complete for NVD CVE - 2020  (10640 ms)
[INFO] Processing Complete for NVD CVE - 2010  (27007 ms)
[INFO] Processing Started for NVD CVE - 2014
[INFO] Processing Complete for NVD CVE - 2011  (26003 ms)
[INFO] Processing Started for NVD CVE - 2015
[INFO] Processing Complete for NVD CVE - 2012  (29732 ms)
[INFO] Processing Started for NVD CVE - 2017
[INFO] Processing Complete for NVD CVE - 2013  (29910 ms)
[INFO] Processing Started for NVD CVE - 2016
[INFO] Processing Complete for NVD CVE - 2015  (22929 ms)
[INFO] Processing Started for NVD CVE - 2018
[INFO] Processing Complete for NVD CVE - 2014  (27129 ms)
[INFO] Processing Started for NVD CVE - 2019
[INFO] Processing Complete for NVD CVE - 2016  (21977 ms)
[INFO] Processing Started for NVD CVE - 2021
[INFO] Processing Complete for NVD CVE - 2021  (2799 ms)
[INFO] Processing Started for NVD CVE - 2020
[INFO] Processing Complete for NVD CVE - 2017  (32300 ms)
[INFO] Processing Complete for NVD CVE - 2019  (25969 ms)
[INFO] Processing Complete for NVD CVE - 2018  (27763 ms)
[INFO] Processing Complete for NVD CVE - 2020  (17989 ms)
[INFO] Download Started for NVD CVE - Modified
[INFO] Download Complete for NVD CVE - Modified  (1177 ms)
[INFO] Processing Started for NVD CVE - Modified
[INFO] Processing Complete for NVD CVE - Modified  (2288 ms)
[INFO] Begin database maintenance
[INFO] Updated the CPE ecosystem on 117117 NVD records
[INFO] Removed the CPE ecosystem on 3463 NVD records
[INFO] End database maintenance (32445 ms)
[INFO] Begin database defrag
[INFO] End database defrag (14219 ms)
[INFO] Check for updates complete (199790 ms)
[INFO] 

Dependency-Check is an open source tool performing a best effort analysis of 3rd party dependencies; false positives and false negatives may exist in the analysis performed by the tool. Use of the tool and the reporting provided constitutes acceptance for use in an AS IS condition, and there are NO warranties, implied or otherwise, with regard to the analysis or its use. Any use of the tool and the reporting provided is at the user’s risk. In no event shall the copyright holder or OWASP be held liable for any damages whatsoever arising out of or in connection with the use of this tool, the analysis performed, or the resulting report.


[INFO] Analysis Started
[INFO] Finished File Name Analyzer (0 seconds)
[INFO] Finished Dependency Merging Analyzer (0 seconds)
[INFO] Finished Version Filter Analyzer (0 seconds)
[INFO] Finished Hint Analyzer (0 seconds)
[INFO] Created CPE Index (4 seconds)
[INFO] Finished CPE Analyzer (4 seconds)
[INFO] Finished False Positive Analyzer (0 seconds)
[INFO] Finished NVD CVE Analyzer (0 seconds)
[INFO] Finished Sonatype OSS Index Analyzer (0 seconds)
[INFO] Finished Vulnerability Suppression Analyzer (0 seconds)
[INFO] Finished Dependency Bundling Analyzer (0 seconds)
[INFO] Analysis Complete (5 seconds)

### Check dc.html
$ ls owasp-dependency-check/data/
cache  jsrepository.json  odc.mv.db
$ ls owasp-dependency-check/reports/
dc.html  dc.log
$ links owasp-dependency-check/reports/dc.html


```

#### DevSecOps J.Pipeline example: [Jenkinsfile](https://github.com/adavarski/DevSecOps-pipelines/blob/main/docker-owasp-depcheck/Jenkinsfile-PROJECT-OWASP)

