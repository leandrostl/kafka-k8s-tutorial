package dev.leandro.songs.model;

import java.util.UUID;

public record Song(Operation operation, UUID id, String name, String author){
    public static Song addSong(Song song) {
        return new Song(Operation.ADD, song.id, song.name, song.author);
    }
}
