package pcm.open_movie.service;

import pcm.open_movie.domain.dto.*;
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

    Map<Long, List<OpenCinemaDateDTO>> openCinemaDateList
            (Long openMovieId, List<OpenCinemaDTO> openCinemaList);

    List<OpenCinemaDTO> openCinemaList(Long openMovieId);

    Map<OpenCinemaRoomDTO, List<OpenCinemaRoomSiteDTO>> openCinemaRoomAndSiteList(Long openMovieId, LocalDateTime openCinemaRoomDate);

    OpenCinemaRoom openCinemaRoomById(Long openCinemaRoomId);

    List<CinemaRoomSite> cinemaRoomSiteList(List<Long> cinemaRoomSiteIdList);

    Map<Long, List<OpenCinemaRoomDateTimeDTO>> openCinemaRoomList(Long openMovieId, String openMovieDate, Long cinemaId);

    Map<Long, List<OpenCinemaRoomSiteStatusDTO>> openCinemaRoomSiteStatusList(Map<Long, List<OpenCinemaRoomDateTimeDTO>> openCinemaRoomList);

    List<ReserveOpenCinemaRoomDTO> openCinemaRoomSiteList(Long openCinemaRoomId);

    OpenCinemaRoomAndOpenMovieDTO openCinemaRoomAndOpenMovie(Long openCinemaRoomId);

}
