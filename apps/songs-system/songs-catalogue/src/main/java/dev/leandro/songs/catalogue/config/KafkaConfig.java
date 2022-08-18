package dev.leandro.songs.catalogue.config;

import dev.leandro.songs.model.Song;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.UUID;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Bean
    public ConsumerFactory<UUID, String> consumerFactory(KafkaProperties properties) {
        return new DefaultKafkaConsumerFactory<>(properties.buildConsumerProperties(), new UUIDDeserializer(), new StringDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<UUID, String> kafkaListenerContainerFactory(ConsumerFactory<UUID, String> consumerFactory) {
        final var factory = new ConcurrentKafkaListenerContainerFactory<UUID, String>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConsumerFactory<UUID, Song> songsConsumerFactory(KafkaProperties properties) {
        return new DefaultKafkaConsumerFactory<>(properties.buildConsumerProperties(), new UUIDDeserializer(), new JsonDeserializer<>(Song.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<UUID, Song> songsKafkaListenerContainerFactory(ConsumerFactory<UUID, Song> consumerFactory) {
        final var factory = new ConcurrentKafkaListenerContainerFactory<UUID, Song>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
