#!/bin/bash
DATE=`date +%Y-%m-%d`
DATEL=`date "+%Y-%m-%d %H:%M:%S"`
# Create folder structure
rm -rf /tmp/gh-pages
git clone -b gh-pages --single-branch https://github.com/${TRAVIS_REPO_SLUG}.git /tmp/gh-pages
mkdir -p /tmp/gh-pages/images
mkdir -p /tmp/gh-pages/jars
mkdir -p /tmp/gh-pages/_posts

# auto format new post
sed -i -e s/REPLACE_COMMIT/${TRAVIS_COMMIT}/g smoke-test/gh-pages-template.markdown
sed -i -e s/REPLACE_DATE/"$DATEL"/g smoke-test/gh-pages-template.markdown
sed -i -e s/REPLACE_MSG/"${TRAVIS_COMMIT_MESSAGE}"/g smoke-test/gh-pages-template.markdown
sed -i -e s/REPLACE_ID/${TRAVIS_BUILD_ID}/g smoke-test/gh-pages-template.markdown
sed -i -e s#REPLACE_USER#"${TRAVIS_REPO_SLUG}"#g smoke-test/gh-pages-template.markdown

cp smoke-test/gh-pages-template.markdown /tmp/gh-pages/_posts/${DATE}-${TRAVIS_COMMIT}.markdown
cp /tmp/smoke/screenshot.png /tmp/gh-pages/images/${TRAVIS_COMMIT}.png || true
cp /tmp/smoke/welcomeapplication-0.0.1-SNAPSHOT.jar /tmp/gh-pages/jars/${TRAVIS_COMMIT}.jar || true
