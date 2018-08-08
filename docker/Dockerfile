FROM postgres:latest
MAINTAINER Igor Drozdov <drozdovigor1996@gmail.com>
ENTRYPOINT ["service", "postgresql", "start"]

RUN echo "deb http://ppa.launchpad.net/linuxuprising/java/ubuntu bionic main" | tee /etc/apt/sources.list.d/linuxuprising-java.list
RUN apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys 73C3DB2A
RUN apt-get update
RUN echo 'y' | apt-get install -y oracle-java10-installer
RUN apt-get install oracle-java10-set-default

COPY entrypoint.sh /usr/local/bin/
COPY ./libs/dist-database-1.0-SNAPSHOT.jar /usr/share/server/database.jar

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/server/database.jar"]