FROM alpine

RUN apk update && apk add go git curl make musl-dev && rm -rf /var/cache/apk/*

ENV GOROOT /usr/lib/go
ENV GOPATH /gopath
ENV GOBIN /usr/bin

RUN git clone https://github.com/arminc/clair-scanner.git /gopath/src/clair

WORKDIR /gopath/src/clair

RUN curl https://raw.githubusercontent.com/golang/dep/master/install.sh | sh && \
 make build 


FROM alpine

COPY --from=0 /gopath/src/clair/clair-scanner /usr/local/bin/clair

EXPOSE 9279

ENTRYPOINT ["clair"]

CMD []
