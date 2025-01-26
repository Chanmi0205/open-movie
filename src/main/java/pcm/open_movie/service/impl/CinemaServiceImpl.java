package pcm.open_movie.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pcm.open_movie.domain.dto.cinema.CinemaRoomIdAndNameDTO;
import pcm.open_movie.domain.dto.cinema.CinemaRoomIdAndSiteNameDTO;
import pcm.open_movie.domain.entity.Cinema;
import pcm.open_movie.repository.cinema.CinemaRepository;
import pcm.open_movie.repository.cinema.CinemaRoomRepository;
import pcm.open_movie.service.CinemaService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Transactional
public class CinemaServiceImpl implements CinemaService {

    private final CinemaRepository cinemaRepository;
    private final CinemaRoomRepository cinemaRoomRepository;

    // CinemaController cinemaList, cinemaRoomAndSiteList
    @Override
    public List<Cinema> cinemaList() {
        return cinemaRepository.findAll();
    }

    // CinemaController cinemaRoomAndSiteList, FocusController openCinemaRoomList
    @Override
    public Cinema getCinema(Long cinemaId) {
        Optional<Cinema> cinema = cinemaRepository.findById(cinemaId);
        return cinema.orElse(null);
    }

    // CinemaController cinemaRoomAndSiteList
    @Override
    public List<CinemaRoomIdAndNameDTO> cinemaRoomList(Long cinemaId) {
        return cinemaRoomRepository.findCinemaRoomIdAndNameByCinemaRoomId(cinemaId);
    }

    // CinemaController cinemaRoomAndSiteList
    @Override
    public Map<Long, List<String>> cinemaRoomSiteList(List<CinemaRoomIdAndNameDTO> CinemaRoomIdAndSiteNameDTOList) {

        List<Long> cinemaRoomIdList = new ArrayList<>();

        for (CinemaRoomIdAndNameDTO cinemaRoomIdAndNameDTO : CinemaRoomIdAndSiteNameDTOList) {
            cinemaRoomIdList.add(cinemaRoomIdAndNameDTO.getCinemaRoomId());
        }

        Map<Long, List<String>> cinemaRoomIdAndSiteList = new ConcurrentHashMap<>();

        List<CinemaRoomIdAndSiteNameDTO> cinemaRoomSiteNameList
                = cinemaRoomRepository.findCinemaRoomSiteNameList(cinemaRoomIdList);

        for (Long cinemaRoomId : cinemaRoomIdList) {

            List<String> siteNameList = new ArrayList<>();

            for (CinemaRoomIdAndSiteNameDTO cinemaRoomIdAndSiteNameDTO : cinemaRoomSiteNameList) {
                if(cinemaRoomId.equals(cinemaRoomIdAndSiteNameDTO.getCinemaRoomId())) {
                    siteNameList.add(cinemaRoomIdAndSiteNameDTO.getCinemaRoomSiteName());
                }
            }

            cinemaRoomIdAndSiteList.put(cinemaRoomId, siteNameList);

        }

        return cinemaRoomIdAndSiteList;

    }

}
