package pcm.open_movie.service;

import pcm.open_movie.domain.dto.*;
import pcm.open_movie.domain.entity.Cinema;
import pcm.open_movie.domain.entity.CinemaRoomSite;
import pcm.open_movie.domain.entity.OpenCinemaRoom;
import pcm.open_movie.domain.entity.OpenMovie;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OpenMovieService {

    String openMovieTitle(Long openMovieId);

    List<OpenMovie> openMovieList();

    Optional<OpenMovie> openMovieById(Long openMovieId);

    List<Cinema> openCinemaList(Long openMovieId);

//    Map<LocalDateTime, List<OpenCinemaDTO>> openCinemaList(Long openMovieId);

    List<String> openCinemaRoomCount(Long openMovieId, Long openCinemaId);

    Map<OpenCinemaRoomDTO, List<OpenCinemaRoomSiteDTO>> openCinemaRoomAndSiteList(Long openMovieId, LocalDateTime openCinemaRoomDate);

    List<OpenCinemaRoomSiteSelectDTO> openCinemaRoomSiteSelect(Long openCinemaRoomId);

    OpenCinemaRoom openCinemaRoomById(Long openCinemaRoomId);

    List<CinemaRoomSite> cinemaRoomSiteList(List<Long> cinemaRoomSiteIdList);

    boolean cinemaRoomSiteReserveCheck(List<Long> cinemaRoomSiteIdList);

    Map<String, List<OpenCinemaRoomAndSiteDTO>> openCinemaRoomAndSiteList(Long openMovieId, String openMovieDate, Long cinemaId);

}
