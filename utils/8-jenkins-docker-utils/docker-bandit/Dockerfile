FROM alpine

MAINTAINER A.Davarski

RUN mkdir -p /app
WORKDIR /app

RUN apk add --no-cache py2-pip python2 bash && pip install --no-cache-dir -U pip && pip install --no-cache-dir -U bandit
