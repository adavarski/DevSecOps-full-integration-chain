### Pre: Install sbt on jenkins-slave

CentOS Example:
```
curl https://bintray.com/sbt/rpm/rpm | sudo tee /etc/yum.repos.d/bintray-sbt-rpm.repo
sudo yum install sbt
```
### build.sbt (SCALA-PROJECT-example)
```
...
dependencyCheckSuppressionFiles += (ThisBuild / baseDirectory).value / "project" / "owasp.xml"
dependencyCheckFormat := "ALL"
dependencyCheckAssemblyAnalyzerEnabled := Some(false)
...
```
### owasp.xml (SCALA-PROJECT-example)

```
<?xml version="1.0" encoding="UTF-8"?>
<suppressions xmlns="https://jeremylong.github.io/DependencyCheck/dependency-suppression.1.1.xsd">
<suppress>
   <notes><![CDATA[
   file name: postgresql-42.2.2.jar
   (misidentified Postgres client driver as the Postgres server)
   ]]></notes>
   <gav regex="true">^org\.postgresql:postgresql:.*$</gav>
   <cpe>cpe:/a:postgresql:postgresql</cpe>
</suppress>

...

</suppressions>

```
### DevSecOps J.Pipeline example: [Jenkinsfile](https://github.com/adavarski/DevSecOps-pipelines/blob/main/scala-owasp/Jenkinsfile-SCALA-PROJECT-example)

Based on [DependencyCheck](https://github.com/jeremylong/DependencyCheck) & [SBT Plugin for OWASP DependencyCheck](https://github.com/albuch/sbt-dependency-check)
