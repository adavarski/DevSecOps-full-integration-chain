FROM node:8.11.2

EXPOSE 3000

ENV HOME=/usr/src/app
RUN mkdir -p ${HOME} && \
    useradd -u 1001 -r -g 0 -d ${HOME} -s /sbin/nologin \
            -c "Visitors Web UI User" default
WORKDIR ${HOME}

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build --production

RUN chown -R 1001:0 ${HOME} && \
    find ${HOME} -type d -exec chmod g+ws {} \;

USER 1001
CMD ["npm", "start"]
