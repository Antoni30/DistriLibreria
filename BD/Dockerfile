FROM postgres:latest

ENV POSTGRES_USER=root
ENV POSTGRES_PASSWORD=adminadmin123

COPY init.sql /docker-entrypoint-initdb.d/
