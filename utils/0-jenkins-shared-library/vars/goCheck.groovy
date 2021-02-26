#!/usr/bin/env groovy

def call() {
    sh 'golint \${WORKSPACE}/fake-backend/config.go'
    sh 'golint \${WORKSPACE}/fake-backend/main.go'
}
