FROM openjdk:11

COPY target/AccountancyApp-0.0.1-SNAPSHOT.jar aAccountancyApp-0.0.1-SNAPSHOT.jar
COPY docker-entrypoint.sh /docker-entrypoint.sh

ENTRYPOINT /docker-entrypoint.sh

EXPOSE 8080