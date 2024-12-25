package gabri.dev.javaspringcompose.controllers;

import gabri.dev.javaspringcompose.entities.Song;
import gabri.dev.javaspringcompose.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
@CrossOrigin(origins = "http://localhost:4200")
public class SongController {

    @Autowired
    private SongService songService;

    @PostMapping
    public ResponseEntity<Song> uploadSong(
            @RequestParam("title") String title,
            @RequestParam("artist") String artist,
            @RequestParam("audio") MultipartFile audioFile,
            @RequestParam("image") MultipartFile imageFile) {

        // Validar tipos de archivo
        if (!isValidAudioFile(audioFile) || !isValidImageFile(imageFile)) {
            return ResponseEntity.badRequest().build();
        }

        Song song = new Song();
        song.setTitle(title);
        song.setArtist(artist);

        return ResponseEntity.ok(songService.saveSong(song, audioFile, imageFile));
    }

    @GetMapping
    public ResponseEntity<List<Song>> getAllSongs() {
        return ResponseEntity.ok(songService.findAllSongs());
    }

    @GetMapping("/search")
    public ResponseEntity<Song> getSongByTitle(@RequestParam String title) {
        return songService.findSongByTitle(title)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private boolean isValidAudioFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (
                contentType.equals("audio/mpeg") ||    // .mp3
                        contentType.equals("audio/wav")        // .wav
        );
    }

    private boolean isValidImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (
                contentType.equals("image/jpeg") ||    // .jpg/.jpeg
                        contentType.equals("image/jpg")
        );
    }
}