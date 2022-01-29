#!/bin/zsh

set -e

export DB_PORT=${DB_PORT}
export DB_SERVER=${DB_SERVER}
export DB_USER=${DB_USER}
export DB_PASSWORD=${DB_PASSWORD}

echo ${JAVA_OPTS}
exec java ${JAVA_OPTS} -jar AccountancyApp-0.0.1-SNAPSHOT.jar