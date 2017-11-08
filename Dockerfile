FROM openjdk:8
MAINTAINER Daniel Watford <daniel@watfordconsulting.com>

ENTRYPOINT ["/usr/bin/java", \
    "-jar", "/usr/share/awf-referral/awf-referral.jar", \
    "--debug"]

COPY config /config
COPY target/awf-referral.jar /usr/share/awf-referral/awf-referral.jar
