package pcm.open_movie.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pcm.open_movie.domain.dto.MovieReserveDTO;
import pcm.open_movie.domain.dto.MovieReserveRoomDTO;
import pcm.open_movie.domain.dto.MovieReserveSiteDTO;
import pcm.open_movie.domain.entity.CinemaRoomSite;
import pcm.open_movie.domain.entity.Member;
import pcm.open_movie.domain.entity.OpenCinemaRoom;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface MovieReserveService {

    void movieReserve(Member member, OpenCinemaRoom openCinemaRoom, List<CinemaRoomSite> cinemaRoomSite);

    Page<MovieReserveRoomDTO> memberMovieReserveList(String memberId, boolean openMovieTF, Pageable pageable);

    Map<Long, List<MovieReserveSiteDTO>> movieReserveSiteList(String memberId, Page<MovieReserveRoomDTO> movieReserveRoomDTOList);
}
