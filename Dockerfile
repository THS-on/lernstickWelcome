# Based on Debian 9 because Xvfb has no RANDR support in 8
FROM debian:stretch

# Create work directory for maven
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

# Copy Repository into container
ADD . /usr/src/app

# Install dependencies
RUN apt-get update && apt-get upgrade --yes && apt-get install openjdk-8-jdk openjfx xvfb libdbus-java maven imagemagick procps --yes

# Run docker-cmd.sh on start
CMD "./docker-cmd.sh"
