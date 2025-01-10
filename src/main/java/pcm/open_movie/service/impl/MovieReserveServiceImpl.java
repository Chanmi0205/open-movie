package pcm.open_movie.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pcm.open_movie.domain.dto.MovieReserveDTO;
import pcm.open_movie.domain.entity.CinemaRoomSite;
import pcm.open_movie.domain.entity.Member;
import pcm.open_movie.domain.entity.MovieReserve;
import pcm.open_movie.domain.entity.OpenCinemaRoom;
import pcm.open_movie.repository.member.MovieReserveRepository;
import pcm.open_movie.service.MovieReserveService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieReserveServiceImpl implements MovieReserveService {

    private final MovieReserveRepository movieReserveRepository;

    @Override
    public String movieReserve(Member member, OpenCinemaRoom openCinemaRoom, List<CinemaRoomSite> cinemaRoomSiteList) {

        // 선택한 좌석이 예매되었는지 확인
        for (CinemaRoomSite cinemaRoomSite : cinemaRoomSiteList) {

        }

        for (CinemaRoomSite cinemaRoomSite : cinemaRoomSiteList) {
            MovieReserve movieReserve = new MovieReserve(cinemaRoomSite, openCinemaRoom, member);
            movieReserveRepository.save(movieReserve);
        }
        return member.getMemberName();
    }

    @Override
    public Page<MovieReserveDTO> memberMovieReserveList(String memberId, boolean openMovieTF, Pageable pageable) {
        return movieReserveRepository.findMovieReserveByMemberId(memberId, openMovieTF, pageable);
    }
}
