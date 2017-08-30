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
This project runs on Wildfly Swarm and depends on configuration being in place for sending emails.

Create file awf-referral.yml in the parent directory of the project with contents similar to the following:

```
swarm:
  network:
    socket-binding-groups:
      standard-sockets:
        outbound-socket-bindings:
          mail-smtp:
            remote-host: <smtp-server-address>
            remote-port: <smtp-server-port: probably 587>
  mail:
    mail-sessions:
      AWF:
        jndi-name: java:/mail/awf-mail
        smtp-server:
          outbound-socket-binding-ref: mail-smtp
          username: <username>
          password: <password>
          tls: true
        debug: true
```

To build and run the solution execute:
```
mvn wildfly-swarm:run
```

Alternatively run or debug class `org.wildfly.swarm.Swarm` from your IDE with VM 
arg `-Dswarm.project.stage.file=../awf-referral.yml`. Before using this method run 
using maven first to ensure all required dependencies are downloaded to the local 
maven repository.

## To Do
Following submission of a referral:
- Validate all required fields have been entered. Display appropriate error messages to the user if any information is missing.
- Email the content of the submission to the referrer.
- Send the content of the submission to AWF.
