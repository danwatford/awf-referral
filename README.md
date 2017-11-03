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

This project has been implemented as a wildfly swarm solution.

The solution publishes a JSF page, form.xhtml, to capture the details of a referral.
The form can be embedded in an iframe on another website. Page inframetest.html exercises that the iframe can be resized along with the form through use of iframe-resizer (https://github.com/davidjbradshaw/iframe-resizer).

### Running
This project is based on spring-boot and depends on configuration being in place for sending emails.

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
```

To build and run the solution execute:
```
mvn spring-boot:run
```

Alternatively run or debug class `com.foomoo.awf.Application` from your IDE. Before using this method run 
using maven first to ensure all required dependencies are downloaded to the local 
maven repository.

### Running with Docker
To build this project as a docker container run:
```mvn -P docker package```

This will create a docker image at repository danwatford/awf-referral:_version_. 

The docker image relies on the awf-referral.yml file being created at /usr/share/awf-referral/etc.
This file should be created in a volume called awf-referral-vol. Possible steps to create the volume
and file are:
- Run the container to populate the volume with a template configuration file:
```docker run --name awf-referral --mount source=awf-referral-vol,destination=/usr/share/awf-referral/etc --publish 8080:8080 danwatford/awf-referral:0.0.1-SNAPSHOT```  
- Stop the container using Ctrl-C or ```docker stop awf-referral```
- Find the location of the awf-referral-vol volume on the docker host: ```docker volume inspect awf-referral-vol```
- At the awf-referral-vol volume location, copy file awf-referral-template.yml to awf-referral.yml. (You will probably need to 
be root to do this.) Edit awf-referral.yml with your mail server details.
- Start the container again: ```docker start awf-referral```
- Monitor the container: ```docker logs -f awf-referral```

## To Do
Following submission of a referral:
- Validate all required fields have been entered. Display appropriate error messages to the user if any information is missing.
