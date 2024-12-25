package gabri.dev.javaspringcompose.service;

import gabri.dev.javaspringcompose.entities.Song;
import gabri.dev.javaspringcompose.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private MinioService minioService;

    public Song saveSong(Song song, MultipartFile audioFile, MultipartFile imageFile) {
        String audioUrl = minioService.uploadFile(audioFile, "audio");
        String imageUrl = minioService.uploadFile(imageFile, "images");

        song.setAudioUrl(audioUrl);
        song.setImageUrl(imageUrl);

        return songRepository.save(song);
    }

    public Optional<Song> findSongByTitle(String title) {
        return songRepository.findByTitle(title);
    }

    public List<Song> findAllSongs() {
        return songRepository.findAll();
    }
}

