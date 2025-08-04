FROM rabbitmq:3-management

WORKDIR /var/lib/rabbitmq

COPY rabbitmq.conf /etc/rabbitmq/
COPY enabled_plugins /etc/rabbitmq/

EXPOSE 5672 15672

ENV RABBITMQ_DEFAULT_USER=admin
ENV RABBITMQ_DEFAULT_PASS=admin

CMD ["rabbitmq-server"]