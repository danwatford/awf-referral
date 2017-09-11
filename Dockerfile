FROM openjdk:8
MAINTAINER Daniel Watford <daniel@watfordconsulting.com>

ENTRYPOINT ["/usr/bin/java", "-Dswarm.project.stage.file=/usr/share/awf-referral/etc/awf-referral.yml", "-jar", "/usr/share/awf-referral/awf-referral-swarm.jar"]

ADD src/main/resources/awf-referral-template.yml /usr/share/awf-referral/etc/
ADD target/awf-referral-swarm.jar /usr/share/awf-referral/awf-referral-swarm.jar
