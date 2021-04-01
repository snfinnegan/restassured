FROM maven:3-openjdk-11

LABEL GitHubUITest="1.0"

WORKDIR /usr/automation

ADD . .

ENTRYPOINT mvn -DHUB_HOST=selenium-hub -Dmaven.clean.failOnError=false clean install