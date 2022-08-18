package dev.leandro.songs.app.config;

import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.UUID;

@Configuration
public class KafkaConfig {
    @Bean
    public ProducerFactory<UUID, String> producerFactory(KafkaProperties properties) {
        return new DefaultKafkaProducerFactory<>(properties.buildProducerProperties(), new UUIDSerializer(), new StringSerializer());
    }

    @Bean
    public KafkaTemplate<UUID, String> kafkaTemplate(ProducerFactory<UUID, String> factory) {
        return new KafkaTemplate<>(factory);
    }
}
