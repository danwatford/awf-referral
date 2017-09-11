FROM openjdk:8
MAINTAINER Daniel Watford <daniel@watfordconsulting.com>

ENTRYPOINT ["/usr/bin/java", \
    "-classpath", "/usr/share/awf-referral/etc", \
    "-Dawf-referral.config.dir=/usr/share/awf-referral/etc", \
    "-Dswarm.project.stage.file=/usr/share/awf-referral/etc/awf-referral.yml", \
    "-jar", "/usr/share/awf-referral/awf-referral-swarm.jar"]

COPY config /usr/share/awf-referral/etc/
COPY target/awf-referral-swarm.jar /usr/share/awf-referral/awf-referral-swarm.jar
