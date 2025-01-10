package pcm.open_movie.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pcm.open_movie.domain.dto.CinemaRoomIdAndNameDTO;
import pcm.open_movie.domain.entity.Cinema;
import pcm.open_movie.domain.entity.CinemaRoom;
import pcm.open_movie.domain.entity.CinemaRoomSite;
import pcm.open_movie.repository.cinema.CinemaRepository;
import pcm.open_movie.repository.cinema.CinemaRoomRepository;
import pcm.open_movie.service.CinemaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Transactional
public class CinemaServiceImpl implements CinemaService {

    private final CinemaRepository cinemaRepository;
    private final CinemaRoomRepository cinemaRoomRepository;

    @Override
    public List<Cinema> cinemaList() {
        return cinemaRepository.findAll();
    }

    @Override
    public Cinema findCinemaById(Long cinemaId) {
        Optional<Cinema> cinema = cinemaRepository.findById(cinemaId);
        return cinema.orElse(null);
    }

    @Override
    public Map<Long, List<String>> cinemaRoomAndSiteList(Long cinemaId) {

        List<CinemaRoom> cinemaRoomList = cinemaRoomRepository.findCinemaRoomByCinemaId(cinemaId);
        List<CinemaRoomSite> CinemaRoomSiteList = cinemaRoomRepository.findCinemaRoomAndSiteByCinemaId(cinemaId);

        // cinemaRoomId - SiteNameList
        Map<Long, List<String>> cinemaRoomAndSiteMap = new ConcurrentHashMap<>();

        // 1관, 2관, 3관 ...
        for (CinemaRoom cinemaRoom : cinemaRoomList) {

            // 1관 - A-1, A-2 ,,,
            List<String> cinemaRoomSiteNameList = new ArrayList<>();

            for (CinemaRoomSite cinemaRoomSite : CinemaRoomSiteList) {
                if(cinemaRoomSite.getCinemaRoom().getCinemaRoomId().equals(cinemaRoom.getCinemaRoomId())) {
                    cinemaRoomSiteNameList.add(cinemaRoomSite.getCinemaRoomSiteName());
                }
            }

            cinemaRoomAndSiteMap.put(cinemaRoom.getCinemaRoomId(), cinemaRoomSiteNameList);
        }

        return cinemaRoomAndSiteMap;
    }

    @Override
    public List<CinemaRoomIdAndNameDTO> cinemaRoomNameList(List<Long> cinemaRoomIdList) {
        return cinemaRoomRepository.findCinemaRoomIdAndNameDTOByCinemaRoomIdList(cinemaRoomIdList);
    }

}
