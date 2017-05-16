#!/bin/bash
DISPLAY=:99
echo "Build lernstickWelcome"
mvn clean package
echo "Start Xvfb"
Xvfb :99 -screen 0 1280x960x24 &
sleep 2
DISPLAY=:99 java -jar target/welcomeapplication-*.jar &
sleep 20
mkdir /tmp/smoke
DISPLAY=:99 import -window root /tmp/smoke/screenshot.png
killall Xvfb || true
