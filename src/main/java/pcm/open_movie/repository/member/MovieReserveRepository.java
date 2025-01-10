package pcm.open_movie.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pcm.open_movie.domain.entity.MovieReserve;

@Repository
public interface MovieReserveRepository extends JpaRepository<MovieReserve, Long>, MovieReserveRepositoryCustom {

}
