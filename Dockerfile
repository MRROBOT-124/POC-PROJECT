ARG STRIMZI_VERSION=latest-kafka-3.5.1
FROM quay.io/strimzi/kafka:${STRIMZI_VERSION}

ARG DEBEZIUM_CONNECTOR_VERSION=2.4.0.Alpha2
ENV KAFKA_CONNECT_PLUGIN_PATH=/tmp/connect-plugins/
ENV KAFKA_CONNECT_LIBS=/opt/kafka/libs

RUN mkdir $KAFKA_CONNECT_PLUGIN_PATH &&\
    cd $KAFKA_CONNECT_PLUGIN_PATH &&\
    curl -sfSL https://repo1.maven.org/maven2/io/debezium/debezium-connector-postgres/2.4.0.Alpha2/debezium-connector-postgres-2.4.0.Alpha2-plugin.tar.gz | tar xz &&\
    cd debezium-connector-postgres &&\
    curl -sfSL https://repo1.maven.org/maven2/io/debezium/debezium-interceptor/2.4.0.Alpha2/debezium-interceptor-2.4.0.Alpha2.jar -o debezium-interceptor-2.4.0.Alpha2.jar
 