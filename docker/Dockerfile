FROM postgres:latest

FROM openjdk:10-jre
MAINTAINER Igor Drozdov <drozdovigor1996@gmail.com>

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/server/database.jar"]

COPY ./entrypoint.sh /usr/share/server/entrypoint.sh
COPY ./libs/dist-database-1.0-SNAPSHOT.jar /usr/share/server/database.jar