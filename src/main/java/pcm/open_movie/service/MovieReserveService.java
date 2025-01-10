package pcm.open_movie.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pcm.open_movie.domain.dto.MovieReserveDTO;
import pcm.open_movie.domain.entity.CinemaRoomSite;
import pcm.open_movie.domain.entity.Member;
import pcm.open_movie.domain.entity.MovieReserve;
import pcm.open_movie.domain.entity.OpenCinemaRoom;

import java.util.List;

public interface MovieReserveService {

    String movieReserve(Member member, OpenCinemaRoom openCinemaRoom, List<CinemaRoomSite> cinemaRoomSite);

    Page<MovieReserveDTO> memberMovieReserveList(String memberId, boolean openMovieTF, Pageable pageable);

}
