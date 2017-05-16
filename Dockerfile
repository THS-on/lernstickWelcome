FROM debian:stretch
LABEL maintainer "mail@thson.de"
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

ADD . /usr/src/app
RUN apt-get update -qq && apt-get upgrade -qq && apt-get install openjdk-8-jdk openjfx xvfb libdbus-java maven imagemagick procps -qq
CMD "./docker-cmd.sh"
