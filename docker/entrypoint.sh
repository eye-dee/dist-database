#!/bin/sh

service postgresql start
service postgresql stop

su -c '/usr/lib/postgresql/10/bin/pg_ctl -D /var/lib/postgresql/10/main start' postgres
su -c '/usr/lib/postgresql/10/bin/pg_ctl -D /var/lib/postgresql/10/main restart' postgres
su -c "psql --command \"CREATE USER igcdist WITH SUPERUSER PASSWORD 'igcdist';\" &&\
  createdb -O igcdist igcdist" postgres

/usr/bin/java -jar /usr/share/server/database.jar
