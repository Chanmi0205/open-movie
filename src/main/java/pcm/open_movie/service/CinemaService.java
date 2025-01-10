package pcm.open_movie.service;

import pcm.open_movie.domain.dto.CinemaRoomIdAndNameDTO;
import pcm.open_movie.domain.entity.Cinema;

import java.util.List;
import java.util.Map;

public interface CinemaService {

    List<Cinema> cinemaList();

    Cinema findCinemaById(Long cinemaId);

    Map<Long, List<String>> cinemaRoomAndSiteList(Long cinemaId);

    List<CinemaRoomIdAndNameDTO> cinemaRoomNameList(List<Long> cinemaRoomIdList);
}
