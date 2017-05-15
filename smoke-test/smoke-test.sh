#!/bin/bash

compare -metric RMSE /tmp/smoke/screenshot.png smoke-test/screenshot.png NULL: &> /tmp/noise-tmp
NOISE=$(cat /tmp/noise-tmp  | sed -e 's/([^()]*)//g') 
NOISE_INT=$(echo "($NOISE/1)" | bc)
rm /tmp/noise-tmp
echo "Noise Level": $NOISE_INT

# Max value is set to 1000. TODO: find good value
if [ $NOISE_INT -lt 1000 ];then
   exit 0
else
  exit 1
fi

