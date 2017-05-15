#!/bin/bash

# Use Display :99 for all the tests
DISPLAY=:99

# Build lernstickWelcome with maven
echo "Build lernstickWelcome"
mvn clean package

# Copy jar
cp target/welcomeapplication-0.0.1-SNAPSHOT.jar /tmp/smoke/

# Start Xvfb to get a virtual display
echo "Start Xvfb"
Xvfb :99 -screen 0 1280x960x24 &
sleep 2

# Run lernstickWelcome on this virtual display
DISPLAY=:99 java -jar target/welcomeapplication-0.0.1-SNAPSHOT.jar &
sleep 20

# Take screenshot from virtual display
mkdir /tmp/smoke
DISPLAY=:99 import -window root /tmp/smoke/screenshot.png

# Kill Xvfb (this kills also lernstickWelcome)
killall Xvfb || true
