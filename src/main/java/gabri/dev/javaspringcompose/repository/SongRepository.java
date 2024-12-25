package gabri.dev.javaspringcompose.repository;

import gabri.dev.javaspringcompose.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    Optional<Song> findByTitle(String title);
}
