FROM python:3

EXPOSE 8000

ENV HOME=/code
RUN mkdir -p ${HOME} && \
    useradd -u 1001 -r -g 0 -d ${HOME} -s /sbin/nologin \
            -c "Visitors Application User" default
WORKDIR ${HOME}

ADD visitors ${HOME}/visitors
ADD requirements.txt manage.py startup.sh ${HOME}/

RUN pip install -r requirements.txt

RUN chown -R 1001:0 ${HOME} && \
    find ${HOME} -type d -exec chmod g+ws {} \;

USER 1001
CMD ["bash", "startup.sh"]
