package dev.leandro.songs.catalogue.consumer;


import dev.leandro.songs.model.Song;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class SongConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(SongConsumer.class);

    @KafkaListener(topics = "${songs.topic}", containerFactory = "songsKafkaListenerContainerFactory")
    public void listenSongs(@Payload Song song) {
        LOG.info("Received song: {}.", song);
    }
}
