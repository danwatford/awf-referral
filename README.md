[![Build Status](https://travis-ci.org/danwatford/awf-referral.svg?branch=master)](https://travis-ci.org/danwatford/awf-referral)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

# Amy Winehouse Foundation (AWF) - Amy's Yard Referral Web Application

This web application allows partner organisations to AWF to submit referrals on behalf of applicants to the Amy's Yard programme - http://amywinehousefoundation.org/our-work/amys-yard


## Background

The Amy Winehouse Foundation (AWF) run the Amy’s Yard programme which supports the personal development of disadvantaged young people through music. Participants of the programme are referred to foundation by partner organisations.

The current referral process involves filling out a form (hard copy or word document) and submitting to AWF by post or email.

AWF would like to reduce the effort required to submit a referral and envision a solution where referral information can be entered and submitted using an online form on their website.

## Project Scope

This project is concerned with providing an online method to submit referrals to AWF for the Amy’s Yard programme. The result of a submission should be the data from the referral being delivered to AWF for consideration. Subsequent management of a referral (e.g. use of email or other tools to discuss the referral) is beyond the scope of this project.

## Current Implementation

This project has been implemented as a spring-boot solution.

The solution publishes a form, based on a thymeleaf template, to capture the details of a referral.
The form can be embedded in an iframe on another website. Page inframetest.html exercises that the iframe can be resized along with the form through use of iframe-resizer (https://github.com/davidjbradshaw/iframe-resizer).

### Running
This project is based on spring-boot and depends on a Mongo instance for storing
session data. Configuration needs to be in place to specify an email server and
details of the Mongo instance.

Create file application.yml in the working directory (root) of the project with contents similar to the following:

```
spring:
  mail:
    host: <smtp-server-address>
    port: <smtp-server-port: probably 587>
    username: <username>
    password: <password>
    properties:
      mail:
        debug: true
        smtp:
          auth: true
          starttls:
            enable: true
  data:
    mongodb:
      uri: mongodb://192.168.99.100/awf
  session:
    type:
      store-type: mongo
```

To build and run the solution execute:
```
mvn spring-boot:run
```

Alternatively run or debug class `com.foomoo.awf.Application` from your IDE. Before using this method run 
using maven first to ensure all required dependencies are downloaded to the local 
maven repository.

### Building with Docker
To build this project as a docker container run:
```mvn -P docker package```

This will create a docker image at repository danwatford/awf-referral:_version_. These details can be changed in pom.xml.

### Running with Docker
Docker images are published to <https://hub.docker.com/r/danwatford/awf-referral>.

If not building your own image, pull an image using a command similar to:
```docker pull danwatford/awf-referral:0.1.0```

The docker image relies on the application.yml file being created at /config.
This file should be created in a volume called awf-referral-config-vol. Possible steps to create the volume
and file are:
- Run the container to populate the volume with a template configuration file:
```docker run --name awf-referral --mount source=awf-referral-config-vol,destination=/config --publish 8080:8080 danwatford/awf-referral:0.1.0```  
- The container should stop due to not being able to find a JavaMail bean. If the container doesn't stop use Ctrl-C or ```docker stop awf-referral```
- Find the location of the awf-referral-config-vol volume on the docker host: ```docker volume inspect awf-referral-config-vol```
- At the awf-referral-config-vol volume location, copy file application-template.yml to application.yml. (You will probably need to 
be root to do this.) Edit application.yml with your mail server details.
- Start the container again: ```docker start awf-referral```
- Monitor the container: ```docker logs -f awf-referral```
