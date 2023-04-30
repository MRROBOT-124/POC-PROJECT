package com.realtime.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DebeziumConfig {

    @Bean
    public io.debezium.config.Configuration debeziumConnector() {
        return io.debezium.config.Configuration.create()
                .with("name", "debezium-connector")
                .with("connector.class", "io.debezium.connector.postgresql.PostgresConnector")
                .with("topic.prefix", "auth")
                .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
                .with("offset.storage.file.filename", "/tmp/offsets.dat")
                .with("offset.flush.interval.ms", "60000")
                .with("database.hostname", "localhost")
                .with("database.port", "5432")
                .with("database.user", "admin")
                .with("database.password", "password")
                .with("database.dbname", "auth")
                .with("database.include.list", "auth")
                .with("include.schema.changes", "false")
                .with("database.server.id", "10181")
                .with("database.server.name", "customer-mysql-db-server")
                .with("database.history", "io.debezium.relational.history.FileDatabaseHistory")
                .with("database.history.file.filename", "/tmp/dbhistory.dat")
                .build();

    }
}
