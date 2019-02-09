FROM openjdk:11

COPY build/libs/gwynder-central.jar gwynder-central.jar

RUN mkdir -p /usr/data/token

ENV internal.token /usr/data/token/tokenfile

VOLUME /usr/data

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/gwynder-central.jar"]