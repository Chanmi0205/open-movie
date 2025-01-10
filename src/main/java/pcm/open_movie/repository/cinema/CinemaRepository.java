package pcm.open_movie.repository.cinema;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pcm.open_movie.domain.entity.Cinema;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {
}
