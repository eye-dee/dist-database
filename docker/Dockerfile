FROM ubuntu:latest
MAINTAINER Igor Drozdov <drozdovigor1996@gmail.com>

RUN apt-get update
RUN apt-get install locales

# Set the locale
RUN locale-gen en_US.UTF-8
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

RUN apt-get install -y software-properties-common

RUN add-apt-repository ppa:linuxuprising/java
RUN apt-get update
RUN echo 'y' | apt-get install -y oracle-java10-installer
RUN apt-get install oracle-java10-set-default

RUN wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | \
      apt-key add -
RUN apt-get update
RUN echo "Europe \ Moscow" | apt-get install -y postgresql

RUN apt-get install sudo

COPY ./entrypoint.sh /usr/local/bin/entrypoint.sh
COPY ./libs/dist-database-1.0-SNAPSHOT.jar /usr/share/server/database.jar

EXPOSE 5432

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]