#!/bin/bash
DISPLAY=:99
echo "Build lernstickWelcome"
mvn clean package
echo "Start Xvfb"
Xvfb :99 -screen 0 1280x960x24 &
sleep 2
DISPLAY=:99 java -jar target/lernstickWelcome-*.jar &
sleep 20
mkdir -p /tmp/smoke
DISPLAY=:99 import -window root /tmp/smoke/screenshot.png
killall Xvfb || true
