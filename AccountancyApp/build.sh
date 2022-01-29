#!/bin/zsh

set -e

mvn clean install -DskipTests

docker build -t accountancy .