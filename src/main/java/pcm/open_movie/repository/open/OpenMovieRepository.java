package pcm.open_movie.repository.open;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pcm.open_movie.domain.entity.OpenMovie;

@Repository
public interface OpenMovieRepository extends JpaRepository<OpenMovie, Long> {

    @Query("SELECT om.openMovieTitle FROM OpenMovie om WHERE om.openMovieId = :openMovieId")
    String findTitleByOpenMovieId(@Param("openMovieId") Long openMovieId);

}
