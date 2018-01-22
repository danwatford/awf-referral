FROM openjdk:8-jre-alpine
MAINTAINER Daniel Watford <daniel@watfordconsulting.com>

VOLUME /tmp

ENTRYPOINT ["/usr/bin/java", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-jar", "/usr/share/awf-referral/awf-referral.jar"]

COPY config /config
ARG JAR_FILE
ADD $JAR_FILE /usr/share/awf-referral/awf-referral.jar
