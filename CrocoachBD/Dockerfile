FROM cockroachdb/cockroach:latest

WORKDIR /cockroach

COPY init-databases.sql /docker-entrypoint-initdb.d/

EXPOSE 26257 8080

CMD ["start", "--insecure", "--store=node1", "--listen-addr=0.0.0.0:26257", "--http-addr=0.0.0.0:8080"]