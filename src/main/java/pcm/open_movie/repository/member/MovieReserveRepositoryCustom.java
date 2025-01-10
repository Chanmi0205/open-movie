package pcm.open_movie.repository.member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import pcm.open_movie.domain.dto.MovieReserveDTO;

public interface MovieReserveRepositoryCustom {

    Page<MovieReserveDTO> findMovieReserveByMemberId(@Param("memberId") String memberId, boolean openMovieTF, Pageable pageable);

}
