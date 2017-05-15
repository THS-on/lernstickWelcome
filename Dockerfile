FROM debian:stretch
LABEL maintainer "mail@thson.de"
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

ADD . /usr/src/app
RUN apt-get update && apt-get upgrade --yes && apt-get install openjdk-8-jdk openjfx xvfb libdbus-java maven imagemagick procps --yes
CMD "./docker-cmd.sh"
