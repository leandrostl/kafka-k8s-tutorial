package dev.leandro.songs.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.leandro.songs.model.Song;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/songs")
public class SongController {
    private static final Logger LOG = LoggerFactory.getLogger(SongController.class);

    private final ObjectMapper mapper;
    private final String kafkaTopic;
    private final KafkaTemplate<UUID, String> kafkaTemplate;

    public SongController(ObjectMapper mapper, @Value("${songs.topic}") String kafkaTopic, KafkaTemplate<UUID, String> kafkaTemplate) {
        this.mapper = mapper;
        this.kafkaTopic = kafkaTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping(consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public DeferredResult<ResponseEntity<String>> createSong(@RequestBody Song song) throws JsonProcessingException {
        final var newSong = Song.addSong(song);
        final var response = new DeferredResult<ResponseEntity<String>>();
        kafkaTemplate.send(kafkaTopic, newSong.id(), mapper.writeValueAsString(newSong)).addCallback(result -> {
            LOG.info("Sent message: {} with offset {}.", result, getOffset(result));
            response.setResult(ResponseEntity.created(URI.create("/songs/" + song.id())).build());
        }, ex -> {
            LOG.error("Unable to send message {} due to: {}.", newSong, ex.getMessage());
            response.setErrorResult(ResponseEntity.internalServerError().body(ex.getMessage()));
        });

        return response;
    }

    private Long getOffset(SendResult<UUID, String> result) {
        return Optional.ofNullable(result).map(SendResult::getRecordMetadata).map(RecordMetadata::offset).orElse(null);
    }
}
