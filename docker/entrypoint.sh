#!/bin/sh

service postgresql start
service postgresql stop


echo '1'
su -c '/usr/lib/postgresql/10/bin/pg_ctl -D /var/lib/postgresql/10/main start' postgres
echo '2'
su -c '/usr/lib/postgresql/10/bin/pg_ctl -D /var/lib/postgresql/10/main restart' postgres
echo '3'
su -c 'psql --command "DROP DATABASE IF EXISTS igcdist;"' postgres
echo '4'
su -c 'psql --command "DROP ROLE IF EXISTS igcdist;"' postgres
echo '5'
su -c "psql --command \"CREATE USER igcdist WITH SUPERUSER PASSWORD 'igcdist';\"" postgres
echo '6'
su -c "psql --command \"CREATE DATABASE igcdist WITH OWNER igcdist;\"" postgres
echo '7'
su -c "psql --command \"GRANT ALL PRIVILEGES ON DATABASE igcdist TO igcdist;\"" postgres

/usr/bin/java -jar /usr/share/server/database.jar
