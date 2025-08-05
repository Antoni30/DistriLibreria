FROM postgres:latest

ENV POSTGRES_USER=admin
ENV POSTGRES_PASSWORD=adminadmin123

COPY init.sql /docker-entrypoint-initdb.d/
