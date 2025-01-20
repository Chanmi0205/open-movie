package pcm.open_movie.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pcm.open_movie.domain.dto.MovieReserveDTO;
import pcm.open_movie.domain.dto.MovieReserveRoomDTO;
import pcm.open_movie.domain.dto.MovieReserveSiteDTO;
import pcm.open_movie.domain.entity.CinemaRoomSite;
import pcm.open_movie.domain.entity.Member;
import pcm.open_movie.domain.entity.MovieReserve;
import pcm.open_movie.domain.entity.OpenCinemaRoom;
import pcm.open_movie.repository.member.MovieReserveRepository;
import pcm.open_movie.service.MovieReserveService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieReserveServiceImpl implements MovieReserveService {

    private final MovieReserveRepository movieReserveRepository;

    @Override
    public void movieReserve(Member member, OpenCinemaRoom openCinemaRoom, List<CinemaRoomSite> cinemaRoomSiteList) {

        for (CinemaRoomSite cinemaRoomSite : cinemaRoomSiteList) {
            MovieReserve movieReserve = new MovieReserve(cinemaRoomSite, openCinemaRoom, member);
            movieReserveRepository.save(movieReserve);
        }

    }

    @Override
    public Page<MovieReserveRoomDTO> memberMovieReserveList(String memberId, boolean openMovieTF, Pageable pageable) {
        return movieReserveRepository.findMovieReserveByMemberId(memberId, openMovieTF, pageable);
    }

    @Override
    public Map<Long, List<MovieReserveSiteDTO>> movieReserveSiteList(String memberId, List<Long> openMovieDateList) {

        List<MovieReserveSiteDTO> movieReserveSiteList = movieReserveRepository.findMovieReserveSiteList(memberId, openMovieDateList);

        Map<Long, List<MovieReserveSiteDTO>> result = new HashMap<>();

        List<MovieReserveSiteDTO> reserveSiteList = new ArrayList<>();

        for (Long openCinemaRoomId : openMovieDateList) {
            for (MovieReserveSiteDTO movieReserveSiteDTO : movieReserveSiteList) {
                if(openCinemaRoomId.equals(movieReserveSiteDTO.getOpenCinemaRoomId())) {
                    reserveSiteList.add(movieReserveSiteDTO);
                }

            }
            result.put(openCinemaRoomId, reserveSiteList);
        }
        return result;

    }

}
