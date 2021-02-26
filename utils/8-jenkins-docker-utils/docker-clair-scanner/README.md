### docker-clair-scanner

This is a docker container for [clair-scanner](https://github.com/arminc/clair-scanner) ideal for integration into a DevSecOps pipelines.


#### Clair-scanner: docker containers vulnerability scan

When you work with containers (Docker) you are not only packaging your application but also part of the OS. It is crucial to know what kind of libraries might be vulnerable in your container. One way to find this information is to look at the Docker registry [Hub or Quay.io] security scan. This means your vulnerable image is already on the Docker registry.

What you want is a scan as a part of CI/CD pipeline that stops the Docker image push on vulnerabilities:

- Build and test your application
- Build the container
- Test the container for vulnerabilities
- Check the vulnerabilities against allowed ones, if everything is allowed then pass otherwise fail

This straightforward process is not that easy to achieve when using the services like Docker Hub or Quay.io. This is because they work asynchronously which makes it harder to do straightforward CI/CD pipeline.

Clair to the rescue

CoreOS has created an awesome container scan tool called [Clair](https://github.com/arminc/clair-scanner). Clair is also used by Quay.io. What clair does not have is a simple tool that scans your image and compares the vulnerabilities against a whitelist to see if they are approved or not.

This is where clair-scanner comes into place. The clair-scanner does the following:

- Scans an image against Clair server
- Compares the vulnerabilities against a whitelist
- Tells you if there are vulnerabilities that are not in the whitelist and fails
- If everything is fine it completes correctly



#### Build docker-clair-scanner
```
docker build -t davarski/docker-clair-scanner .
docker login 
docker push davarski/docker-clair-scanner 
```
#### Quick how-to
```
docker network create scanning
docker run -p 5432:5432 -d --net=scanning --name db arminc/clair-db:latest
docker run -p 6060:6060  --net=scanning --link db:postgres -d --name clair arminc/clair-local-scan:latest
docker run --net=scanning --rm --name=scanner --link=clair:clair -v '/var/run/docker.sock:/var/run/docker.sock'  davarski/docker-clair-scanner --clair="http://clair:6060" --ip="scanner" -t Medium <local-image-to-scan>
```
#### Example with generated json report and date formated
```
docker network create scanning
docker run -p 5432:5432 -d --net=scanning --name db arminc/clair-db:latest
docker run -p 6060:6060  --net=scanning --link db:postgres -d --name clair arminc/clair-local-scan:latest
docker run --net=scanning --name=scanner --link=clair:clair -v '/var/run/docker.sock:/var/run/docker.sock'  davarski/docker-clair-scanner --clair="http://clair:6060" --ip="scanner" -t Medium -r report.json <local-image-to-scan>
docker container cp scanner:report.json ./report.json
docker container rm scanner
```
#### Clean:
```
docker container stop db 
docker container stop clair 
docker container rm db 
docker container rm clair 
docker network rm scanning 
docker container prune -f 
docker image prune -f 
```
Example DevSecOps J.Pipeline: [Jenkinsfile](https://github.com/adavarski/DevSecOps-pipelines/blob/main/docker-clair-scanner/Jenkinsfile-example)

### Example APP 
```
docker network create scanning
docker run -p 5432:5432 -d --net=scanning --name db arminc/clair-db:latest
docker run -p 6060:6060  --net=scanning --link db:postgres -d --name clair arminc/clair-local-scan:latest
docker images
cd GITHUB-CLONE-FOLDER/app/docker/visitors-service
docker run --net=scanning --name=scanner --link=clair:clair -v '/var/run/docker.sock:/var/run/docker.sock'  davarski/docker-clair-scanner --clair="http://clair:6060" --ip="scanner" -t Medium -r report.json davarski/visitors-service:1.0.0

2021/02/25 16:03:52 [INFO] ▶ Start clair-scanner
2021/02/25 16:05:19 [INFO] ▶ Server listening on port 9279
2021/02/25 16:05:19 [INFO] ▶ Analyzing 0a6e892618a32d140d717f0134eddb58189545ccc03edac02832b32116174b43
2021/02/25 16:05:22 [INFO] ▶ Analyzing 4cc6d7c46d0bb71bbea420e00f72dd046b6fa54dad23c05ff48611d98e323062
2021/02/25 16:05:22 [INFO] ▶ Analyzing c760a9070c87202c8d13001a3ec7fa67df31286e5b54ae2b67db0b06539463d2
2021/02/25 16:05:22 [INFO] ▶ Analyzing 0dbb0421b8e0fdaf5a5484b27844d9f6b80d40766478137afb69ebd0de80a11d
2021/02/25 16:05:23 [INFO] ▶ Analyzing fd5d1ee3b72398384a48f138bd06d46160fff3a06ad2718ee92f8fb10d687d89
2021/02/25 16:05:28 [INFO] ▶ Analyzing a07e1fde6faa01bf2d59420650a5a2d9a320ad753da31fe67e9d09395f9ba81f
2021/02/25 16:05:28 [INFO] ▶ Analyzing 2e6c287e86ab5869ae57a7643e8063cf399fd815de2ddf9dc75d326d98febaa8
2021/02/25 16:05:28 [INFO] ▶ Analyzing bfb682ca02d28dfd46b06fca49cf1af301e34878a143f69919a953c484a9caa5
2021/02/25 16:05:28 [INFO] ▶ Analyzing cd243e12e0222b66e6f795ac9efade87030930e2f4f070d48d3ad476ff52c8a8
2021/02/25 16:05:28 [INFO] ▶ Analyzing a090ed8e721a941536169f5096218ea12e98fdbfe89dc584d3deef89e8bfb08d
2021/02/25 16:05:29 [INFO] ▶ Analyzing df47d73e21fbe8f59016680adc464e2ab18eec60965ffb91dfd419476474faa8
2021/02/25 16:05:29 [INFO] ▶ Analyzing 5738cb19dfcec31d1b4e476961ee8897b0a78efa2136680c0449ec43ee496158
2021/02/25 16:05:29 [INFO] ▶ Analyzing 2f91e2fce5cfa982d83e369e96baef93c45345b7e74b8a6407d18d9f53eeb262
2021/02/25 16:05:29 [INFO] ▶ Analyzing b3a3373051a7bc4e2fff57e013ee0ca34777f417db907eb8b03c16a8106cf9ec
2021/02/25 16:05:29 [WARN] ▶ Image [davarski/visitors-service:1.0.0] contains 470 total vulnerabilities
2021/02/25 16:05:29 [ERRO] ▶ Image [davarski/visitors-service:1.0.0] contains 138 unapproved vulnerabilities
+------------+-----------------------------+-----------------+------------------------------+------------------------------------------------------------------+
| STATUS     | CVE SEVERITY                | PACKAGE NAME    | PACKAGE VERSION              | CVE DESCRIPTION                                                  |
+------------+-----------------------------+-----------------+------------------------------+------------------------------------------------------------------+
| Unapproved | Critical CVE-2019-19816     | linux           | 4.19.146-1                   | In the Linux kernel 5.0.21, mounting a crafted btrfs             |
|            |                             |                 |                              | filesystem image and performing some operations                  |
|            |                             |                 |                              | can cause slab-out-of-bounds write access in                     |
|            |                             |                 |                              | __btrfs_map_block in fs/btrfs/volumes.c, because a value         |
|            |                             |                 |                              | of 1 for the number of data stripes is mishandled.               |
|            |                             |                 |                              | https://security-tracker.debian.org/tracker/CVE-2019-19816       |
+------------+-----------------------------+-----------------+------------------------------+------------------------------------------------------------------+
| Unapproved | Critical CVE-2019-19814     | linux           | 4.19.146-1                   | In the Linux kernel 5.0.21, mounting a crafted f2fs              |
|            |                             |                 |                              | filesystem image can cause __remove_dirty_segment                |
|            |                             |                 |                              | slab-out-of-bounds write access because an                       |
|            |                             |                 |                              | array is bounded by the number of dirty types                    |
|            |                             |                 |                              | (8) but the array index can exceed this.                         |
|            |                             |                 |                              | https://security-tracker.debian.org/tracker/CVE-2019-19814       |
+------------+-----------------------------+-----------------+------------------------------+------------------------------------------------------------------+
| Unapproved | High CVE-2020-27153         | bluez           | 5.50-1.2~deb10u1             | In BlueZ before 5.55, a double free was found in the             |
|            |                             |                 |                              | gatttool disconnect_cb() routine from shared/att.c.              |
|            |                             |                 |                              | A remote attacker could potentially cause a denial               |
|            |                             |                 |                              | of service or code execution, during service                     |
|            |                             |                 |                              | discovery, due to a redundant disconnect MGMT event.             |
|            |                             |                 |                              | https://security-tracker.debian.org/tracker/CVE-2020-27153       |
+------------+-----------------------------+-----------------+------------------------------+------------------------------------------------------------------+
| Unapproved | High CVE-2020-36158         | linux           | 4.19.146-1                   | mwifiex_cmd_802_11_ad_hoc_start in                               |
|            |                             |                 |                              | drivers/net/wireless/marvell/mwifiex/join.c                      |
|            |                             |                 |                              | in the Linux kernel through 5.10.4 might allow                   |
|            |                             |                 |                              | remote attackers to execute arbitrary code                       |
|            |                             |                 |                              | via a long SSID value, aka CID-5c455c5ab332.                     |
|            |                             |                 |                              | https://security-tracker.debian.org/tracker/CVE-2020-36158       |
+------------+-----------------------------+-----------------+------------------------------+------------------------------------------------------------------+
| Unapproved | High CVE-2020-25643         | linux           | 4.19.146-1                   | A flaw was found in the HDLC_PPP module of the Linux             |
|            |                             |                 |                              | kernel in versions before 5.9-rc7. Memory corruption and         |
|            |                             |                 |                              | a read overflow is caused by improper input validation           |
|            |                             |                 |                              | in the ppp_cp_parse_cr function which can cause the              |
|            |                             |                 |                              | system to crash or cause a denial of service. The highest        |
|            |                             |                 |                              | threat from this vulnerability is to data confidentiality        |
|            |                             |                 |                              | and integrity as well as system availability.                    |
|            |                             |                 |                              | https://security-tracker.debian.org/tracker/CVE-2020-25643       |
+------------+-----------------------------+-----------------+------------------------------+------------------------------------------------------------------+
| Unapproved | High CVE-2020-0452          | libexif         | 0.6.21-5.1+deb10u4           | In exif_entry_get_value of exif-entry.c, there is a              |
|            |                             |                 |                              | possible out of bounds write due to an integer overflow.         |
|            |                             |                 |                              | This could lead to remote code execution if a third              |
|            |                             |                 |                              | party app used this library to process remote image              |
|            |                             |                 |                              | data with no additional execution privileges needed.             |
|            |                             |                 |                              | User interaction is not needed for exploitation.Product:         |
|            |                             |                 |                              | AndroidVersions: Android-8.1 Android-9 Android-10                |
|            |                             |                 |                              | Android-11 Android-8.0Android ID: A-159625731                    |
|            |                             |                 |                              | https://security-tracker.debian.org/tracker/CVE-2020-0452        |
+------------+-----------------------------+-----------------+------------------------------+------------------------------------------------------------------+
| Unapproved | High CVE-2020-29661         | linux           | 4.19.146-1                   | A locking issue was discovered in the tty                        |
|            |                             |                 |                              | subsystem of the Linux kernel through 5.9.13.                    |
|            |                             |                 |                              | drivers/tty/tty_jobctrl.c allows a use-after-free                |
|            |                             |                 |                              | attack against TIOCSPGRP, aka CID-54ffccbf053b.                  |
|            |                             |                 |                              | https://security-tracker.debian.org/tracker/CVE-2020-29661       |
+------------+-----------------------------+-----------------+------------------------------+------------------------------------------------------------------+
| Unapproved | High CVE-2013-7445          | linux           | 4.19.146-1                   | The Direct Rendering Manager (DRM) subsystem in                  |
|            |                             |                 |                              | the Linux kernel through 4.x mishandles requests                 |
|            |                             |                 |                              | for Graphics Execution Manager (GEM) objects,                    |
|            |                             |                 |                              | which allows context-dependent attackers to cause                |
|            |                             |                 |                              | a denial of service (memory consumption) via                     |
|            |                             |                 |                              | an application that processes graphics data, as                  |
|            |                             |                 |                              | demonstrated by JavaScript code that creates many                |
|            |                             |                 |                              | CANVAS elements for rendering by Chrome or Firefox.              |
|            |                             |                 |                              | https://security-tracker.debian.org/tracker/CVE-2013-7445        |
+------------+-----------------------------+-----------------+------------------------------+------------------------------------------------------------------+
| Unapproved | High CVE-2020-27777         | linux           | 4.19.146-1                   | A flaw was found in the way RTAS handled memory accesses         |
|            |                             |                 |                              | in userspace to kernel communication. On a locked down           |
|            |                             |                 |                              | (usually due to Secure Boot) guest system running on             |
|            |                             |                 |                              | top of PowerVM or KVM hypervisors (pseries platform)             |
|            |                             |                 |                              | a root like local user could use this flaw to further            |
|            |                             |                 |                              | increase their privileges to that of a running kernel.           |
|            |                             |                 |                              | https://security-tracker.debian.org/tracker/CVE-2020-27777       |
+------------+-----------------------------+-----------------+------------------------------+------------------------------------------------------------------+
| Unapproved | High CVE-2020-29374         | linux           | 4.19.146-1                   | An issue was discovered in the Linux kernel before               |
|            |                             |                 |                              | 5.7.3, related to mm/gup.c and mm/huge_memory.c. The             |
|            |                             |                 |                              | get_user_pages (aka gup) implementation, when used               |
|            |                             |                 |                              | for a copy-on-write page, does not properly consider             |
|            |                             |                 |                              | the semantics of read operations and therefore can               |
|            |                             |                 |                              | grant unintended write access, aka CID-17839856fd58.             |
|            |                             |                 |                              | https://security-tracker.debian.org/tracker/CVE-2020-29374       |
+------------+-----------------------------+-----------------+------------------------------+------------------------------------------------------------------+
| Unapproved | High CVE-2020-25696         | postgresql-11   | 11.9-0+deb10u1               | A flaw was found in the psql interactive terminal                |
|            |                             |                 |                              | of PostgreSQL in versions before 13.1, before                    |
|            |                             |                 |                              | 12.5, before 11.10, before 10.15, before 9.6.20                  |
|            |                             |                 |                              | and before 9.5.24. If an interactive psql session                |
|            |                             |                 |                              | uses \gset when querying a compromised server, the               |
|            |                             |                 |                              | attacker can execute arbitrary code as the operating             |
|            |                             |                 |                              | system account running psql. The highest threat                  |
|            |                             |                 |                              | from this vulnerability is to data confidentiality               |
|            |                             |                 |                              | and integrity as well as system availability.                    |
|            |                             |                 |                              | https://security-tracker.debian.org/tracker/CVE-2020-25696       |
+------------+-----------------------------+-----------------+------------------------------+------------------------------------------------------------------+
| Unapproved | High CVE-2019-25013         | glibc           | 2.28-10                      | The iconv feature in the GNU C Library (aka                      |
|            |                             |                 |                              | glibc or libc6) through 2.32, when processing                    |
|            |                             |                 |                              | invalid multi-byte input sequences in the                        |
|            |                             |                 |                              | EUC-KR encoding, may have a buffer over-read.                    |
|            |                             |                 |                              | https://security-tracker.debian.org/tracker/CVE-2019-25013       |
+------------+-----------------------------+-----------------+------------------------------+------------------------------------------------------------------+
...
...
...

docker container cp scanner:report.json ./report.json
docker container rm scanner
cd GITHUB-CLONE-FOLDER/app/docker/visitors-webui
docker run --net=scanning --name=scanner --link=clair:clair -v '/var/run/docker.sock:/var/run/docker.sock'  davarski/docker-clair-scanner --clair="http://clair:6060" --ip="scanner" -t Medium -r report.json davarski/visitors-webui:1.0.0

2021/02/25 16:01:26 [INFO] ▶ Start clair-scanner
2021/02/25 16:03:13 [INFO] ▶ Server listening on port 9279
2021/02/25 16:03:13 [INFO] ▶ Analyzing 46731af015ded0b395040db630c0fc584f35fa5d8bf1b4fa74cb9fa1957e4334
2021/02/25 16:03:17 [INFO] ▶ Analyzing cb2d3d1a72eef4025280fb9c4f6f71c1de1c9a32cab4baffa6d1b1c7e57881b1
2021/02/25 16:03:17 [INFO] ▶ Analyzing 0a9b5aaa9e4010370d099b2c7767cf06780d4239ee0659140c8e05b13ac198de
2021/02/25 16:03:18 [INFO] ▶ Analyzing 5ed6a9ea97ca9263c1bf230a29b03a3abebe75dc2c02e8f1e86bb6d73411e230
2021/02/25 16:03:20 [INFO] ▶ Analyzing e7e282278e90cd7549d73d4d4b1de6fccc22376c9cbb34d2c66c6ca73ae752e8
2021/02/25 16:03:20 [INFO] ▶ Analyzing 1a831711e818c6d8b5fe4933ea20ab2c3f8c8272e229176d80b60e02716c27a0
2021/02/25 16:03:21 [INFO] ▶ Analyzing abdca7333b04c2a1246abfc6ce9de06089df2fcd405abdd95491e779f875f178
2021/02/25 16:03:21 [INFO] ▶ Analyzing d62745d55f887a4df6043fb7b62bba8cfcd7cc3a15c5ff634dfbbecfed26d1d7
2021/02/25 16:03:21 [INFO] ▶ Analyzing 31a16df9194f6a2917879039f7a5226ebcd2c3a90c1246b9a932f58840058de7
2021/02/25 16:03:21 [INFO] ▶ Analyzing 9bea0ab15c058bc1831d9c113ecbaf34531f84df8d45f8eee550ea5f76f1e024
2021/02/25 16:03:21 [INFO] ▶ Analyzing 367c5b52ce3a5e06d75572d5083a213ac37bf05404725c8b4bac92e7e9793096
2021/02/25 16:03:22 [INFO] ▶ Analyzing 52007644d96093dbfc702e30974574678a0d01511fc6ee16eb94bdd9d6e54c4c
2021/02/25 16:03:22 [INFO] ▶ Analyzing 5fea725dc03ae31574ed3d40a76160268fc20910be57d8fda464ec8146280fb2
2021/02/25 16:03:22 [INFO] ▶ Analyzing 77ac001e7fa547167454ad29d2a70c121810387f0fc5b717c3b1cc82a7f349c5
2021/02/25 16:03:22 [INFO] ▶ Image [davarski/visitors-webui:1.0.0] contains NO unapproved vulnerabilities


docker container cp scanner:report.json ./report.json
docker container rm scanner
docker container stop db 
docker container stop clair 
docker container rm db 
docker container rm clair 
docker network rm scanning

```
