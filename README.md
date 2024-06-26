# Description
A [Spring Boot](https://spring.io/projects/spring-boot) Microservice Application programming practice to understand building and deploying of microservices.

## Motivation
This is a master's program assignment that gave me an opportunity to learn microservices through Java Spring Boot, and deployment though [Jenkins](https://www.jenkins.io) pipeline.

## Type
Microservice

## Jenkins pipeline
Jenkins webhook is plugged into this repository which notifies "code push" event to Jenkins server which starts the automated build pipeline involving:
- Building of the application including unit and integration tests (Through [Maven](https://maven.apache.org)])
- Executing static code analysis and code coverage (Through [SonarQube](https://www.sonarsource.com/products/sonarqube/))
- Building a [Docker](https://www.docker.com) Image out of the built application (Through shell script)
- Pushing the Docker Image to a repository (Docker Hub) (Through shell script)
- Executing shell script in a virtual server (AWS EC2 instance) to run [Ansible](https://www.ansible.com) playbook (To pull image from the Docker Hub and deploy Docker Containers in App staging server (EC2 instance)
