#!/usr/bin/env groovy

def call() {
   sh 'shellcheck --version'
   sh 'apk --no-cache add grep'
   sh '''
   for file in $(grep -IRl "#!(/usr/bin/env |/bin/)" --exclude-dir ".git" --exclude Jenkinsfile \${WORKSPACE}); do
     if ! shellcheck -x $file; then
       export FAILED=1
     else
       echo "$file OK"
     fi
   done
   if [ "${FAILED}" = "1" ]; then
     exit 1
   fi
   '''
}
