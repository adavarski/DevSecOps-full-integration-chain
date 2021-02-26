FROM debian:jessie

RUN apt-get update -y && apt-get install --no-install-recommends -y -q \
    curl \
    zip \
    build-essential \
    ca-certificates \
    git mercurial bzr \
    && rm -rf /var/lib/apt/lists/*

ENV GOVERSION 1.14
RUN mkdir /goroot && mkdir /gopath
RUN curl https://storage.googleapis.com/golang/go${GOVERSION}.linux-amd64.tar.gz \
    | tar xvzf - -C /goroot --strip-components=1

ENV GOPATH /gopath
ENV GOROOT /goroot
ENV PATH $GOROOT/bin:$GOPATH/bin:$PATH

RUN go get github.com/mitchellh/gox

RUN git clone https://github.com/arminc/clair-scanner.git /gopath/src/clair

WORKDIR /gopath/src/clair

RUN make build 

FROM alpine

COPY --from=0 /gopath/src/clair/clair-scanner /usr/local/bin/clair

EXPOSE 9279

ENTRYPOINT ["clair"]

CMD []

