package pcm.open_movie.service;

import pcm.open_movie.domain.dto.cinema.CinemaRoomIdAndNameDTO;
import pcm.open_movie.domain.dto.cinema.CinemaRoomIdAndSiteNameDTO;
import pcm.open_movie.domain.entity.Cinema;

import java.util.List;
import java.util.Map;

public interface CinemaService {

    List<Cinema> cinemaList();

    Cinema getCinema(Long cinemaId);

    Map<Long, List<String>> cinemaRoomSiteList(List<CinemaRoomIdAndNameDTO> CinemaRoomIdAndSiteNameDTOList);

    List<CinemaRoomIdAndNameDTO> cinemaRoomList(Long cinemaId);
}
