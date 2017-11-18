FROM openjdk:8-jre-alpine
MAINTAINER Daniel Watford <daniel@watfordconsulting.com>

ENTRYPOINT ["/usr/bin/java", \
    "-jar", "/usr/share/awf-referral/awf-referral.jar"]

COPY config /config
COPY target/awf-referral.jar /usr/share/awf-referral/awf-referral.jar
