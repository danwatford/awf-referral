# Amy Winehouse Foundation (AWF) - Amy's Yard Referral Web Application
# Deployment Guide

This document describes how to deploy the Amy's Yard Referral Web Application in a Docker container
along with supporting containers for reverse proxy, SSL and storage support.

This web application allows partner organisations to AWF to submit referrals on behalf of applicants 
to the Amy's Yard programme - http://amywinehousefoundation.org/our-work/amys-yard

## Application Overview

The Amy's Yard Referral Web Application, referred to in this document as awf-referral, is implemented
as a Spring Boot application which provides a form for users to enter details of applicants
referred to the Amy's Yard programme. Information entered on the form is validated to ensure
any mandatory items are provided and any constraints, such as minimum applicant age, are met.

Once the form is validated and submitted, its contents are forwarded to the Amy Winehouse Foundation
by email, and an email confirmation of the submission is sent to the user.

Awf-referral maintains long-running HTTP sessions which are used to store any form data entered
prior to submission. This allows a user to partially complete the form and save it for later
editing. HTTP sessions are persisted to a MongoDB database running in a Docker container.

The awf-referral webapp does not offer SSL directly, instead it relies on Nginx reverse proxying
with Nginx managing server certificates signed by [LetsEncrypt](https://letsencrypt.org).
Jason Wilder's [nginx-proxy](https://github.com/jwilder/nginx-proxy) and Yves Blusseau's 
[docker-letsencrypt-nginx-proxy-companion](https://github.com/JrCs/docker-letsencrypt-nginx-proxy-companion)
docker images provide reverse proxying and certificate support.

A docker-compose configuration is provided to define containers for the awf-referral webapp
and the supporting components (MongoDB and Nginx).

## Sample Docker-Commpose Configuration

The following docker-compose configuration can be used to deploy the awf-referral webapp.
Note that the environment variables VIRTUAL_HOST, LETSENCRYPT_HOST and LETSENCRYPT_EMAIL for the 
awf-referral container must be set as appropriate for your domain.
```yaml
version: '3.2'
services:
  nginx-proxy:
    image: jwilder/nginx-proxy
    container_name: nginx-proxy
    ports:
      - "80:80"
      - "443:443"
    volumes:
      - type: volume
        source: nginx-html-vol
        target: /usr/share/nginx/html
      - type: volume
        source: le-certs-vol
        target: /etc/nginx/certs
        read-only: true
      - type: volume
        source: vhost-vol
        target: /etc/nginx/vhost.d
      - /var/run/docker.sock:/tmp/docker.sock:ro
    labels:
      com.github.jrcs.letsencrypt_nginx_proxy_companion.nginx_proxy: ""

  le-nginx:
    image: jrcs/letsencrypt-nginx-proxy-companion
    container_name: le-nginx
    volumes:
      - type: volume
        source: nginx-html-vol
        target: /usr/share/nginx/html
      - type: volume
        source: le-certs-vol
        target: /etc/nginx/certs
      - type: volume
        source: vhost-vol
        target: /etc/nginx/vhost.d
      - /var/run/docker.sock:/var/run/docker.sock:ro

  awf-referral:
    image: danwatford/awf-referral:0.3.0
    container_name: awf-referral
    volumes:
      - type: volume
        source: awf-referral-config-vol
        target: /config
    expose:
      - "8080"
    environment:
      - VIRTUAL_HOST=<changeme-fully-qualified-domain-name>
      - LETSENCRYPT_HOST=<changeme-fully-qualified-domain-name>
      - LETSENCRYPT_EMAIL=<changeme-email-address>

  monbo-awf:
    image: mongo
    container_name: mongoawf
    volumes:
      - type: volume
        source: mongo-data-vol
        target: /data/db
      - type: volume
        source: mongo-config-vol
        target: /data/configdb
    expose:
      - "27017"

volumes:
  le-certs-vol:
  vhost-vol:
  nginx-html-vol:
  awf-referral-config-vol:
  mongo-data-vol:
  mongo-config-vol:
```

Place the above docker-compose.yml file in an empty directory on your docker host. The directory
name will be used as the docker-compose project name and will be used as the name prefix for any
volumes created.

Change to the directory and create and start the containers using:
```
docker-compose up -d
```

This will create and start the containers in detached mode. The logging from the containers
can be viewed using:
```
docker-compose logs
```

Since this is the first time the awf-webapp has been started, some configuration will need to be
set: 
- Stop the containers:
```docker-compose stop```
- Find the location of the awf-referral-config-vol volume on the docker host: 
```docker volume inspect <docker-compose-project-name>_awf-referral-config-vol```
- At the awf-referral-config-vol volume location, copy file application-template.yml to application.yml. (You will probably need to 
be root to do this.) Edit application.yml with your mail server details.
- Start the docker-compose project again: ```docker-compose start```
- Monitor the container: ```docker-compose logs -f```
