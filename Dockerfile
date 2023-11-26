FROM openjdk:11
RUN mkdir /opt/broker
WORKDIR /opt/broker
COPY broker.war $WORKDIR
CMD [ "java","-jar","broker.war" ]
